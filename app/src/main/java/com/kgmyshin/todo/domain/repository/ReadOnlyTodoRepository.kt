package com.kgmyshin.todo.domain.repository

import com.kgmyshin.todo.domain.Todo
import com.kgmyshin.todo.domain.TodoId
import io.reactivex.Maybe
import io.reactivex.Single

interface ReadOnlyTodoRepository {

    fun findById(todoId: TodoId): Maybe<Todo>

    fun findAllByPage(page: Int): Single<List<Todo>>

}