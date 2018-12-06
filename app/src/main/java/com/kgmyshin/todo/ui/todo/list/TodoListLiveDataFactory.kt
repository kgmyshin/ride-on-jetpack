package com.kgmyshin.todo.ui.todo.list

import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.infra.repository.ErrorLiveData
import io.reactivex.Scheduler

class TodoListLiveDataFactory(
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val uiScheduler: Scheduler,
        private val errorLiveData: ErrorLiveData
) {

    fun create(): TodoListLiveData = TodoListLiveData(
            readOnlyTodoRepository,
            uiScheduler,
            errorLiveData
    )

}
