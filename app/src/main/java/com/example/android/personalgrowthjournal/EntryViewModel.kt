package com.example.android.personalgrowthjournal

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.android.personalgrowthjournal.Database.EntriesDatabase
import com.example.android.personalgrowthjournal.Database.Entry

class EntryViewModel(app: Application) : AndroidViewModel(app) {

    private var db: EntriesDatabase = EntriesDatabase.getInstance(app)

    private var entries = db.entryDao().getAllEntries()

    fun getEntries(): LiveData<List<Entry>> {
        return entries
    }

    fun deleteEntry(entry: Entry) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                db.entryDao().deleteEntry(entry)
                return null
            }
        }.execute()
    }

}