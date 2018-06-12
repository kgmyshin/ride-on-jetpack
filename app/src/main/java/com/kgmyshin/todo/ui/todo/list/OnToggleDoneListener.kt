package com.kgmyshin.todo.ui.todo.list

import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

interface OnToggleDoneListener {

    fun onToggle(
            onOff: Boolean,
            bindingModel: TodoBindingModel
    )

}