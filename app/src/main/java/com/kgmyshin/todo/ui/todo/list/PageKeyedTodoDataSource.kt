package com.kgmyshin.todo.ui.todo.list

import androidx.paging.PageKeyedDataSource
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel
import com.kgmyshin.todo.ui.todo.bindingModel.TodoConverter
import io.reactivex.rxkotlin.subscribeBy

class PageKeyedTodoDataSource(
        private val readOnlyTodoRepository: ReadOnlyTodoRepository
) : PageKeyedDataSource<Int, TodoBindingModel>() {

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, TodoBindingModel>
    ) {
        readOnlyTodoRepository.findAllByPage(0).subscribeBy(
                onSuccess = { todoList ->
                    callback.onResult(
                            TodoConverter.convertToBindingModel(todoList),
                            null,
                            1
                    )
                }
        )
    }

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, TodoBindingModel>
    ) {
        readOnlyTodoRepository.findAllByPage(params.key).subscribeBy(
                onSuccess = { todoList ->
                    callback.onResult(
                            TodoConverter.convertToBindingModel(todoList),
                            if (todoList.isEmpty()) {
                                null
                            } else {
                                params.key + 1
                            }
                    )
                }
        )
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, TodoBindingModel>
    ) {
        // do nothing.
    }
}