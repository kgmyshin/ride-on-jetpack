package com.kgmyshin.todo.ui.todo.detail

import androidx.lifecycle.MutableLiveData
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel
import com.kgmyshin.todo.ui.todo.bindingModel.TodoConverter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class TodoLiveData(
        private val id: TodoId,
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val uiScheduler: Scheduler
) : MutableLiveData<TodoBindingModel>() {

    private val disposables = CompositeDisposable()

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