package com.kgmyshin.todo

import com.kgmyshin.todo.api.TodoApiClient
import com.kgmyshin.todo.infra.repository.ErrorLiveDataFactory
import com.kgmyshin.todo.infra.repository.InfraModule
import com.kgmyshin.todo.usecase.UseCaseModule
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module(
        includes = [
            InfraModule::class,
            UseCaseModule::class
        ]
)
class AppModule {

    @Provides
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    fun provideTodoApiClient(): TodoApiClient = TodoApiClient()

    @Singleton
    @Provides
    fun provideErrorLiveDataFactory(): ErrorLiveDataFactory = ErrorLiveDataFactory()

}