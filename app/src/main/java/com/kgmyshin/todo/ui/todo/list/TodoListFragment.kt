package com.kgmyshin.todo.ui.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
            onTodoClickListener = object : OnTodoClickListener {
                override fun onClick(bindingModel: TodoBindingModel) {
                    viewModel.done(bindingModel.id)
                }
            }
            onToggleDoneListener = object : OnToggleDoneListener {
                override fun onToggle(onOff: Boolean, bindingModel: TodoBindingModel) {
                    viewModel.undone(bindingModel.id)
                }
            }
        }
        binding.recyclerView.adapter = adapter
        viewModel.todoList.observe(
                this,
                Observer { bindingModelList ->
                    adapter.submitList(bindingModelList)
                }
        )
        return binding.root
    }
}