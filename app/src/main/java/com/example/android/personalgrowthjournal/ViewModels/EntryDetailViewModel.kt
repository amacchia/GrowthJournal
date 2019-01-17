package com.example.android.personalgrowthjournal.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.android.personalgrowthjournal.Database.EntriesDatabase
import com.example.android.personalgrowthjournal.Database.Entry

class EntryDetailViewModel(app: Application): AndroidViewModel(app) {

    private var mDb: EntriesDatabase = EntriesDatabase.getInstance(app)

    fun getEntryById(id: Int): LiveData<Entry> {
        return mDb.entryDao().getEntryById(id)
    }

    fun insertEntry(entry: Entry) {

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                mDb.entryDao().insertEntry(entry)
                return null
            }
        }.execute()

    }

    fun updateEntry(entry: Entry) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                mDb.entryDao().updateEntry(entry)
                return null
            }
        }.execute()
    }
}