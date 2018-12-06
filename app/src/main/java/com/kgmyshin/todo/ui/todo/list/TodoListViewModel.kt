package com.kgmyshin.todo.ui.todo.list

import androidx.lifecycle.*
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.infra.repository.ErrorLiveData
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel
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
        private val uiScheduler: Scheduler,
        val errorLiveData: ErrorLiveData
) : ViewModel() {

    private val mutableTodoList = todoListLiveDataFactory.create()
    private val disposables = CompositeDisposable()

    private val filterWord: MutableLiveData<String> = MutableLiveData()
    private val filteredTodoList: LiveData<List<TodoBindingModel>> = Transformations.map(filterWord) { word ->
        mutableTodoList.value?.filter { it.description.contains(word) } ?: listOf()
    }

    val todoList: LiveData<List<TodoBindingModel>> = MediatorLiveData<List<TodoBindingModel>>().apply {
        addSource(mutableTodoList) {
            value = it
        }
        addSource(filteredTodoList) {
            value = it
        }
    }

    fun filter(filterWord: String?) {
        this.filterWord.value = filterWord
    }

    fun readMore() {
        mutableTodoList.readMore()
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
                            errorLiveData.value = it
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
                            errorLiveData.value = it
                        }
                )
                .addTo(disposables)
    }

    private fun changeDoneOfTodoItem(
            todoId: TodoId,
            done: Boolean
    ) {
        val snapshot = mutableTodoList.value ?: return
        val target = mutableTodoList.value?.find { it.id == todoId } ?: return
        mutableTodoList.value = snapshot.toMutableList().apply {
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