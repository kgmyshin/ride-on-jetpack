package com.kgmyshin.todo.ui.todo.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.kgmyshin.todo.databinding.FragmentTodoListBinding
import com.kgmyshin.todo.injector.Injectors
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

class TodoListFragment : Fragment() {

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
        val factory = TodoListViewModelFactory(
                readOnlyTodoRepository = Injectors.readOnlyRepository,
                doneTodoUseCase = Injectors.doneTodoUseCase,
                undoneTodoUseCase = Injectors.undoneTodoUseCase,
                uiScheduler = Injectors.uiScheduler
        )
        val viewModel = ViewModelProviders.of(
                this,
                factory
        ).get(TodoListViewModel::class.java)
        val adapter = TodoListAdapter().apply {
            onClickTodoClickListener = object : OnClickTodoClickListener {
                override fun onClick(bindingModel: TodoBindingModel) {
                    // 詳細画面へ
                }
            }
            onClickDoneTodoListener = object : OnClickDoneTodoListener {
                override fun onClick(bindingModel: TodoBindingModel) {
                    viewModel.done(bindingModel.id)
                }
            }
            onClickUndoneTodoListener = object : OnClickUndoneTodoListener {
                override fun onClick(bindingModel: TodoBindingModel) {
                    viewModel.undone(bindingModel.id)
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
                Log.d("aaaaa", "${recyclerView.canScrollHorizontally(RecyclerView.VERTICAL)} : $lastIndex")
                if (!recyclerView.canScrollHorizontally(RecyclerView.VERTICAL) && lastIndex != 0) {
                    viewModel.readMore()
                }
            }
        })
        viewModel.todoList.observe(
                this,
                Observer { bindingModelList ->
                    adapter.submitList(bindingModelList)
                }
        )
        return binding.root
    }
}