package com.whatsappclone.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.whatsappclone.whatsappclone.databinding.ActivityOtpBinding

const val PHONE_NUMBER = "phone_number"

class OtpActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOtpBinding
    var phoneNumber:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phoneNumber = intent.getStringExtra(PHONE_NUMBER)
        initView()

        SetSpannableStringToWrongNumber()

    }

    private fun SetSpannableStringToWrongNumber() {
        val span = SpannableString(getString(R.string.wrong_number,phoneNumber))

        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                 // previous activity
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)

                ds.isUnderlineText = false
                ds.color = ds.linkColor

            }

        }

        span.setSpan(clickableSpan,span.length-14,span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // this will not allow user to copy or move over that link \/ \/
        binding.WrongNumberText.movementMethod = LinkMovementMethod.getInstance()

        binding.WrongNumberText.text = span

    }

    private fun initView() {

        binding.VerifyNumberText.text = getString(R.string.verify_number,phoneNumber)

    }
}

