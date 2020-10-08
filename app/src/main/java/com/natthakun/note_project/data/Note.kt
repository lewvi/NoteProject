package com.natthakun.note_project.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id : Int,

    @ColumnInfo(name = "note_title")
    var title : String,

    @ColumnInfo(name = "note_des")
    var des : String
)