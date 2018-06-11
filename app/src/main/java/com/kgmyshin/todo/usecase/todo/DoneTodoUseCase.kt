package com.kgmyshin.todo.usecase.todo

import com.kgmyshin.todo.domain.Todo
import com.kgmyshin.todo.domain.TodoId
import io.reactivex.Single

interface DoneTodoUseCase {

    fun execute(todoId: TodoId): Single<Todo>

}