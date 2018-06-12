package com.kgmyshin.todo.ui.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kgmyshin.todo.databinding.ViewTodoBinding
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

class TodoViewHolder private constructor(
        private val binding: ViewTodoBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(
                inflater: LayoutInflater,
                parent: ViewGroup,
                attachToRoot: Boolean
        ): TodoViewHolder = TodoViewHolder(
                ViewTodoBinding.inflate(
                        inflater,
                        parent,
                        attachToRoot
                )
        )
    }

    fun bindTo(bindingModel: TodoBindingModel) {
        binding.bindingModel = bindingModel
        binding.executePendingBindings()
    }

}