package com.soten.androidstudio.j2kb.ui.home.goal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.databinding.ItemGoalCardBinding
import com.soten.androidstudio.j2kb.model.goal.GoalCard

class GoalCardAdapter : ListAdapter<GoalCard, GoalCardAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemGoalCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: GoalCard) {
            binding.nameTextView.text = card.name
            binding.firstGoalTextView.text = card.firstGoal
            binding.secondGoalTextView.text = card.secondGoal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalCardAdapter.ViewHolder {
        return ViewHolder(
            ItemGoalCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GoalCardAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GoalCard>() {
            override fun areContentsTheSame(oldItem: GoalCard, newItem: GoalCard) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: GoalCard, newItem: GoalCard) =
                oldItem == newItem
        }
    }

}