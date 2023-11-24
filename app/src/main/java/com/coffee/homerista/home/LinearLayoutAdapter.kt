package com.coffee.homerista.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.coffee.homerista.R

class LinearLayoutAdapter(private val items: List<String>) :
    RecyclerView.Adapter<LinearLayoutAdapter.LinearLayoutViewHolder>() {

    class LinearLayoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linearLayout: LinearLayout = view.findViewById(R.id.linearLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearLayoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_first_recyclerview_items, parent, false)
        return LinearLayoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinearLayoutViewHolder, position: Int) {
        val itemPosition = position % items.size
        // Bind data to the views inside the LinearLayout as needed
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE
}
