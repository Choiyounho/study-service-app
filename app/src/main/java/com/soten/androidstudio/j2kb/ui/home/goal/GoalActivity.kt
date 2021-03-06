package com.soten.androidstudio.j2kb.ui.home.goal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.databinding.ActivityGoalBinding
import com.soten.androidstudio.j2kb.databinding.DialogBinding
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

    private lateinit var binding: ActivityGoalBinding
    private lateinit var dialogBinding: DialogBinding

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
        binding = ActivityGoalBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initGoalDb()
        initAddAdapter()
        initRecyclerView()
        initSwipeRefreshLayout()
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
                Toast.makeText(this, TOAST_FAIL, Toast.LENGTH_SHORT).show()
                return@AddCardAdapter
            }

            store.collection(DB_USERS)
                .document(auth.currentUser?.uid.orEmpty())
                .get()
                .addOnSuccessListener {
                    name = it[DB_USERS_NAME].toString()
                    Log.d(TAG, "name $name")
                }

            dialogBinding = DialogBinding.inflate(LayoutInflater.from(applicationContext))
            val dialog = AlertDialog.Builder(this)

            dialog.setView(dialogBinding.root)
                .setTitle(DIALOG_TITLE)
                .setPositiveButton(
                    DIALOG_POSITIVE
                ) { _, _ ->
                    firstGoal = dialogBinding.f.text.toString()
                    secondGoal = dialogBinding.s.text.toString()
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
        cardList.clear()

        goalCardAdapter = GoalCardAdapter()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding.cardRecyclerView.layoutManager = layoutManager
        concatAdapter = ConcatAdapter(goalCardAdapter, addCardAdapter)
        binding.cardRecyclerView.adapter = concatAdapter

        goalDb.addChildEventListener(listener)
    }

    private fun initSwipeRefreshLayout() {
        binding.goalSwipeRefreshLayout.setOnRefreshListener {
            val intent = intent
            finish()
            startActivity(intent)
        }
    }

    companion object {
        private const val DIALOG_TITLE = "주간 목표"
        private const val DIALOG_POSITIVE = "목표 추가"
        private const val DIALOG_NEGATIVE = "취소"

        private const val TOAST_MESSAGE = "등록 완료"
        private const val TOAST_FAIL = "목표 설정 기간이 아닙니다"
    }

}