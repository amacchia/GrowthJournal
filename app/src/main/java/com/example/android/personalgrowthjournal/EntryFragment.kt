package com.example.android.personalgrowthjournal

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.EditText
import com.example.android.personalgrowthjournal.Database.Entry
import java.util.*

class EntryFragment : Fragment() {

    private lateinit var mGratitudeEditText: EditText
    private lateinit var mSuccessfulEditText: EditText
    private lateinit var mWellEditText: EditText
    private lateinit var mBetterEditText: EditText
    private lateinit var mVentEditText: EditText
    private lateinit var mViewModel: EntryDetailViewModel

    private lateinit var mEntry: Entry // Will be initialized if an Entry is being updated

    var mNewEntry = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mViewModel = ViewModelProviders.of(this).get(EntryDetailViewModel::class.java)

        mNewEntry = arguments == null
        if (!mNewEntry) {
            // Load the entry from the database
            val selectedEntryId = arguments?.getInt("entryId")
            val entryLiveData = selectedEntryId?.let { mViewModel.getEntryById(it) }

            entryLiveData?.observe(this, android.arch.lifecycle.Observer {
                Log.i("EntryFragment", "${it!!}")
                mEntry = it
            })
        }
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
                finishFragment()
            }
        }

        return true
    }


    private fun finishFragment() {

        if (mNewEntry) {
            mEntry = Entry(
                mGratitudeEditText.text.toString(),
                mSuccessfulEditText.text.toString(),
                mWellEditText.text.toString(),
                mBetterEditText.text.toString(),
                mVentEditText.text.toString(),
                Date()
            )

            mViewModel.insertEntry(mEntry)
        } else {
            mEntry.gratitudeEntry = mGratitudeEditText.text.toString()
            mEntry.successEntry = mSuccessfulEditText.text.toString()
            mEntry.wellEntry = mWellEditText.text.toString()
            mEntry.betterEntry = mBetterEditText.text.toString()
            mEntry.ventEntry = mVentEditText.text.toString()
            mViewModel.updateEntry(mEntry)
        }

        // Use this instead of pop backstack because it will return to previous state without updated data
        // At least I think that's how it works
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, EntriesListFragment())
            ?.commit()
    }

}