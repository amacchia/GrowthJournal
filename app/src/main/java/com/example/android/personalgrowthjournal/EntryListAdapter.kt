package com.example.android.personalgrowthjournal

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.personalgrowthjournal.Database.Entry
import java.text.SimpleDateFormat
import java.util.*

class EntryListAdapter(val fragmentManager: FragmentManager?) : RecyclerView.Adapter<EntryListAdapter.EntryViewHolder>() {

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


    inner class EntryViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView), View.OnClickListener {

        init {
            textView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val selectedEntryId = entryDataset[adapterPosition].id
            Log.i("EntryViewHolder", "$selectedEntryId")

            val fragment = EntryFragment()
            val args = Bundle()
            if (selectedEntryId != null) {
                args.putInt("entryId", selectedEntryId)
            }
            fragment.arguments = args

            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        }

    }

}