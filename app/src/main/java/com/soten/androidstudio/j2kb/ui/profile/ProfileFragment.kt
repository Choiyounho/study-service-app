package com.soten.androidstudio.j2kb.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val store = Firebase.firestore
    private val auth = Firebase.auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nickname: TextView = getView()?.findViewById(R.id.text_profile_name) as TextView
        val logout: Button = getView()?.findViewById(R.id.btn_kakao_logout) as Button

        initProfileImageView()

        logout.setOnClickListener {

        }
    }

    private fun initProfileImageView() {
        val profile: ImageView = view?.findViewById(R.id.image_profile) as ImageView
        store.collection(DB_USERS)
            .document(auth.currentUser?.uid.orEmpty())
            .get()
            .addOnSuccessListener {
                Glide.with(profile.context)
                    .load(it["profileImage"].toString())
                    .into(profile)
            }
    }

}