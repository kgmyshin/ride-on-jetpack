package com.kgmyshin.todo.ui.todo.detail

import androidx.lifecycle.LiveData
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel
import com.kgmyshin.todo.ui.todo.bindingModel.TodoConverter
import com.kgmyshin.todo.usecase.todo.DoneTodoUseCase
import com.kgmyshin.todo.usecase.todo.UndoneTodoUseCase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TodoLiveData(
        private val id: TodoId,
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val doneTodoUseCase: DoneTodoUseCase,
        private val undoneTodoUseCase: UndoneTodoUseCase,
        private val uiScheduler: Scheduler
) : LiveData<TodoBindingModel>() {

    private val disposables = CompositeDisposable()

    fun done() {
        value?.let { todo ->
            if (todo.done) {
                return
            }
            doneTodoUseCase.execute(todo.id)
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe {
                        value = todo.copy(done = true)
                    }
                    .doOnError {
                        value = todo.copy(done = false)
                    }
                    .subscribeBy(
                            onError = {
                                TODO("エラーハンドリング")
                            }
                    )
                    .addTo(disposables)
        }
    }

    fun undone() {
        value?.let { todo ->
            if (!todo.done) {
                return
            }
            undoneTodoUseCase.execute(todo.id)
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe {
                        value = todo.copy(done = false)
                    }
                    .doOnError {
                        value = todo.copy(done = true)
                    }
                    .subscribeBy(
                            onError = {
                                TODO("エラーハンドリング")
                            }
                    )
                    .addTo(disposables)
        }
    }

    override fun onActive() {
        super.onActive()
        readOnlyTodoRepository.findById(id)
                .subscribeOn(uiScheduler)
                .subscribeBy(
                        onSuccess = { todo ->
                            value = TodoConverter.convertToBindingModel(todo)
                        },
                        onError = {
                            TODO("エラーハンドリング")
                        }
                )
                .addTo(disposables)
    }

    override fun onInactive() {
        disposables.clear()
        super.onInactive()
    }

}