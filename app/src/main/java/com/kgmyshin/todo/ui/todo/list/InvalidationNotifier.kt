package com.kgmyshin.todo.ui.todo.list

class InvalidationTracker {

    private val observers = mutableListOf<Observer>()

    fun invalidate() {
        observers.forEach {
            it.onInvalidate()
        }
    }

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    interface Observer {
        fun onInvalidate()
    }

}