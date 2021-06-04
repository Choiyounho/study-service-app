package com.soten.androidstudio.j2kb.ui.home.qna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
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

    val recyclerView:RecyclerView by lazy {
        findViewById(R.id.qnaRecyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)

        chatDb.child("Chat").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatItem::class.java)
                chatItem ?: return

                chatList.add(chatItem)
                chatAdapter.submitList(chatList)
                chatAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatItem::class.java)
                chatItem ?: return

                chatList.add(chatItem)
                chatAdapter.submitList(chatList)
                chatAdapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })

        recyclerView.adapter = chatAdapter

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        val chatDescription = findViewById<EditText>(R.id.messageEditText)
        val sendButton = findViewById<ImageView>(R.id.sendButton)
        sendButton.setOnClickListener {
            chatDb.child("Chat")
                .limitToLast(1)
                .get()
                .addOnSuccessListener {
                    val text = it.children.last().child("id").value.toString()
                    val des = it.children.last().child("description").value.toString()
                    val sendTime = it.children.last().child("time").value.toString()

                    if (text == auth.currentUser?.uid.orEmpty() && sendTime == TimeFormat.sendTime()) {
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