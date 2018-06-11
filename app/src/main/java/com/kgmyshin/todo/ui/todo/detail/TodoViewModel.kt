package com.kgmyshin.todo.ui.todo.detail

import androidx.lifecycle.ViewModel
import com.kgmyshin.todo.domain.TodoId

class TodoViewModel(
        todoId: TodoId,
        todoLiveDataFactory: TodoLiveDataFactory
) : ViewModel() {

    private val todoLiveData = todoLiveDataFactory.create(todoId)

    fun done() = todoLiveData.done()

    fun undone() = todoLiveData.undone()

}
