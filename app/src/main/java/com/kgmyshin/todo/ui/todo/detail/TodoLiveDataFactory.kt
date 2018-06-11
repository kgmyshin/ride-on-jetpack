package com.kgmyshin.todo.ui.todo.detail

import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.usecase.todo.DoneTodoUseCase
import com.kgmyshin.todo.usecase.todo.UndoneTodoUseCase
import io.reactivex.Scheduler

class TodoLiveDataFactory(
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val doneTodoUseCase: DoneTodoUseCase,
        private val undoneTodoUseCase: UndoneTodoUseCase,
        private val uiScheduler: Scheduler
) {
    fun create(todoId: TodoId): TodoLiveData = TodoLiveData(
            todoId,
            readOnlyTodoRepository,
            doneTodoUseCase,
            undoneTodoUseCase,
            uiScheduler
    )
}