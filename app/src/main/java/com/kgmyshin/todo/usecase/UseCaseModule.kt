package com.kgmyshin.todo.usecase

import com.kgmyshin.todo.usecase.impl.todo.DoneTodoUseCaseImpl
import com.kgmyshin.todo.usecase.impl.todo.UndoneTodoUseCaseImpl
import com.kgmyshin.todo.usecase.todo.DoneTodoUseCase
import com.kgmyshin.todo.usecase.todo.UndoneTodoUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {

    @Binds
    abstract fun provideDoneTodoUseCase(
            impl: DoneTodoUseCaseImpl
    ): DoneTodoUseCase

    @Binds
    abstract fun provideUndoneTodoUseCase(
            impl: UndoneTodoUseCaseImpl
    ): UndoneTodoUseCase

}