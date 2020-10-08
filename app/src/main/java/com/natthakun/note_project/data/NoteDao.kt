package com.natthakun.note_project.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: Note) : Long

    @Update
    suspend fun updateNote(note: Note) : Int

    @Delete
    suspend fun deleteNote(note: Note) : Int

    @Query("DELETE FROM note_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM note_table")
    fun getAllNote(): LiveData<List<Note>>
}