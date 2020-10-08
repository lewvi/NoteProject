package com.natthakun.note_project.data

class NoteRepository(private val dao : NoteDao) {

    val note = dao.getAllNote()

    suspend fun insert(note: Note):Long{
        return dao.insertNote(note)
    }

    suspend fun update(note: Note):Int{
        return dao.updateNote(note)
    }

    suspend fun delete(note: Note) : Int{
        return dao.deleteNote(note)
    }

    suspend fun deleteAll() : Int{
        return dao.deleteAll()
    }
}