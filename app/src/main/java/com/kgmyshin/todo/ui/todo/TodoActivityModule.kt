package com.kgmyshin.todo.ui.todo

import com.kgmyshin.todo.ui.todo.detail.TodoFragment
import com.kgmyshin.todo.ui.todo.list.TodoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TodoActivityModule {

    @ContributesAndroidInjector
    abstract fun todoListFragment(): TodoListFragment

    @ContributesAndroidInjector
    abstract fun todoFragment(): TodoFragment

}