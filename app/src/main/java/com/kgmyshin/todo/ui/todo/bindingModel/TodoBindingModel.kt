package com.kgmyshin.todo.ui.todo.bindingModel

import com.kgmyshin.todo.domain.TodoId

data class TodoBindingModel(
        val id: TodoId,
        val name: String,
        val description: String,
        val done: Boolean
)