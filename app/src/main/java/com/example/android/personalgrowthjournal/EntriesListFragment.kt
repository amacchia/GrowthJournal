package com.example.android.personalgrowthjournal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EntriesListFragment : Fragment() {

    val TAG = "EntriesListFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)

        viewManager = LinearLayoutManager(context)
        viewAdapter = EntryListAdapter()


        viewModel.entries.observe(this, Observer {
            if (it != null) {
                (viewAdapter as EntryListAdapter).setData(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        Log.i(TAG, "onCreateView()")

        val view = inflater.inflate(R.layout.entries_list, container, false)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            val fragment = EntryFragment()
            fragmentTransaction?.add(R.id.fragment_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.hide(this)
            fragmentTransaction?.commit()
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.entries_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }


        return view
    }
}