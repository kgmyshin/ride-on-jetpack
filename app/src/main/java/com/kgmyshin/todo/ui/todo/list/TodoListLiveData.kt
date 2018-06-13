package com.kgmyshin.todo.ui.todo.list

import androidx.lifecycle.MutableLiveData
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel
import com.kgmyshin.todo.ui.todo.bindingModel.TodoConverter
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class TodoListLiveData(
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val uiScheduler: Scheduler
) : MutableLiveData<List<TodoBindingModel>>() {

    private val disposables = CompositeDisposable()
    private var lastPage = 0

    fun readMore() {
        readOnlyTodoRepository.findAllByPage(lastPage + 1)
                .subscribeOn(uiScheduler)
                .subscribeBy(
                        onSuccess = { todoList ->
                            val addedList = TodoConverter.convertToBindingModel(todoList)
                            value = value?.let { oldList ->
                                oldList.toMutableList().apply { addAll(addedList) }
                            } ?: addedList
                            if (addedList.isNotEmpty()) {
                                lastPage += 1
                            }
                        },
                        onError = {
                            TODO("エラーハンドリング")
                        }
                )
    }

    override fun onActive() {
        super.onActive()
        readOnlyTodoRepository.findAllByPage(0)
                .subscribeOn(uiScheduler)
                .subscribeBy(
                        onSuccess = { todoList ->
                            value = TodoConverter.convertToBindingModel(todoList)
                        },
                        onError = {
                            TODO("エラーハンドリング")
                        }
                )

    }

    override fun onInactive() {
        disposables.clear()
        super.onInactive()
    }
}