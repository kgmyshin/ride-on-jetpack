package com.kgmyshin.todo.ui.todo.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kgmyshin.todo.domain.TodoId
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
        pagedKeyedTodoDataSourceFactory: PageKeyedTodoDataSourceFactory,
        private val uiScheduler: Scheduler
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 5
    }

    private val disposables = CompositeDisposable()

    private val originalTodoList = LivePagedListBuilder(
            pagedKeyedTodoDataSourceFactory,
            PagedList.Config.Builder()
                    .setInitialLoadSizeHint(PAGE_SIZE)
                    .setPageSize(PAGE_SIZE)
                    .build()
    ).build()

    private val snapshotTodoList = MutableLiveData<PagedList<TodoBindingModel>>()

    val todoList = MediatorLiveData<PagedList<TodoBindingModel>>().apply {
        addSource(originalTodoList, { newValue ->
            value = newValue
        })
        addSource(snapshotTodoList, { newValue ->
            value = newValue
        })
    }

    fun done(todoId: TodoId) {
        doneTodoUseCase.execute(todoId)
                .subscribeOn(uiScheduler)
                .doOnSubscribe {
                    val snapshot = (originalTodoList.value?.snapshot() as? PagedList<TodoBindingModel>)
                            ?: return@doOnSubscribe
                    val target = snapshot.find { it.id == todoId } ?: return@doOnSubscribe
                    snapshot[snapshot.indexOf(target)] = target.copy(done = true)
                    snapshot.set(
                            snapshot.indexOf(target),
                            target.copy(done = true)
                    )
                    snapshotTodoList.value = snapshot
                }
                .subscribeBy(
                        onSuccess = {
                            originalTodoList.value?.dataSource?.invalidate()
                        },
                        onError = {
                            originalTodoList.value?.dataSource?.invalidate()
                        }
                )
                .addTo(disposables)
    }

    fun undone(todoId: TodoId) {
        undoneTodoUseCase.execute(todoId)
                .subscribeOn(uiScheduler)
                .doOnSubscribe {
                    val snapshot = (originalTodoList.value?.snapshot() as? PagedList<TodoBindingModel>)
                            ?: return@doOnSubscribe
                    val target = snapshot.find { it.id == todoId } ?: return@doOnSubscribe
                    snapshot[snapshot.indexOf(target)] = target.copy(done = false)
                    snapshot.set(
                            snapshot.indexOf(target),
                            target.copy(done = false)
                    )
                    snapshotTodoList.value = snapshot
                }
                .subscribeBy(
                        onSuccess = {
                            originalTodoList.value?.dataSource?.invalidate()
                        },
                        onError = {
                            originalTodoList.value?.dataSource?.invalidate()
                        }
                )
                .addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}