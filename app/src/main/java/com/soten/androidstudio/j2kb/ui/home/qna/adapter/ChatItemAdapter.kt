package com.soten.androidstudio.j2kb.ui.home.qna.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.chat.ChatItem
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS

class ChatItemAdapter : ListAdapter<ChatItem, ChatItemAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(chatItem: ChatItem) {
            val nickname = view.findViewById<TextView>(R.id.nicknameTextView)
            val currentTime = view.findViewById<TextView>(R.id.sendTimeTextView)
            val message = view.findViewById<TextView>(R.id.messageTextView)
            val thumbnail = view.findViewById<ImageView>(R.id.thumbnailImageView)

            nickname.text = chatItem.name
            currentTime.text = chatItem.time
            message.text = chatItem.description

            Glide.with(thumbnail.context)
                .load(chatItem.thumbnail)
                .transform(
                    CenterCrop(),
                    RoundedCorners(dpToPx(thumbnail.context, CORNERS_DP))
                )
                .into(thumbnail)
            val store = Firebase.firestore
            val auth = Firebase.auth
            store.collection(DB_USERS)
                .document(auth.currentUser?.uid.orEmpty())
                .get()
                .addOnSuccessListener {
                    if (chatItem.id == auth.currentUser?.uid.orEmpty()) {
                        Glide.with(thumbnail.context)
                            .load(it["profileImage"])
                            .transform(
                                CenterCrop(),
                                RoundedCorners(dpToPx(thumbnail.context, CORNERS_DP))
                            )
                            .into(thumbnail)
                    } else {
                        // 다른 사람 사진
                        store.collection(DB_USERS)
                            .document(chatItem.id)
                            .get()
                            .addOnSuccessListener { another ->
                                Glide.with(thumbnail.context)
                                    .load(another["profileImage"])
                                    .transform(
                                        CenterCrop(),
                                        RoundedCorners(dpToPx(thumbnail.context, CORNERS_DP))
                                    )
                                    .into(thumbnail)
                            }
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ChatItemAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_chat, parent, false))
    }

    override fun onBindViewHolder(holder: ChatItemAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatItem>() {
            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem) =
                oldItem.time == newItem.time

            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem) =
                oldItem == newItem
        }

        private const val CORNERS_DP = 12
    }
}