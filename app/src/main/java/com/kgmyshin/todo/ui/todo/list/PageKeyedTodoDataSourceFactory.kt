package com.kgmyshin.todo.ui.todo.list

import androidx.paging.DataSource
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

class PageKeyedTodoDataSourceFactory(
        private val readOnlyTodoRepository: ReadOnlyTodoRepository
) : DataSource.Factory<Int, TodoBindingModel>() {

    override fun create(): DataSource<Int, TodoBindingModel> =
            PageKeyedTodoDataSource(readOnlyTodoRepository)

}