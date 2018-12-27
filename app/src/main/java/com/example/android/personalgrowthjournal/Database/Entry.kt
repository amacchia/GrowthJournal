package com.example.android.personalgrowthjournal.Database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry(
    val gratitudeEntry: String, val successEntry: String,
    val wellEntry: String, val betterEntry: String, val ventEntry: String
) {

    @PrimaryKey(autoGenerate = true)
    var key: Int? = null

}