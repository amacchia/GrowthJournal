package com.example.android.personalgrowthjournal

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.android.personalgrowthjournal.Database.EntriesDatabase
import com.example.android.personalgrowthjournal.Database.Entry

class EntryViewModel(app: Application) : AndroidViewModel(app) {

    private var db: EntriesDatabase = EntriesDatabase.getInstance(app)
    var entries: LiveData<List<Entry>> = db.entryDao().getEntries()

}