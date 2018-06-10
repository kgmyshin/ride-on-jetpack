package com.kgmyshin.todo.infra.repository

import com.kgmyshin.todo.api.TodoApiClient
import com.kgmyshin.todo.domain.Todo
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.TodoRepository
import io.reactivex.Maybe
import io.reactivex.Single

class TodoRepositoryImpl(
        val todoApiClient: TodoApiClient
) : TodoRepository {

    override fun findById(todoId: TodoId): Maybe<Todo> = Maybe.create { emitter ->
        val todoJson = todoApiClient.getTodo(todoId.value)
        if (todoJson != null) {
            emitter.onSuccess(TodoConverter.convertToModel(todoJson))
        } else {
            emitter.onComplete()
        }
    }

    override fun findAll(): Single<List<Todo>> = Single.fromCallable {
        todoApiClient.getTodoList().let { TodoConverter.convertToModel(it) }
    }

    override fun store(todo: Todo): Single<Todo> = Single.fromCallable {
        todoApiClient.updateTodo(TodoConverter.convertToJson(todo))
        todo
    }

}