package com.clickchat.clickchat.ui.actvities.auth

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.clickchat.clickchat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    lateinit var phoneNumber:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.MobileNumber.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.SubmitButton.isEnabled = binding.MobileNumber.text.toString().length >= 10
                Log.d("hello?","on")
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.SubmitButton.setOnClickListener {
            phoneNumber = binding.CodePicker.selectedCountryCodeWithPlus + binding.MobileNumber.text.toString()
            notifyUser()

        }

    }

    private fun notifyUser() {
        MaterialAlertDialogBuilder(this).apply {

            setMessage("We will be verifying the phone number $phoneNumber . Is it ok ? or you like to edit your Number ?")

            setPositiveButton("Ok", DialogInterface.OnClickListener { _,_ ->
                showOtpActivity()
            })

            setNegativeButton("Edit", DialogInterface.OnClickListener { _,_ ->

            })

            setCancelable(false)
            create()
            show()
        }
    }

    private fun showOtpActivity() {
       startActivity(Intent(this, OtpActivity::class.java).putExtra(PHONE_NUMBER,phoneNumber))
       finish()
    }
}