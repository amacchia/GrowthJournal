package com.example.android.personalgrowthjournal

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.personalgrowthjournal.Database.Entry
import java.text.SimpleDateFormat
import java.util.*

class EntryListAdapter(val mFragment: EntriesListFragment) : RecyclerView.Adapter<EntryListAdapter.EntryViewHolder>() {

    private var entryDataset = emptyList<Entry>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EntryViewHolder {

        val textView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.entry_recycler_item, viewGroup, false) as TextView

        return EntryViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return entryDataset.size
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val dateString = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(entryDataset[position].entryDate)
        holder.textView.text = dateString
    }

    fun setData(newData: List<Entry>) {
        entryDataset = newData
        notifyDataSetChanged()
    }


    inner class EntryViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView),
        View.OnClickListener, View.OnLongClickListener {

        init {
            textView.isClickable = true
            textView.isLongClickable = true
            textView.setOnClickListener(this)
            textView.setOnLongClickListener(this)
        }

        override fun onClick(view: View?) {
            val selectedEntryId = entryDataset[adapterPosition].id
            Log.i("EntryViewHolder", "$selectedEntryId")

            val newFragment = EntryFragment()
            val args = Bundle()
            if (selectedEntryId != null) {
                args.putInt("entryId", selectedEntryId)
            }
            newFragment.arguments = args

            val fragmentTransaction = mFragment.fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, newFragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        override fun onLongClick(view: View?): Boolean {
            mFragment.deleteEntry(entryDataset[adapterPosition])
            return true
        }

    }

}