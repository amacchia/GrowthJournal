package com.example.android.personalgrowthjournal

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
        InsertTask().execute(entry)
    }

    fun updateEntry(entry: Entry) {
        UpdateTask().execute(entry)
    }

    private inner class InsertTask: AsyncTask<Entry, Void, Void>() {
        override fun doInBackground(vararg entry: Entry?): Void? {
            entry[0]?.let { mDb.entryDao().insertEntry(it) }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            cancel(true)
        }
    }

    private inner class UpdateTask: AsyncTask<Entry, Void, Void>() {
        override fun doInBackground(vararg entry: Entry?): Void? {

            entry[0]?.let { mDb.entryDao().updateEntry(it) }


            return null
        }
    }

}