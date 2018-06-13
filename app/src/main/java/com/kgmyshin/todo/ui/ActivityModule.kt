package com.kgmyshin.todo.ui

import com.kgmyshin.todo.ui.todo.TodoActivity
import com.kgmyshin.todo.ui.todo.TodoActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(
            modules = [TodoActivityModule::class]
    )
    abstract fun todoActivity(): TodoActivity
}

