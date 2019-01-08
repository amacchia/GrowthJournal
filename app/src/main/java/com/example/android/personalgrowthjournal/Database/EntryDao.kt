package com.example.android.personalgrowthjournal.Database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE id = :id LIMIT 1")
    fun getEntryById(id: Int) : LiveData<Entry>

    @Insert
    fun insertEntry(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)

    @Update
    fun updateEntry(entry: Entry)

}