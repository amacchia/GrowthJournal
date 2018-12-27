package com.example.android.personalgrowthjournal

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import com.example.android.personalgrowthjournal.Database.EntriesDatabase
import com.example.android.personalgrowthjournal.Database.Entry

class EntryFragment : Fragment() {

    lateinit var mDb: EntriesDatabase
    lateinit var mGratitudeEditText: EditText
    lateinit var mSuccessfulEditText: EditText
    lateinit var mWellEditText: EditText
    lateinit var mBetterEditText: EditText
    lateinit var mVentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mDb = EntriesDatabase.getInstance(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.entry_fragment, container, false)

        mGratitudeEditText = view.findViewById(R.id.edit_text_gratitude_entry)
        mSuccessfulEditText = view.findViewById(R.id.edit_text_successful_entry)
        mWellEditText = view.findViewById(R.id.edit_text_well_entry)
        mBetterEditText = view.findViewById(R.id.edit_text_better_entry)
        mVentEditText = view.findViewById(R.id.edit_text_vent_entry)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.entry_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.save_entry_menu_item -> {

                val entry = Entry(
                    mGratitudeEditText.text.toString(),
                    mSuccessfulEditText.text.toString(),
                    mWellEditText.text.toString(),
                    mBetterEditText.text.toString(),
                    mVentEditText.text.toString())

                class UpdateDbTask :AsyncTask<Void, Void, Boolean>() {

                    override fun onPostExecute(result: Boolean?) {
                        super.onPostExecute(result)
                    }

                    override fun doInBackground(vararg p0: Void?): Boolean {
                        // Insert or Update db
                        mDb.entryDao().insertEntry(entry)

                        return true // If op was successful
                    }
                }

                UpdateDbTask().execute()
            }
        }

        return true
    }

}