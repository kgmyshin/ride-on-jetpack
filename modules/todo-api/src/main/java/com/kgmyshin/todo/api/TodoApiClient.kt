package com.kgmyshin.todo.api

import io.reactivex.Completable
import io.reactivex.Single

class TodoApiClient {

    companion object {

        private const val NUM_OF_PAGE = 20
    }

    private val todoList: MutableList<TodoJson> = (1..100).fold(mutableListOf()) { acc, i ->
        acc.apply {
            acc.add(TodoJson("id$i", "name$i", "description$i", false))
        }
    }

    fun getTodoList(page: Int): Single<List<TodoJson>> = Single.fromCallable {
        if (page < 0 || (page - 1) * NUM_OF_PAGE > todoList.size) {
            return@fromCallable listOf<TodoJson>()
        }
        val start = page * NUM_OF_PAGE
        val end = if ((page + 1) * NUM_OF_PAGE > todoList.size) {
            todoList.size - 1
        } else {
            (page + 1) * NUM_OF_PAGE - 1
        }
        return@fromCallable todoList.slice(start..end)
    }

    fun getTodo(todoId: String): Single<TodoJson> = Single.fromCallable {
        todoList.find { it.id == todoId }
    }

    fun createTodo(todo: TodoJson): Completable = Completable.fromAction {
        todoList.add(todo)
    }

    fun removeTodo(todoId: String): Completable = Completable.fromAction {
        todoList.removeAll { it.id == todoId }
    }

    fun updateTodo(todo: TodoJson): Single<TodoJson> = Single.fromCallable {
        todoList.find { it.id == todo.id }?.let {
            todoList.set(
                    todoList.indexOf(it),
                    todo
            )
        }
    }

}
