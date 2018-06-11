package com.kgmyshin.todo.ui.todo.bindingModel

import com.kgmyshin.todo.domain.Todo

object TodoConverter {

    fun convertToBindingModel(todoList: List<Todo>): List<TodoBindingModel> =
            todoList.map { convertToBindingModel(it) }

    fun convertToBindingModel(todo: Todo): TodoBindingModel =
            TodoBindingModel(
                    todo.id,
                    todo.name,
                    todo.description,
                    todo.done
            )

}