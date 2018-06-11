package com.kgmyshin.todo.ui.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kgmyshin.todo.domain.TodoId
import com.kgmyshin.todo.domain.repository.ReadOnlyTodoRepository
import com.kgmyshin.todo.usecase.todo.DoneTodoUseCase
import com.kgmyshin.todo.usecase.todo.UndoneTodoUseCase
import io.reactivex.Scheduler

class TodoViewModelFactory(
        private val todoId: TodoId,
        private val readOnlyTodoRepository: ReadOnlyTodoRepository,
        private val doneTodoUseCase: DoneTodoUseCase,
        private val undoneTodoUseCase: UndoneTodoUseCase,
        private val uiScheduler: Scheduler
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            TodoViewModel(
                    todoId = todoId,
                    todoLiveDataFactory = TodoLiveDataFactory(
                            readOnlyTodoRepository,
                            doneTodoUseCase,
                            undoneTodoUseCase,
                            uiScheduler
                    )
            ) as T
}