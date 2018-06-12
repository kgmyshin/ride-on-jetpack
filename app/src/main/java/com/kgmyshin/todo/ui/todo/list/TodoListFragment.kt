package com.kgmyshin.todo.ui.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kgmyshin.todo.injector.Injectors

class TodoListFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
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
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}