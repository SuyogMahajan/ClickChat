package com.whatsappclone.whatsappclone.ui.actvities

import android.os.Bundle
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.whatsappclone.whatsappclone.databinding.ActivityChatBinding
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider
import com.whatsappclone.whatsappclone.data.ChatEvent
import com.whatsappclone.whatsappclone.data.DateHeader
import com.whatsappclone.whatsappclone.data.Inbox
import com.whatsappclone.whatsappclone.data.User
import com.whatsappclone.whatsappclone.ui.adapters.ChatAdapter
import com.whatsappclone.whatsappclone.utils.isSameDayAs
import java.util.*

const val UID = "uid"
const val PHOTO = "photo"
const val NAME = "name"

class ChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatBinding

    private val name by lazy{ intent.getStringExtra(NAME) }
    private val photo by lazy{ intent.getStringExtra(PHOTO) }
    private val id by lazy{ intent.getStringExtra(UID) }

    private lateinit var chatAdapter: ChatAdapter

    private val mCurrentUid:String by lazy{
        FirebaseAuth.getInstance().uid!!
    }

    private val db :FirebaseDatabase by lazy{
        FirebaseDatabase.getInstance()
    }
    private val messages: MutableList<ChatEvent> = mutableListOf()
    lateinit var CurrentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        EmojiManager.install(GoogleEmojiProvider())

        binding = ActivityChatBinding.inflate(layoutInflater)

        chatAdapter = ChatAdapter(messages,mCurrentUid)

        setContentView(binding.root)

        FirebaseFirestore.getInstance().collection("users").document(mCurrentUid).get()
            .addOnSuccessListener {
                CurrentUser = it.toObject(User::class.java)!!
            }

        binding.msgRv.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }

        listenToMessages()

        binding.nameTv.text = name.toString()
        Picasso.get().load(photo).into(binding.userImgView)

        binding.sendBtn.setOnClickListener {
            binding.msgEdtv.text?.let{
                if(it.isNotEmpty()){
                    sendMessage(it.toString())
                    it.clear()
                }
            }
        }

    }

    private fun sendMessage(message: String) {
        val xid = getMessage(id!!).push().key

        checkNotNull(id){ "must not null" }

        val msgMap = com.whatsappclone.whatsappclone.data.Message(message,mCurrentUid,xid!!)

        getMessage(id!!).child(xid!!).setValue(msgMap).addOnSuccessListener {

        }.addOnFailureListener{

        }

        updateLastMessage(msgMap)

    }

    fun updateLastMessage(message: com.whatsappclone.whatsappclone.data.Message){
        val inboxMap = Inbox(message.msg ,id!!,name!!,photo!!, count = 0)

        getInbox(mCurrentUid, id!!).setValue(inboxMap)

        getInbox(id!!, mCurrentUid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val value = p0.getValue(Inbox::class.java)
                inboxMap.apply {
                    from = message.senderId
                    name = CurrentUser.name
                    image = CurrentUser.thumbImage
                    count = 1
                }
                if (value?.from == message.senderId) {
                    inboxMap.count = value.count + 1
                }
                getInbox(id!!, mCurrentUid).setValue(inboxMap)
            }

        })

    }


private fun listenToMessages() {
    getMessage(id!!).orderByKey().addChildEventListener(object : ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
              val msg = snapshot.getValue(com.whatsappclone.whatsappclone.data.Message::class.java)!!

              addMessage(msg)

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    })
}

    private fun addMessage(msg: com.whatsappclone.whatsappclone.data.Message) {
        val eventBefore = messages.lastOrNull()

        // Add date header if it's a different day
        if ((eventBefore != null
                    && !eventBefore.sentAt.isSameDayAs(msg.sentAt))
            || eventBefore == null
        ) {
            messages.add(
                DateHeader(
                    msg.sentAt, this
                )
            )
        }
        messages.add(msg)

        binding.msgRv.adapter!!.notifyItemInserted(messages.size-1)
        binding.msgRv.scrollToPosition(messages.size-1)

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