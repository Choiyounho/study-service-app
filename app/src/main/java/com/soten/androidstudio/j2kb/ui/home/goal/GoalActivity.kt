package com.soten.androidstudio.j2kb.ui.home.goal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.goal.GoalCard
import com.soten.androidstudio.j2kb.ui.home.goal.adapter.AddCardAdapter
import com.soten.androidstudio.j2kb.ui.home.goal.adapter.GoalCardAdapter
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS
import com.soten.androidstudio.j2kb.utils.TimeFormat

class GoalActivity : AppCompatActivity() {

    private lateinit var goalCardAdapter: GoalCardAdapter
    private lateinit var addCardAdapter: AddCardAdapter
    private lateinit var concatAdapter: ConcatAdapter

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val goal = snapshot.getValue(GoalCard::class.java)
            goal ?: return

            cardList.add(goal)
            goalCardAdapter.submitList(cardList)

            goalCardAdapter.notifyDataSetChanged()
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}
    }

    private val auth = Firebase.auth
    private lateinit var goalDb: DatabaseReference
    private val store = Firebase.firestore

    private val cardList = mutableListOf<GoalCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        initGoalDb()
        initAddAdapter()
        initRecyclerView()
    }

    private fun initGoalDb() {
        val week = intent.getIntExtra("week", 0)
        if (week == 11) {
            goalDb = Firebase.database.reference.child("11")
        }
        if (week == 12) {
            goalDb = Firebase.database.reference.child("12")
        }
        if (week == 13) {
            goalDb = Firebase.database.reference.child("13")
        }
    }


    private fun initAddAdapter() {
        var name = ""
        var firstGoal = ""
        var secondGoal = ""

        addCardAdapter = AddCardAdapter(onItemClicked = {
            val dialogView = View.inflate(this, R.layout.dialog, null)

            store.collection(DB_USERS)
                .document(auth.currentUser?.uid.orEmpty())
                .get()
                .addOnSuccessListener {
                    name = it["nickname"].toString()
                    Log.d(TAG, "name $name")
                }

            val dialog = AlertDialog.Builder(this)

            dialog.setTitle("제목")
                .setView(dialogView)
                .setPositiveButton(
                    "입력"
                ) { _, _ ->
                    firstGoal = dialogView.findViewById<EditText>(R.id.f).text.toString()
                    secondGoal = dialogView.findViewById<EditText>(R.id.s).text.toString()
                    val card = GoalCard(
                        id = TimeFormat.createdTimeForId(),
                        firstGoal = firstGoal,
                        secondGoal = secondGoal,
                        name = name
                    )
                    goalDb.child(TimeFormat.createdTimeForId())
                        .setValue(card)
                }.setNegativeButton("취소") { _, _ -> }.show()
        })
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.cardRecyclerView)
        cardList.clear()

        goalCardAdapter = GoalCardAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        concatAdapter = ConcatAdapter(goalCardAdapter, addCardAdapter)
        recyclerView.adapter = concatAdapter

        goalDb.addChildEventListener(listener)
    }

}