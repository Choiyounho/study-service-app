package com.soten.androidstudio.j2kb.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.soten.androidstudio.j2kb.LoginActivity
import com.soten.androidstudio.j2kb.MainActivity
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.databinding.FragmentProfileBinding
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var selectedUri: Uri? = null

    private val store = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        auth = Firebase.auth

        initNicknameTextView()
        initProfileImageView()
        initLogoutButton()

        val button = binding.submitButton
        button.setOnClickListener {
            if (selectedUri != null) {
                val photoUri = selectedUri ?: return@setOnClickListener
                uploadPhoto(photoUri,
                    successHandler = { uri ->
                        uploadProfile(uri)
                        Toast.makeText(context, "성공 핸들러", Toast.LENGTH_SHORT).show()
                    },
                    errorHandler = {
                        Toast.makeText(context, "사진 업로드에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    private fun uploadProfile(uri: String) {
        store.collection(DB_USERS)
            .document(auth.currentUser?.uid.orEmpty())
            .update("profileImage", uri)
    }

    private fun initNicknameTextView() {
        binding.profileNameTextView.text = auth.currentUser?.displayName
    }

    private fun initLogoutButton() {
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity as MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun uploadPhoto(uri: Uri, successHandler: (String) -> Unit, errorHandler: () -> Unit) {
        val fileName = "${auth.currentUser?.uid}.png"
        storage.reference.child("user/profile").child(fileName)
            .putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    storage.reference.child("user/profile").child(fileName)
                        .downloadUrl
                        .addOnSuccessListener { uri ->
                            successHandler(uri.toString())
                        }.addOnFailureListener {
                            errorHandler()
                        }
                } else {
                    errorHandler()
                }
            }
    }

    private fun initProfileImageView() {
        store.collection(DB_USERS)
            .document(auth.currentUser?.uid.orEmpty())
            .get()
            .addOnSuccessListener {
                Glide.with(binding.imageProfile.context)
                    .load(it["profileImage"].toString())
                    .into(binding.imageProfile)
            }

        binding.imageProfile.setOnClickListener {
            when {
                context?.let { it1 ->
                    ContextCompat.checkSelfPermission(
                        it1,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1010
                    )
                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(context)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 2020)
            }
            .create()
            .show()
    }

    private fun startContentProvider() { // image 필터링 하여 모든 사진을 가져옴
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2020)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1010 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startContentProvider()
                } else {
                    Toast.makeText(context, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            2020 -> {
                val uri = data?.data
                if (uri != null) {
                    binding.imageProfile.setImageURI(uri)
                    selectedUri = uri
                } else {
                    Toast.makeText(context, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(context, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}