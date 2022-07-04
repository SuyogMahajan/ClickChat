package com.whatsappclone.whatsappclone.ui.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.squareup.picasso.Picasso
import com.whatsappclone.whatsappclone.R
import com.whatsappclone.whatsappclone.databinding.ActivityChatBinding

const val UID = "uid"
const val PHOTO = "photo"
const val NAME = "name"

class ChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val emojiConfig = BundledEmojiCompatConfig(this)
        emojiConfig.setReplaceAll(true)
            .registerInitCallback(object : EmojiCompat.InitCallback() {
                override fun onInitialized() {
                    Log.d("HELLO", "EmojiCompat initialized")
                }

                override fun onFailed(throwable: Throwable?) {
                    Log.d("HELLO","EmojiCompat initialization failed $throwable" )
                }
            })
        EmojiCompat.init(emojiConfig)

        binding = ActivityChatBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val name = intent.getStringExtra(NAME)
        val photo = intent.getStringExtra(PHOTO)
        val id = intent.getStringExtra(UID)

        binding.nameTv.text = name.toString()
        Picasso.get().load(photo).into(binding.userImgView)

    }
}