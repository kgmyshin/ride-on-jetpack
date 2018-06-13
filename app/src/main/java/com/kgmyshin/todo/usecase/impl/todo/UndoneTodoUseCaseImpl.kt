package com.kgmyshin.todo.usecase.impl.todo

import com.kgmyshin.todo.domain.Todo
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.TodoRepository
import com.kgmyshin.todo.usecase.todo.UndoneTodoUseCase
import io.reactivex.Single
import javax.inject.Inject

class UndoneTodoUseCaseImpl @Inject constructor(
        private val repository: TodoRepository
) : UndoneTodoUseCase {

    override fun execute(todoId: TodoId): Single<Todo> =
            repository.findById(todoId)
                    .flatMapSingle { todo ->
                        todo.undone()
                        repository.store(todo)
                    }

}