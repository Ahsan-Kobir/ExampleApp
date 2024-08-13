package com.example.testapp.ui.main.adapter

import android.media.RouteListingPreference.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.model.ToDoItem
import com.example.testapp.databinding.ItemNotesBinding

class ToDoListAdapter: RecyclerView.Adapter<ToDoListAdapter.ToDoListHolder>() {

    private var items = emptyList<ToDoItem>()
    set(value) {
        field = value
    }

   fun swapList(newItems: List<ToDoItem>){
       items = newItems
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListHolder {
        return ToDoListHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ToDoListHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            title.text = item.title
            checkBox.isChecked = item.completed
        }
    }

    override fun getItemCount(): Int = items.size

    class ToDoListHolder(val binding: ItemNotesBinding):RecyclerView.ViewHolder(binding.root)
}