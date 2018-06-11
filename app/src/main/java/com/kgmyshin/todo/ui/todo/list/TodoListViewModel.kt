package com.kgmyshin.todo.ui.todo.list

import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

class TodoListViewModel(
        pagedKeyedTodoDataSourceFactory: PageKeyedTodoDataSourceFactory
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 5
    }

    val todoList = LivePagedListBuilder(
            pagedKeyedTodoDataSourceFactory,
            PagedList.Config.Builder()
                    .setInitialLoadSizeHint(PAGE_SIZE)
                    .setPageSize(PAGE_SIZE)
                    .build()
    ).build() as ComputableLiveData<TodoBindingModel>

}