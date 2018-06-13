package com.kgmyshin.todo.ui.todo.detail

import androidx.lifecycle.ViewModel
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.usecase.todo.DoneTodoUseCase
import com.kgmyshin.todo.usecase.todo.UndoneTodoUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TodoViewModel(
        todoId: TodoId,
        private val doneTodoUseCase: DoneTodoUseCase,
        private val undoneTodoUseCase: UndoneTodoUseCase,
        todoLiveDataFactory: TodoLiveDataFactory,
        private val uiScheduler: Scheduler
) : ViewModel() {

    val todoLiveData = todoLiveDataFactory.create(todoId)

    private val disposables = CompositeDisposable()

    fun done() {
        todoLiveData.value?.let { todo ->
            if (todo.done) {
                return
            }
            doneTodoUseCase.execute(todo.id)
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe {
                        todoLiveData.value = todo.copy(done = true)
                    }
                    .subscribeBy(
                            onError = {
                                todoLiveData.value = todo.copy(done = false)
                                TODO("エラーハンドリング")
                            }
                    )
                    .addTo(disposables)
        }
    }

    fun undone() {
        todoLiveData.value?.let { todo ->
            if (todo.done) {
                return
            }
            undoneTodoUseCase.execute(todo.id)
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe {
                        todoLiveData.value = todo.copy(done = false)
                    }
                    .subscribeBy(
                            onError = {
                                todoLiveData.value = todo.copy(done = true)
                                TODO("エラーハンドリング")
                            }
                    )
                    .addTo(disposables)
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
