package com.example.android.personalgrowthjournal

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.personalgrowthjournal.Database.Entry

class EntryListAdapter: RecyclerView.Adapter<EntryListAdapter.EntryViewHolder>() {

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
        holder.textView.text = entryDataset[position].gratitudeEntry
    }

    fun setData(newData: List<Entry>) {
        entryDataset = newData
        notifyDataSetChanged()
    }


    class EntryViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}