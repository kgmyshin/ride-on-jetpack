package com.kgmyshin.todo.ui.todo.list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kgmyshin.todo.R
import com.kgmyshin.todo.databinding.FragmentTodoListBinding
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TodoListFragment : Fragment() {

    @Inject
    lateinit var todoListViewModelFactory: TodoListViewModelFactory

    private lateinit var todoListViewModel: TodoListViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodoListBinding.inflate(
                inflater,
                container,
                false
        )
        todoListViewModel = ViewModelProviders.of(
                this,
                todoListViewModelFactory
        ).get(TodoListViewModel::class.java)
        val adapter = TodoListAdapter().apply {
            onClickTodoClickListener = object : OnClickTodoClickListener {
                override fun onClick(bindingModel: TodoBindingModel) {
                    binding.root.findNavController()
                            .navigate(TodoListFragmentDirections.toDetail(bindingModel.id.value))
                }
            }
            onClickDoneTodoListener = object : OnClickDoneTodoListener {
                override fun onClick(bindingModel: TodoBindingModel) {
                    todoListViewModel.done(bindingModel.id)
                }
            }
            onClickUndoneTodoListener = object : OnClickUndoneTodoListener {
                override fun onClick(bindingModel: TodoBindingModel) {
                    todoListViewModel.undone(bindingModel.id)
                }
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastIndex = binding.recyclerView.adapter?.itemCount
                if (!recyclerView.canScrollHorizontally(RecyclerView.VERTICAL) && lastIndex != 0) {
                    todoListViewModel.readMore()
                }
            }
        })
        todoListViewModel.todoList.observe(
                this,
                Observer { bindingModelList ->
                    adapter.submitList(bindingModelList)
                }
        )
        return binding.root
    }

    override fun onCreateOptionsMenu(
            menu: Menu?,
            inflater: MenuInflater?
    ) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(
                R.menu.menu_search,
                menu
        )
        (menu?.findItem(R.id.action_search)?.actionView as? SearchView)?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                todoListViewModel.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                todoListViewModel.filter(newText)
                return false
            }
        })
    }
}