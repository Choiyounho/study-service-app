package com.soten.androidstudio.j2kb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.model.user.Member
import com.soten.androidstudio.j2kb.model.user.Role
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    private val store = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initGoogleSignInClient()

        auth = Firebase.auth

        initLoginButton()
    }

    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun initLoginButton() {
        val loginButton = findViewById<ImageView>(R.id.login)
        loginButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_LOGIN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.displayName)
                firebaseAuthWithGoogle(account.idToken!!, account.displayName)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, displayName: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val member = instanceMember(displayName)

                    addUserInfoToFirestore(member)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun instanceMember(displayName: String?): Member {
        return Member(
            auth.currentUser!!.uid,
            displayName,
            "",
            Role.NORMAL,
            createdTime()
        )
    }

    private fun createdTime(): String {
        val currentDateTime = Calendar.getInstance().time
        return SimpleDateFormat("yyMMddHHmmss", Locale.KOREA).format(currentDateTime)
    }

    private fun addUserInfoToFirestore(member: Member) {
        store.collection("Users")
            .get()
            .addOnSuccessListener { result ->
                if (isExistMember(result)) {
                    return@addOnSuccessListener
                } else {
                    addUsersDbToMember(member)
                    startMainActivity()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun isExistMember(result: QuerySnapshot): Boolean {
        for (document in result) {
            Log.d(TAG, "${document.id} => ${document.data}")
            if (document.get("id") == auth.currentUser?.uid.orEmpty()) {
                startMainActivity()
                return true
            }
        }
        return false
    }

    private fun addUsersDbToMember(member: Member) {
        store.collection("Users")
            .add(member)
            .addOnSuccessListener {
                Log.d(TAG, "유저 추가 성공")
            }.addOnFailureListener {
                Log.d(TAG, "유저 추가 실패")
            }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val GOOGLE_LOGIN_REQUEST_CODE = 9001
    }

}