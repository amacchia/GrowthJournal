package com.example.android.personalgrowthjournal.Database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "entries")
data class Entry(
    var gratitudeEntry: String?, var successEntry: String?,
    var wellEntry: String?, var betterEntry: String?,
    var ventEntry: String?, val entryDate: Date
) {

    @PrimaryKey(autoGenerate = true)
    var key: Int? = null

}