package com.kgmyshin.todo.api

import io.reactivex.Completable
import io.reactivex.Single

class TodoApiClient {

    companion object {

        private const val NUM_OF_PAGE = 5
    }

    private val todoList = mutableListOf(
            TodoJson("id1", "name1", "description1", false),
            TodoJson("id2", "name2", "description2", false),
            TodoJson("id3", "name3", "description3", false),
            TodoJson("id4", "name4", "description4", false),
            TodoJson("id5", "name5", "description5", false),

            TodoJson("id21", "name1", "description1", false),
            TodoJson("id22", "name2", "description2", false),
            TodoJson("id23", "name3", "description3", false),
            TodoJson("id24", "name4", "description4", false),
            TodoJson("id25", "name5", "description5", false),

            TodoJson("id31", "name1", "description1", false),
            TodoJson("id32", "name2", "description2", false),
            TodoJson("id33", "name3", "description3", false),
            TodoJson("id34", "name4", "description4", false),
            TodoJson("id35", "name5", "description5", false),

            TodoJson("id45", "name5", "description5", false)
    )

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
