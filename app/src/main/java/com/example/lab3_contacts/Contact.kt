package com.example.lab3_contacts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast

data class Contact(val name: String, val phoneNumber: String)

fun Context.fetchAllContacts(): List<Contact> {
    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        .use { cursor ->
            if (cursor == null) return emptyList()
            val builder = ArrayList<Contact>()
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) ?: "N/A"
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ?: "N/A"
                builder.add(Contact(name, phoneNumber))
            }
            return builder
        }
}

fun Context.toDialPhone(phoneNumber: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_DIAL
        data = Uri.parse("tel:${phoneNumber}") }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(this, getString(R.string.dial_error), Toast.LENGTH_SHORT).show()
    }
}

fun Context.sendSMS(phoneNumber: String, message: String = getString(R.string.sms_test)) {
    val intent = Intent().apply {
        action = Intent.ACTION_SENDTO
        data = Uri.parse("smsto:${phoneNumber}")
    }
    intent.putExtra("sms_body", message)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(this, getString(R.string.sendto_error), Toast.LENGTH_SHORT).show()
    }
}