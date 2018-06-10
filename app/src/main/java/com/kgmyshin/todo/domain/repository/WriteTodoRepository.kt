package com.kgmyshin.todo.domain.repository

import com.kgmyshin.todo.domain.Todo
import io.reactivex.Single

interface WriteTodoRepository {

    fun store(todo: Todo): Single<Todo>
}