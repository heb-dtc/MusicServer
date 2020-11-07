package com.heb.soli

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heb.soli.api.Media

class RadioStreamsAdapter : RecyclerView.Adapter<RadioStreamViewHolder>() {

    private var items = mutableListOf<Media>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioStreamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_radio_stream, parent, false)
        return RadioStreamViewHolder(view)
    }

    override fun onBindViewHolder(holder: RadioStreamViewHolder, position: Int) {
        holder.title.text = items[position].name
        holder.back
    }

    override fun getItemCount() = items.size

    fun setItems(radios: List<Media>) {
        this.items = radios.toMutableList()
        notifyDataSetChanged()
    }

}

class RadioStreamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.name)
}
