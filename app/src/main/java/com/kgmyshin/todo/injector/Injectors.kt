package com.kgmyshin.todo.injector

import com.kgmyshin.todo.api.TodoApiClient
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.infra.repository.TodoRepositoryImpl
import com.kgmyshin.todo.usecase.impl.todo.DoneTodoUseCaseImpl
import com.kgmyshin.todo.usecase.impl.todo.UndoneTodoUseCaseImpl
import io.reactivex.android.schedulers.AndroidSchedulers

object Injectors {
    private val repository: TodoRepositoryImpl = TodoRepositoryImpl(TodoApiClient())
    val readOnlyRepository: ReadOnlyTodoRepository = repository
    val doneTodoUseCase = DoneTodoUseCaseImpl(repository)
    val undoneTodoUseCase = UndoneTodoUseCaseImpl(repository)
    val uiScheduler = AndroidSchedulers.mainThread()
}