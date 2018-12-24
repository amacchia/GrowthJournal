package com.example.android.personalgrowthjournal

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EntriesListFragment : Fragment() {

    val TAG = "EntriesListFragment"

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

        return view
    }
}