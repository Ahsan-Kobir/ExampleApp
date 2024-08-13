package com.example.testapp.data.remote

import com.example.testapp.data.model.ToDoItem
import retrofit2.Response
import retrofit2.http.GET

interface ToDoService {
    @GET("todos")
    suspend fun getToDoItems(): Response<List<ToDoItem>>
}