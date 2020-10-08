package com.natthakun.note_project

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natthakun.note_project.data.Note
import com.natthakun.note_project.data.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(), Observable {

    val note = repository.note
    private var isUpdateOrDelete = false
    private lateinit var noteToUpdateOrDelete: Note

    @Bindable
    val inputTitle = MutableLiveData<String>()

    @Bindable
    val inputDes = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {

        if (inputTitle.value == null) {
            statusMessage.value = Event("Please enter Title")
        } else if (inputDes.value == null) {
            statusMessage.value = Event("you don't write des...")
        }else {
            if (isUpdateOrDelete) {
                noteToUpdateOrDelete.title = inputTitle.value!!
                noteToUpdateOrDelete.des = inputDes.value!!
                update(noteToUpdateOrDelete)
            } else {
                val title = inputTitle.value!!
                val des = inputDes.value!!
                insert(Note(0, title, des))
                inputTitle.value = null
                inputDes.value = null
            }
        }


    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(noteToUpdateOrDelete)
        } else {
            clearAll()
        }

    }

    fun insert(note: Note) = viewModelScope.launch {
        val newRowId = repository.insert(note)
        if (newRowId > -1) {
            statusMessage.value = Event("Note Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun update(note: Note) = viewModelScope.launch {
        val noOfRows = repository.update(note)
        if (noOfRows > 0) {
            inputTitle.value = null
            inputDes.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(note: Note) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(note)

        if (noOfRowsDeleted > 0) {
            inputTitle.value = null
            inputDes.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun initUpdateAndDelete(note: Note) {
        inputTitle.value = note.title
        inputDes.value = note.des
        isUpdateOrDelete = true
        noteToUpdateOrDelete = note
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"

    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}