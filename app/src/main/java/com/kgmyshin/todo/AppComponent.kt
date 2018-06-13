package com.kgmyshin.todo

import com.kgmyshin.todo.ui.ActivityModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            ActivityModule::class
        ]
)
interface AppComponent : AndroidInjector<TodoApplication>