package com.soten.androidstudio.j2kb.ui.home.notice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.databinding.ItemNoticeViewBinding
import com.soten.androidstudio.j2kb.model.post.Notice

class NoticeAdapter(val onItemClicked: (Notice) -> Unit) :
    ListAdapter<Notice, NoticeAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemNoticeViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: Notice) {
            binding.noticeTitleTextView.text = notice.title
            binding.createdTimeForNoticeTextView.text = notice.createdTime
            binding.writerNameTextView.text = notice.nickname

            binding.root.setOnClickListener {
                onItemClicked(notice)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoticeViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

