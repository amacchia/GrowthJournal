package com.example.android.personalgrowthjournal

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import com.example.android.personalgrowthjournal.Database.Entry
import java.text.SimpleDateFormat
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

        // Load the entry from the database
        val selectedEntryId = arguments?.getInt("entryId")
        val entryLiveData = selectedEntryId?.let { mViewModel.getEntryById(it) }

        entryLiveData?.observe(this, android.arch.lifecycle.Observer {
            Log.i("EntryFragment", "${it!!}")
            if (mNewEntry) {
                fillEntryPrompts(true)
            } else {
                mEntry = it
                fillEntryPrompts(false)
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.entry_fragment, container, false)

        val gratCardView = view.findViewById<CardView>(R.id.gratitude_entry_card_view)
        val gratPrompt = gratCardView.findViewById<TextView>(R.id.text_prompt)
        gratPrompt.text = getString(R.string.gratitude_prompt)
        mGratitudeEditText = gratCardView.findViewById(R.id.edit_text_entry)

        val succCardView = view.findViewById<CardView>(R.id.successful_entry_card_view)
        val succPrompt = succCardView.findViewById<TextView>(R.id.text_prompt)
        succPrompt.text = getString(R.string.successful_prompt)
        mSuccessfulEditText = succCardView.findViewById(R.id.edit_text_entry)

        val wellCardView = view.findViewById<CardView>(R.id.well_entry_card_view)
        val wellPrompt = wellCardView.findViewById<TextView>(R.id.text_prompt)
        wellPrompt.text = getString(R.string.well_prompt)
        mWellEditText = wellCardView.findViewById(R.id.edit_text_entry)

        val betterCardView = view.findViewById<CardView>(R.id.better_entry_card_view)
        val betterPrompt = betterCardView.findViewById<TextView>(R.id.text_prompt)
        betterPrompt.text = getString(R.string.better_prompt)
        mBetterEditText = betterCardView.findViewById(R.id.edit_text_entry)

        val ventCardView = view.findViewById<CardView>(R.id.vent_entry_card_view)
        val ventPrompt = ventCardView.findViewById<TextView>(R.id.text_prompt)
        ventPrompt.text = getString(R.string.vent_prompt)
        mVentEditText = ventCardView.findViewById(R.id.edit_text_entry)

        return view
    }

    private fun fillEntryPrompts(isNewEntry: Boolean) {
        if (!isNewEntry) {
            activity?.title = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(mEntry.entryDate)
            mGratitudeEditText.setText(mEntry.gratitudeEntry)
            mSuccessfulEditText.setText(mEntry.successEntry)
            mWellEditText.setText(mEntry.wellEntry)
            mBetterEditText.setText(mEntry.betterEntry)
            mVentEditText.setText(mEntry.ventEntry)
        } else {
            activity?.title = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date())
            mWellEditText.hint = getString(R.string.come_back_later_hint)
            mBetterEditText.hint = getString(R.string.come_back_later_hint)
        }
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