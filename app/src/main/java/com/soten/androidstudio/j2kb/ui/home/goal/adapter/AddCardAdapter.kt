package com.soten.androidstudio.j2kb.ui.home.goal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.databinding.ItemAddCardBinding


class AddCardAdapter(
    val onItemClicked: () -> Unit
) : RecyclerView.Adapter<AddCardAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemAddCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_card, parent, false)

        return ViewHolder(ItemAddCardBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked()
        }
    }

    override fun getItemCount() = 1
}