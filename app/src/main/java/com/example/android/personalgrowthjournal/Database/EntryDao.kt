package com.example.android.personalgrowthjournal.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries")
    fun getEntries(): LiveData<List<Entry>>

    @Insert
    fun insertEntry(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)

    @Update
    fun updateEntry(entry: Entry)

}