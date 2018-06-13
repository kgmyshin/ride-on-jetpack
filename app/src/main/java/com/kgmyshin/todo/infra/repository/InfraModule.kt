package com.kgmyshin.todo.infra.repository

import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module

@Module
abstract class InfraModule {

    @Binds
    abstract fun provideTodoRepository(
            impl: TodoRepositoryImpl
    ): TodoRepository

    @Binds
    abstract fun provideReadOnlyTodoRepository(
            impl: TodoRepositoryImpl
    ): ReadOnlyTodoRepository

}