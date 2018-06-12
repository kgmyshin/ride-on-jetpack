package com.kgmyshin.todo.infra.repository

import com.kgmyshin.todo.api.TodoJson
import com.kgmyshin.todo.domain.Todo
import com.kgmyshin.todo.domain.TodoId

object TodoConverter {

    fun convertToModel(todoList: List<TodoJson>): List<Todo> =
            todoList.map { convertToModel(it) }

    fun convertToModel(todoJson: TodoJson): Todo =
            Todo(
                    id = TodoId(todoJson.id),
                    name = todoJson.name,
                    description = todoJson.description,
                    done = todoJson.done
            )

    fun convertToJson(todo: Todo): TodoJson =
            TodoJson(
                    id = todo.id.value,
                    name = todo.name,
                    description = todo.description,
                    done = todo.hasDone()
            )

}