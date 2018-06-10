package com.kgmyshin.todo.domain.support

abstract class Entity<out T : Identifier<*>>(val id: T) {

    override fun equals(other: Any?): Boolean {
        return (other is Entity<*>) && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}