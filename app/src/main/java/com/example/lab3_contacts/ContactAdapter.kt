package com.example.lab3_contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class ContactAdapter (val contacts: List<Contact>, val onClick: (Contact) -> Unit):
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val holder = ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item, parent, false))
        holder.itemView.setOnClickListener {
            onClick(contacts[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = contacts[position].name
        holder.phoneNumber.text = contacts[position].phoneNumber
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.name
        val phoneNumber: TextView = v.phoneNumber
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}
