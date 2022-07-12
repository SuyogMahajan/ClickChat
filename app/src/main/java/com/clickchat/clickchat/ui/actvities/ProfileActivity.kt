package com.clickchat.clickchat.ui.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.clickchat.clickchat.R
import com.clickchat.clickchat.databinding.ActivityProfileBinding
import com.clickchat.clickchat.ui.actvities.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val storage by lazy {
        FirebaseStorage.getInstance()
    }

    val userDatabase by lazy {
        FirebaseFirestore.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var user:com.clickchat.clickchat.data.User? = null

        val snap = userDatabase.collection("users").document(auth!!.uid.toString())
        snap.get().addOnSuccessListener {dataSnapShot ->

            user = dataSnapShot.toObject(com.clickchat.clickchat.data.User::class.java)

            binding.NameEditText.setText(user?.name)
            binding.statusEditText.setText(user?.status)
            Picasso.get().load(user?.thumbImage).into(binding.ProfileImage)

           Log.d("HLL", dataSnapShot.data?.get("name").toString())
        }

            binding.saveButton.setOnClickListener {
                  snap.update(mapOf(
                      "name" to binding.NameEditText.text.toString() ,
                      "status" to binding.statusEditText.text.toString()
                  ))
            }

    }
}