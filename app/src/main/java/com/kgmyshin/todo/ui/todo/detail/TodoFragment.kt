package com.kgmyshin.todo.ui.todo.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kgmyshin.todo.databinding.FragmentTodoBinding
import com.kgmyshin.todo.domain.TodoId
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TodoFragment : Fragment() {

    @Inject
    lateinit var todoViewModelFactoryProvider: TodoViewModelFactory.Provider

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

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
        val factory = todoViewModelFactoryProvider.provide(
                TodoId(TodoFragmentArgs.fromBundle(arguments).id)
        )
        val viewModel = ViewModelProviders.of(
                this,
                factory
        ).get(TodoViewModel::class.java)
        binding.doneButton.setOnClickListener {
            viewModel.done()
        }
        binding.undoneButton.setOnClickListener {
            viewModel.undone()
        }
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        return binding.root
    }
}