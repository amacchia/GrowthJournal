package com.example.android.personalgrowthjournal

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.android.personalgrowthjournal.Database.EntriesDatabase
import com.example.android.personalgrowthjournal.Database.Entry

class EntryViewModel(app: Application) : AndroidViewModel(app) {

    private var db: EntriesDatabase = EntriesDatabase.getInstance(app)

    private lateinit var entries: LiveData<List<Entry>>

//    private var entries: LiveData<List<Entry>> = db.entryDao().getEntries()

    fun getEntries(): LiveData<List<Entry>> {
        if (!::entries.isInitialized) {
            entries = MutableLiveData()
            loadEntries()
        }
        return entries
    }

    private fun loadEntries() {
        entries = db.entryDao().getAllEntries()
    }

}