package com.example.testapp.ui.main.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.config.Config
import com.example.testapp.data.remote.ToDoService
import com.example.testapp.data.repository.MainRepository
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.ui.main.viewmodel.MainViewModel
import com.example.testapp.ui.main.viewmodel.MainViewModelFactory
import com.example.testapp.ui.main.adapter.ToDoListAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val repository by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ToDoService::class.java)
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MainRepository(repository))
    }

    private val views by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ToDoListAdapter()
    }

    // Flag to track if a new item has been added
    private var newItemAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(views.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        loadTodoList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViews() {
        views.listRecyclerView.layoutManager = LinearLayoutManager(this)
        views.listRecyclerView.adapter = adapter

        viewModel.toDoItems.observe(this){
            // Whenever the to-do list changes, update the adapter

            // Hide the progress bar
            views.progressBar.visibility = View.GONE

            // Update the adapter with the new list
            adapter.swapList(it)

            if(newItemAdded){
                adapter.notifyItemInserted(0)
                views.listRecyclerView.smoothScrollToPosition(0)
                newItemAdded = false
            } else {
                adapter.notifyDataSetChanged()
            }
        }

        views.addNoteButton.setOnClickListener {
            // Show the AddNoteSheetFragment
            AddNoteSheetFragment{ note ->
                // Add the new note
                newItemAdded = true
                viewModel.addNote(note)
            }.show(supportFragmentManager, "AddNoteSheetFragment")
        }
    }

    private fun loadTodoList(){
        // Show the progress bar and call viewmodel to start loading the data
        views.progressBar.visibility = View.VISIBLE
        viewModel.loadToDoItems()
    }
}