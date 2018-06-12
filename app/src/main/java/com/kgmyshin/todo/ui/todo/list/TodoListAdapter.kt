package com.kgmyshin.todo.ui.todo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

class TodoListAdapter : PagedListAdapter<TodoBindingModel, TodoViewHolder>(DIFF_ITEM_CALL_BACK) {

    var onTodoClickListener: OnTodoClickListener? = null
    var onToggleDoneListener: OnToggleDoneListener? = null

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
                    onTodoClickListener,
                    onToggleDoneListener
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