package com.kgmyshin.todo.ui.todo.list

import androidx.lifecycle.ViewModel
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.usecase.todo.DoneTodoUseCase
import com.kgmyshin.todo.usecase.todo.UndoneTodoUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TodoListViewModel(
        private val doneTodoUseCase: DoneTodoUseCase,
        private val undoneTodoUseCase: UndoneTodoUseCase,
        todoListLiveDataFactory: TodoListLiveDataFactory,
        private val uiScheduler: Scheduler
) : ViewModel() {

    private val disposables = CompositeDisposable()

    val todoList = todoListLiveDataFactory.create()

    fun readMore() {
        todoList.readMore()
    }

    fun done(todoId: TodoId) {
        doneTodoUseCase.execute(todoId)
                .subscribeOn(uiScheduler)
                .doOnSubscribe {
                    changeDoneOfTodoItem(
                            todoId,
                            true
                    )
                }
                .subscribeBy(
                        onError = {
                            changeDoneOfTodoItem(
                                    todoId,
                                    false
                            )
                        }
                )
                .addTo(disposables)
    }

    fun undone(todoId: TodoId) {
        undoneTodoUseCase.execute(todoId)
                .subscribeOn(uiScheduler)
                .doOnSubscribe {
                    changeDoneOfTodoItem(
                            todoId,
                            false
                    )
                }
                .subscribeBy(
                        onError = {
                            changeDoneOfTodoItem(
                                    todoId,
                                    true
                            )
                        }
                )
                .addTo(disposables)
    }

    private fun changeDoneOfTodoItem(
            todoId: TodoId,
            done: Boolean
    ) {
        val snapshot = todoList.value ?: return
        val target = todoList.value?.find { it.id == todoId } ?: return
        todoList.value = snapshot.toMutableList().apply {
            set(
                    snapshot.indexOf(target),
                    target.copy(
                            done = done
                    )
            )
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}