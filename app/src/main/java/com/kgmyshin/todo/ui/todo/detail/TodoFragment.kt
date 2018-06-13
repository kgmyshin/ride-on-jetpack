package com.kgmyshin.todo.ui.todo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kgmyshin.todo.databinding.FragmentTodoBinding
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.injector.Injectors

class TodoFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodoBinding.inflate(
                inflater,
                container,
                false
        )
        val factory = TodoViewModelFactory(
                todoId = TodoId(TodoFragmentArgs.fromBundle(arguments).id),
                readOnlyTodoRepository = Injectors.readOnlyRepository,
                doneTodoUseCase = Injectors.doneTodoUseCase,
                undoneTodoUseCase = Injectors.undoneTodoUseCase,
                uiScheduler = Injectors.uiScheduler
        )
        val viewModel = ViewModelProviders.of(
                this,
                factory
        ).get(TodoViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }
}