package com.soten.androidstudio.j2kb.ui.home.qna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.chat.ChatItem
import com.soten.androidstudio.j2kb.ui.home.qna.adapter.ChatItemAdapter
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import com.soten.androidstudio.j2kb.utils.TimeFormat

class QnaActivity : AppCompatActivity() {

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
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}
    }

    val recyclerView:RecyclerView by lazy {
        findViewById(R.id.qnaRecyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)

        recyclerView.adapter = chatAdapter

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        val chatDescription = findViewById<EditText>(R.id.messageEditText)
        initSendButton(chatDescription)

        chatDb.child("Chat").addChildEventListener(listener)
    }

    private fun initSendButton(chatDescription: EditText) {
        val sendButton = findViewById<ImageView>(R.id.sendButton)
        sendButton.setOnClickListener {
            if (chatDescription.text.toString() == "") {
                return@setOnClickListener
            }
            
            chatDb.child("Chat")
                .limitToLast(1)
                .get() // Chat db에서 마지막 요소를 가지고 옴
                .addOnSuccessListener {
                    // 마지막 요소 가져오기 서ㅓㅇ공
                    val text = it.children.last().child("id").value.toString() // 마지막 요소의 id
                    val des = it.children.last().child("description").value.toString() // 마지막 요소의 내용
                    val sendTime = it.children.last().child("time").value.toString() // 마지막 요소의 시:분

                    if (text == auth.currentUser?.uid.orEmpty() && sendTime == TimeFormat.sendTime()) { // 마지막요소의 id와 현재 로그인한 유저의 id가 같고, 보낸 시간과 현재 시:분이 같은 지
                        chatDb.child("Chat")
                            .child(it.children.last().key.toString())
                            .child("description")
                            .setValue("$des \n${chatDescription.text}")
                    } else {
                        val chatItem = ChatItem(
                            id = auth.currentUser?.uid.orEmpty(),
                            name = auth.currentUser?.displayName.orEmpty(),
                            description = chatDescription.text.toString()
                        )
                        chatDb.child("Chat")
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

}