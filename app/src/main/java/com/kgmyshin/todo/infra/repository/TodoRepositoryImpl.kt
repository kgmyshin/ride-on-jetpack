package com.kgmyshin.todo.infra.repository

import android.util.LruCache
import com.kgmyshin.todo.api.TodoApiClient
import com.kgmyshin.todo.domain.Todo
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.TodoRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImpl @Inject constructor(
        private val todoApiClient: TodoApiClient
) : TodoRepository {

    private val idCache = LruCache<TodoId, Todo>(1000)
    private val pageCache = LruCache<Int, List<Todo>>(1000)

    override fun findById(todoId: TodoId): Maybe<Todo> = if (idCache[todoId] == null) {
        todoApiClient.getTodo(todoId.value).toMaybe().map {
            TodoConverter.convertToModel(it)
        }.doOnSuccess {
            idCache.put(it.id, it)
        }
    } else {
        Maybe.just(idCache[todoId])
    }

    override fun findAllByPage(page: Int): Single<List<Todo>> = if (pageCache[page] == null) {
        todoApiClient.getTodoList(page)
                .map { TodoConverter.convertToModel(it) }
                .doOnSuccess {
                    pageCache.put(
                            page,
                            it
                    )
                    it.forEach {
                        idCache.put(it.id, it)
                    }
                }
    } else {
        Single.just(pageCache[page])
    }

    override fun store(todo: Todo): Single<Todo> =
            todoApiClient.updateTodo(TodoConverter.convertToJson(todo))
                    .map { TodoConverter.convertToModel(it) }
                    .doOnSuccess { stored ->
                        idCache.put(stored.id, stored)
                        pageCache.snapshot().keys.forEach { page ->
                            val pageList = pageCache[page].toMutableList()
                            if (pageList.any { it.id == stored.id }) {
                                pageList[pageList.indexOf(stored)] = stored
                                pageCache.put(
                                        page,
                                        pageList
                                )
                            }
                        }
                    }

}