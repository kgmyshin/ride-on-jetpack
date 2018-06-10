package com.kgmyshin.todo.domain

import com.kgmyshin.todo.domain.support.Entity

class Todo(
        id: TodoId,
        val name: String,
        val description: String,
        val done: Boolean
) : Entity<TodoId>(id)