package com.natthakun.note_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.natthakun.note_project.data.Note
import com.natthakun.note_project.data.NoteDatabase
import com.natthakun.note_project.data.NoteRepository
import com.natthakun.note_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = NoteDatabase.getInstance(application).noteDao
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
        binding.myViewModel = noteViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        noteViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun initRecyclerView(){
        binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteRecyclerViewAdapter({selectedItem:Note->listItemClicked(selectedItem)})
        binding.noteRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList(){
        noteViewModel.note.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(note: Note){
        //Toast.makeText(this,"selected name is ${subscriber.name}",Toast.LENGTH_LONG).show()
        noteViewModel.initUpdateAndDelete(note)
    }
}