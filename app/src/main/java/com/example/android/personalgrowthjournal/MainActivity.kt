package com.example.android.personalgrowthjournal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.android.personalgrowthjournal.Authentication.FingerprintDialog
import com.example.android.personalgrowthjournal.Fragments.EntriesListFragment

class MainActivity : AppCompatActivity(), FingerprintDialog.Callback {
    override fun onAuthSuccess() {
       showEntriesListFragment()
    }

    override fun onAuthError() {
        showToast("Auth Error")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fingerprintDialog = FingerprintDialog.newInstance()
        fingerprintDialog.setCallback(this)
        fingerprintDialog.show(supportFragmentManager, "authentication")
    }

    private fun showEntriesListFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = EntriesListFragment()
        fragmentTransaction.add(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}
