package com.kgmyshin.todo.ui.todo.list

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kgmyshin.todo.ui.todo.bindingModel.TodoBindingModel

class TodoListAdapter : PagedListAdapter<TodoBindingModel, RecyclerView.ViewHolder>(DIFF_ITEM_CALL_BACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}