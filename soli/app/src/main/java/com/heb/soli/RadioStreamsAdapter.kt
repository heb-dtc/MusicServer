package com.heb.soli

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.heb.soli.api.Media

class RadioStreamsAdapter(private val callback: ItemCallback) : RecyclerView.Adapter<RadioStreamViewHolder>() {

    interface ItemCallback {
        fun onClicked(media: Media)
    }

    private val colors = listOf("#e63946", "#f1faee", "#a8dadc", "#457b9d", "#1d3557")
    private var items = mutableListOf<Media>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioStreamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_radio_stream, parent, false)
        return RadioStreamViewHolder(view)
    }

    override fun onBindViewHolder(holder: RadioStreamViewHolder, position: Int) {
        holder.title.text = items[position].name

        val colorIndex = if (position >= colors.size) (colors.indices).random() else position
        holder.card.setBackgroundColor(Color.parseColor(colors[colorIndex]))

        holder.card.setOnClickListener {
            callback.onClicked(items[position])
        }
    }

    override fun getItemCount() = items.size

    fun setItems(radios: List<Media>) {
        this.items = radios.toMutableList()
        notifyDataSetChanged()
    }
}

class RadioStreamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById<TextView>(R.id.name)
    val card: CardView = view.findViewById<CardView>(R.id.card_view)
}
