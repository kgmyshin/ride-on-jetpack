package com.kgmyshin.todo.ui.todo.list

import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import io.reactivex.Scheduler

class TodoListLiveDataFactory(
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val uiScheduler: Scheduler
) {

    fun create(): TodoListLiveData = TodoListLiveData(
            readOnlyTodoRepository,
            uiScheduler
    )

}
