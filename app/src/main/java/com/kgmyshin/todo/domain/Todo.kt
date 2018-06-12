package com.kgmyshin.todo.domain

import com.kgmyshin.todo.domain.support.Entity

class Todo(
        id: TodoId,
        val name: String,
        val description: String,
        private var done: Boolean
) : Entity<TodoId>(id) {

    fun done() {
        done = true
    }

    fun undone() {
        done = false
    }

}