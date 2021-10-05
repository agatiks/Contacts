package com.example.lab3_contacts

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val permission: String = Manifest.permission.READ_CONTACTS
    private lateinit var contactAdapter: ContactAdapter
    val CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED) {
            showContacts()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), CODE)
        }
    }

    private fun showContacts() {
        val contacts = fetchAllContacts().sortedBy { it.name }

        //Toast.makeText(this, contacts[1].name, Toast.LENGTH_SHORT).show()
        contactAdapter = ContactAdapter(contacts) {
            toDialPhone(it.phoneNumber)
        }
        val manager = LinearLayoutManager(this)
        contact_list.apply{
            layoutManager = manager
            adapter = contactAdapter}
        Toast.makeText(this, "Found ${contactAdapter.itemCount} contacts", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CODE -> {
                if(grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showContacts()
                } else {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
