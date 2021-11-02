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
    private val permission_read: String = Manifest.permission.READ_CONTACTS
    //private val permission_sms: String = Manifest.permission.SEND_SMS
    private lateinit var contactAdapter: ContactAdapter
    val CODE_READ = 1
    //val CODE_SMS = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, permission_read)
                == PackageManager.PERMISSION_GRANTED) {
            showContacts()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission_read), CODE_READ)
        }
    }

    private fun showContacts() {
        val contacts = fetchAllContacts().sortedBy { it.name }
        //Toast.makeText(this, contacts[1].name, Toast.LENGTH_SHORT).show()
        contactAdapter = ContactAdapter(contacts, { toDialPhone(it.phoneNumber) },
                { /*if(ContextCompat.checkSelfPermission(this, permission_sms)
                        == PackageManager.PERMISSION_GRANTED) {*/
                    sendSMS(it.phoneNumber)
                /*} else {
                    ActivityCompat.requestPermissions(this, arrayOf(permission_read), CODE_SMS)
                }*/
                })
        val manager = LinearLayoutManager(this)
        contact_list.apply{
            layoutManager = manager
            adapter = contactAdapter}
        val plurals = resources.getQuantityString(R.plurals.contact, contactAdapter.itemCount, contactAdapter.itemCount)
        Toast.makeText(this, "Found $plurals", Toast.LENGTH_LONG).show()
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CODE_READ -> {
                if(grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showContacts()
                } else {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
            /*CODE_SMS -> {
                if(grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }*/
        }
    }
}
