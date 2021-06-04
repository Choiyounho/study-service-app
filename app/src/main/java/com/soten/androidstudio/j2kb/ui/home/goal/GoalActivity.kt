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
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.INTENT_WEEK
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_GOAL
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS_NAME
import com.soten.androidstudio.j2kb.utils.GoalWriteTerms
import java.util.*

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

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            goalCardAdapter.notifyDataSetChanged()
        }

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
        val week = intent.getIntExtra(INTENT_WEEK, 0)
        goalDb = Firebase.database.reference.child(DB_GOAL).child(week.toString())
    }

    private fun initAddAdapter() {
        var name = ""
        var firstGoal = ""
        var secondGoal = ""

        addCardAdapter = AddCardAdapter(onItemClicked = {
            if (!GoalWriteTerms.terms()) {
                Toast.makeText(this, "목표 설정 기간이 아닙니다", Toast.LENGTH_SHORT).show()
                return@AddCardAdapter
            }

            store.collection(DB_USERS)
                .document(auth.currentUser?.uid.orEmpty())
                .get()
                .addOnSuccessListener {
                    name = it[DB_USERS_NAME].toString()
                    Log.d(TAG, "name $name")
                }

            val dialogView = View.inflate(this, R.layout.dialog, null)
            val dialog = AlertDialog.Builder(this)

            dialog.setTitle(DIALOG_TITLE)
                .setView(dialogView)
                .setPositiveButton(
                    DIALOG_POSITIVE
                ) { _, _ ->
                    firstGoal = dialogView.findViewById<EditText>(R.id.f).text.toString()
                    secondGoal = dialogView.findViewById<EditText>(R.id.s).text.toString()
                    val card = GoalCard(
                        id = auth.currentUser?.uid.orEmpty(),
                        firstGoal = firstGoal,
                        secondGoal = secondGoal,
                        name = name
                    )
                    goalDb.child(auth.currentUser?.uid.orEmpty())
                        .setValue(card)
                    Toast.makeText(this, TOAST_MESSAGE, Toast.LENGTH_SHORT).show()
                }.setNegativeButton(DIALOG_NEGATIVE) { _, _ -> }.show()
        })
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.cardRecyclerView)
        cardList.clear()

        goalCardAdapter = GoalCardAdapter()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd= true
        recyclerView.layoutManager = layoutManager
        concatAdapter = ConcatAdapter(goalCardAdapter, addCardAdapter)
        recyclerView.adapter = concatAdapter

        goalDb.addChildEventListener(listener)
    }

    companion object {
        private const val DIALOG_TITLE = "주간 목표"
        private const val DIALOG_POSITIVE = "목표 추가"
        private const val DIALOG_NEGATIVE = "취소"

        private const val TOAST_MESSAGE = "등록 완료"
    }

}