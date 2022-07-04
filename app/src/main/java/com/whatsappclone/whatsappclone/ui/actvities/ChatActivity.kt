package com.whatsappclone.whatsappclone.ui.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whatsappclone.whatsappclone.R

const val UID = "uid"
const val PHOTO = "photo"
const val NAME = "name"

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        HELLO
    }
}