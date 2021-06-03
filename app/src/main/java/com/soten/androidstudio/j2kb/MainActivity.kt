package com.soten.androidstudio.j2kb

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.model.user.Role
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USER_GRADE

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val store = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        checkGrade()

        initNavView()
    }

    private fun checkGrade() {
        store.collection(DB_USERS)
            .document(auth.currentUser?.uid.orEmpty())
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "뭐야 " + auth.currentUser?.uid.orEmpty())
                val myGrade = result?.get(DB_USER_GRADE)
                val restrictGrade = Role.NORMAL.toString()
                if (myGrade == restrictGrade || myGrade == null) {
                    Toast.makeText(this, "회원 인증 후 이용 하세요", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener {
                Log.d(TAG, "뭘까")
                Toast.makeText(this, "회원 인증 후 이용 하세요", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    private fun initNavView() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Log.d(TAG, "로그아웃")
                auth.signOut()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}