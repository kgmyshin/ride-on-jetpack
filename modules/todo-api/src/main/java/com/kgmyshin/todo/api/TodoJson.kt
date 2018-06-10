package com.kgmyshin.todo.api

data class TodoJson(
        val id: String,
        val name: String,
        val description: String,
        val done: Boolean
)