package com.soten.androidstudio.j2kb.ui.home.notice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.Notice

class NoticeAdapter(val onItemClicked: (Notice) -> Unit) : ListAdapter<Notice, NoticeAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(notice: Notice) {
            val title = view.findViewById<TextView>(R.id.titleTextView)
            val time = view.findViewById<TextView>(R.id.createdTimeForNoticeTextView)
            val name = view.findViewById<TextView>(R.id.writerNameTextView)

            title.text = notice.title
            time.text = notice.createdTime
            name.text = notice.nickname

            view.rootView.setOnClickListener {
                onItemClicked(notice)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_notice_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Notice>() {
            override fun areContentsTheSame(oldItem: Notice, newItem: Notice) =
                oldItem.createdTime == newItem.createdTime

            override fun areItemsTheSame(oldItem: Notice, newItem: Notice) =
                oldItem == newItem
        }
    }
}

