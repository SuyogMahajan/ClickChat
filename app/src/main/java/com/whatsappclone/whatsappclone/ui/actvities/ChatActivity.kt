package com.whatsappclone.whatsappclone.ui.actvities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.whatsappclone.whatsappclone.databinding.ActivityChatBinding
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.google.GoogleEmojiProvider

const val UID = "uid"
const val PHOTO = "photo"
const val NAME = "name"

class ChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        EmojiManager.install(GoogleEmojiProvider())

//        val emojiConfig = BundledEmojiCompatConfig(this)
//        emojiConfig.setReplaceAll(true)
//            .registerInitCallback(object : EmojiCompat.InitCallback() {
//                override fun onInitialized() {
//                    Log.d("HELLO", "EmojiCompat initialized")
//                }
//
//                override fun onFailed(throwable: Throwable?) {
//                    Log.d("HELLO","EmojiCompat initialization failed $throwable" )
//                }
//            })
//        EmojiCompat.init(emojiConfig)

        binding = ActivityChatBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val name = intent.getStringExtra(NAME)
        val photo = intent.getStringExtra(PHOTO)
        val id = intent.getStringExtra(UID)

        binding.nameTv.text = name.toString()
        Picasso.get().load(photo).into(binding.userImgView)

    }
}