package com.example.android.personalgrowthjournal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.personalgrowthjournal.Authentication.FingerprintDialog
import com.example.android.personalgrowthjournal.Fragments.EntriesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fingerprintDialog = FingerprintDialog.newInstance()
        fingerprintDialog.show(supportFragmentManager, "authentication")

//        showEntriesListFragment()
    }

    private fun showEntriesListFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = EntriesListFragment()
        fragmentTransaction.add(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
