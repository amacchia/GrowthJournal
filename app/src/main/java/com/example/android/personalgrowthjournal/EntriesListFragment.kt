package com.example.android.personalgrowthjournal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.personalgrowthjournal.Database.Entry
import java.util.*

class EntriesListFragment : Fragment() {

    val TAG = "EntriesListFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewModel: EntryViewModel

    private lateinit var latestEntryDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewAdapter = EntryListAdapter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
        viewModel.getEntries().observe(this, Observer {
            if (it != null) {
                (viewAdapter as EntryListAdapter).setData(it)

                // Store the date of the latest entry
                latestEntryDate = it[it.size - 1].entryDate
            }
        })
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        Log.i(TAG, "onCreateView()")

        val view = inflater.inflate(R.layout.entries_list, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            // Check if there's an entry for today
            val cal = Calendar.getInstance()
            val month = cal.get(Calendar.MONTH)
            val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
            val year = cal.get(Calendar.YEAR)

            cal.time = latestEntryDate

            val lastEntryMonth = cal.get(Calendar.MONTH)
            val lastEntryDayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
            val lastEntryYear = cal.get(Calendar.YEAR)


            if (month == lastEntryMonth && dayOfMonth == lastEntryDayOfMonth &&
                    year == lastEntryYear) { // There is already entry for today do not create a new one
                Toast.makeText(context, getString(R.string.existing_entry_msg), Toast.LENGTH_SHORT).show()
            } else {
                val fragmentManager = fragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragment_container, EntryFragment())
                fragmentTransaction?.addToBackStack(null) // Add to backstack to return with back button
                fragmentTransaction?.commit()
            }
        }

        val viewManager = LinearLayoutManager(context)
        viewManager.reverseLayout = true
        viewManager.stackFromEnd = true
        recyclerView = view.findViewById<RecyclerView>(R.id.entries_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divider)

        return view
    }

    fun deleteEntry(entry: Entry) {
        // Build AlertDialog
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.apply {
            setPositiveButton("OK") { _, _ ->
                viewModel.deleteEntry(entry)
            }

            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }

        builder?.setMessage(getString(R.string.delete_entry_message))
            ?.setTitle(getString(R.string.delete_entry_title))

        builder?.create()?.show()
    }
}