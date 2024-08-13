package com.example.testapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.model.ToDoItem
import com.example.testapp.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val _toDoItems = MutableLiveData<List<ToDoItem>>()
    val toDoItems: LiveData<List<ToDoItem>> = _toDoItems

    fun loadToDoItems(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getToDoItems()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main){
                    _toDoItems.value = response.body()
                }
            }
        }
    }

    fun addNote(note: String) {
        // Create a new note and add it to the list
        val newNote = ToDoItem(
            id = _toDoItems.value?.size ?: 0,
            title = note,
            completed = false,
          )
        val currentList = _toDoItems.value.orEmpty().toMutableList()
        currentList.add(0, newNote) // Add to the beginning of the list
        _toDoItems.value = currentList
    }
}