package com.kgmyshin.todo.api

class TodoApiClient {

    private val todoList = mutableListOf(
            TodoJson("id1", "name1", "description1", false),
            TodoJson("id2", "name2", "description2", false),
            TodoJson("id3", "name3", "description3", false),
            TodoJson("id4", "name4", "description4", false),
            TodoJson("id5", "name5", "description5", false)
    )

    fun getTodoList(): List<TodoJson> = todoList

    fun createTodo(todo: TodoJson) = todoList.add(todo)

    fun removeTodo(todoId: String) = todoList.removeIf { it.id == todoId }

    fun updateTodo(todo: TodoJson) {
        todoList.find { it.id == todo.id }?.let {
            todoList.set(
                    todoList.indexOf(it),
                    todo
            )
        }
    }

}
