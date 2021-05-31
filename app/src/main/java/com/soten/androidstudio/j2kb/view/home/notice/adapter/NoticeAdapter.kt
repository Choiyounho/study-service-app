package com.soten.androidstudio.j2kb.view.home.notice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.NoticePost

class NoticeAdapter(
    private val noticePostList: ArrayList<NoticePost>
) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNoticeId: TextView = itemView.findViewById(R.id.notice_id)
        val itemNoticeTitle: TextView = itemView.findViewById(R.id.notice_title)
        val itemWriteDate: TextView = itemView.findViewById(R.id.write_date)
        val itemWriter: TextView = itemView.findViewById(R.id.writer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNoticeId.text = noticePostList[position].id.toString()
        holder.itemNoticeTitle.text = noticePostList[position].title
        holder.itemWriteDate.text = noticePostList[position].createdTime.toString()
        holder.itemWriter.text = noticePostList[position].nickname
    }

    override fun getItemCount() = noticePostList.size

}

