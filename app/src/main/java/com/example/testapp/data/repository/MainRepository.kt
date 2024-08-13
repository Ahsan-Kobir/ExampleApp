package com.example.testapp.data.repository

import com.example.testapp.data.remote.ToDoService

class MainRepository(private val apiService: ToDoService) {
    suspend fun getToDoItems() = apiService.getToDoItems()
}