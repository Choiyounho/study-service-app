package com.soten.androidstudio.j2kb.ui.home.qna

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.databinding.ActivityQnaBinding
import com.soten.androidstudio.j2kb.model.chat.ChatItem
import com.soten.androidstudio.j2kb.ui.home.qna.adapter.ChatItemAdapter
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import com.soten.androidstudio.j2kb.utils.TimeFormat

class QnaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQnaBinding

    private val chatDb = Firebase.database.reference
    private val auth = Firebase.auth

    private val chatList = mutableListOf<ChatItem>()
    private val chatAdapter = ChatItemAdapter()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val chatItem = snapshot.getValue(ChatItem::class.java)
            chatItem ?: return

            chatList.add(chatItem)
            chatAdapter.submitList(chatList)
            chatAdapter.notifyDataSetChanged()
            binding.qnaRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val chatItem = snapshot.getValue(ChatItem::class.java)
            chatItem ?: return

            chatList.removeAt(chatList.size - 1)
            chatList.add(chatItem)
            chatAdapter.submitList(chatList)
            chatAdapter.notifyDataSetChanged()
            binding.qnaRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQnaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatLayoutManager = LinearLayoutManager(this)
        chatLayoutManager.stackFromEnd = true

        binding.qnaRecyclerView.apply {
            adapter = chatAdapter
            layoutManager = chatLayoutManager
        }

        initSendButton(binding.messageEditText)

        chatDb.child(DB_CHAT).addChildEventListener(listener)
    }

    private fun initSendButton(chatDescription: EditText) {
        binding.sendButton.setOnClickListener {
            if (chatDescription.text.isEmpty()) {
                return@setOnClickListener
            }
            
            chatDb.child(DB_CHAT)
                .limitToLast(1)
                .get() // Chat db에서 마지막 요소를 가지고 옴
                .addOnSuccessListener {
                    // 마지막 요소 가져오기 성공
                    val text = it.children.last().child(DB_CHAT_ID).value.toString() // 마지막 요소의 id
                    val des = it.children.last().child(DB_CHAT_DESCRIPTION).value.toString() // 마지막 요소의 내용
                    val sendTime = it.children.last().child(DB_CHAT_TIME).value.toString() // 마지막 요소의 시:분

                    if (text == auth.currentUser?.uid.orEmpty() && sendTime == TimeFormat.sendTime()) { // 마지막요소의 id와 현재 로그인한 유저의 id가 같고, 보낸 시간과 현재 시:분이 같은 지
                        chatDb.child(DB_CHAT)
                            .child(it.children.last().key.toString())
                            .child(DB_CHAT_DESCRIPTION)
                            .setValue("$des \n${chatDescription.text}")
                    } else {
                        val chatItem = ChatItem(
                            id = auth.currentUser?.uid.orEmpty(),
                            name = auth.currentUser?.displayName.orEmpty(),
                            description = chatDescription.text.toString()
                        )
                        chatDb.child(DB_CHAT)
                            .child(TimeFormat.createdTimeForId())
                            .setValue(chatItem)
                    }
                    chatDescription.text.clear()
                }.addOnFailureListener {
                    Log.d(TAG, "실패")
                }
            chatAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val DB_CHAT = "Chat"
        private const val DB_CHAT_DESCRIPTION = "description"
        private const val DB_CHAT_TIME = "time"
        private const val DB_CHAT_ID = "id"
    }

}