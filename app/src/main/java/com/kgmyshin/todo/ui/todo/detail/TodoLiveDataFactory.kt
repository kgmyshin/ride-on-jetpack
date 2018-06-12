package com.kgmyshin.todo.ui.todo.detail

import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import io.reactivex.Scheduler

class TodoLiveDataFactory(
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val uiScheduler: Scheduler
) {
    fun create(todoId: TodoId): TodoLiveData = TodoLiveData(
            todoId,
            readOnlyTodoRepository,
            uiScheduler
    )
}