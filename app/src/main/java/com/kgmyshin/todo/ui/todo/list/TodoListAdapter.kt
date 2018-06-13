package com.kgmyshin.todo.ui.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

class TodoListAdapter : ListAdapter<TodoBindingModel, TodoViewHolder>(DIFF_ITEM_CALL_BACK) {

    var onClickTodoClickListener: OnClickTodoClickListener? = null
    var onClickDoneTodoListener: OnClickDoneTodoListener? = null
    var onClickUndoneTodoListener: OnClickUndoneTodoListener? = null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TodoViewHolder = TodoViewHolder.create(
            LayoutInflater.from(parent.context),
            parent,
            false
    )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindTo(
                    it,
                    onClickTodoClickListener,
                    onClickDoneTodoListener,
                    onClickUndoneTodoListener
            )
        }
    }

    companion object {
        private val DIFF_ITEM_CALL_BACK = object : DiffUtil.ItemCallback<TodoBindingModel>() {
            override fun areItemsTheSame(
                    oldItem: TodoBindingModel,
                    newItem: TodoBindingModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                    oldItem: TodoBindingModel,
                    newItem: TodoBindingModel
            ): Boolean = oldItem == newItem
        }

    }

}