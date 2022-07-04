package com.whatsappclone.whatsappclone.ui.actvities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.whatsappclone.whatsappclone.databinding.ActivityChatBinding
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider
import com.whatsappclone.whatsappclone.data.User

const val UID = "uid"
const val PHOTO = "photo"
const val NAME = "name"

class ChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatBinding

    private val name by lazy{ intent.getStringExtra(NAME) }
    private val photo by lazy{ intent.getStringExtra(PHOTO) }
    private val id by lazy{ intent.getStringExtra(UID) }

    private val mCurrentUid:String by lazy{
        FirebaseAuth.getInstance().uid!!
    }

    private val db :FirebaseDatabase by lazy{
        FirebaseDatabase.getInstance()
    }

    lateinit var CurrentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        EmojiManager.install(GoogleEmojiProvider())

        binding = ActivityChatBinding.inflate(layoutInflater)

        setContentView(binding.root)

        FirebaseFirestore.getInstance().collection("users").document(mCurrentUid).get()
            .addOnSuccessListener {
                CurrentUser = it.toObject(User::class.java)!!
            }

        binding.nameTv.text = name.toString()
        Picasso.get().load(photo).into(binding.userImgView)

    }

    private fun getMessage(friendId: String) = db.reference.child("messages/${getId(friendId)}")

    private fun getInbox(toUser:String,fromUser:String) = db.reference.child("chats/$toUser/$fromUser")

    // message ids
    private fun getId(friendId:String):String {
        if(friendId < mCurrentUid){
            return friendId + mCurrentUid
        }
            return mCurrentUid + friendId
    }

}