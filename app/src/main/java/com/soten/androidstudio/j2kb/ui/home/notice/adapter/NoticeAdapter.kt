package com.soten.androidstudio.j2kb.ui.home.notice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.NoticePost

class NoticeAdapter : ListAdapter<NoticePost, NoticeAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(noticePost: NoticePost) {
            val title = view.findViewById<TextView>(R.id.titleTextView)
            val time = view.findViewById<TextView>(R.id.createdTimeForNoticeTextView)
            val name = view.findViewById<TextView>(R.id.writerNameTextView)

            title.text = noticePost.title
            time.text = noticePost.createdTime
            name.text = noticePost.nickname

//            view.rootView.setOnClickListener {
//                onItemClicked(noticePost)
//            }
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
        val diffUtil = object : DiffUtil.ItemCallback<NoticePost>() {
            override fun areContentsTheSame(oldItem: NoticePost, newItem: NoticePost) =
                oldItem.createdTime == newItem.createdTime

            override fun areItemsTheSame(oldItem: NoticePost, newItem: NoticePost) =
                oldItem == newItem
        }
    }
}

