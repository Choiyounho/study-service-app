package com.soten.androidstudio.j2kb.ui.home.goal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.goal.GoalCard

class GoalCardAdapter: ListAdapter<GoalCard, GoalCardAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(card: GoalCard) {
            val name = view.findViewById<TextView>(R.id.nameTextView)
            val firstGoal = view.findViewById<TextView>(R.id.firstGoalTextView)
            val secondGoal = view.findViewById<TextView>(R.id.secondGoalTextView)

            name.text = card.name
            firstGoal.text = card.firstGoal
            secondGoal.text = card.secondGoal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalCardAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_goal_card, parent, false))
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