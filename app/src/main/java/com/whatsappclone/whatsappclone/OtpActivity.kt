package com.whatsappclone.whatsappclone

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.format.Time
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.whatsappclone.whatsappclone.databinding.ActivityOtpBinding
import java.util.concurrent.TimeUnit

const val PHONE_NUMBER = "phone_number"

class OtpActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOtpBinding
    var phoneNumber:String? = null

    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var mVerificationId:String? = null
    var mResendToken:PhoneAuthProvider.ForceResendingToken? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phoneNumber = intent.getStringExtra(PHONE_NUMBER)
        initView()
        startToVerify()


    }

    private fun startToVerify() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber!!,60, TimeUnit.SECONDS,this,callbacks
        )

        setTimerForResend(60000)
    }


    private fun setTimerForResend(milliSec:Long) {
        binding.ResendButton.isEnabled = false

        object:CountDownTimer(milliSec,1000){

            override fun onTick(millisUntilFinished: Long) {
               binding.CountDownText.text = getString(R.string.count_time,millisUntilFinished/1000)
            }

            override fun onFinish() {
                binding.ResendButton.isEnabled = true
                binding.CountDownText.visibility = View.INVISIBLE
            }

        }.start()

    }

    // setting text for wrong number spannable
    private fun SetSpannableStringToWrongNumber() {
        val span = SpannableString(getString(R.string.wrong_number,phoneNumber))

        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                startActivity(Intent(this@OtpActivity,LoginActivity::class.java))
                finish()
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

    // initiating first line with mobile verify number
    private fun initView() {
        SetSpannableStringToWrongNumber()
        binding.VerifyNumberText.text = getString(R.string.verify_number,phoneNumber)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                val smsOTP = credential.smsCode
                if(!smsOTP.isNullOrEmpty()){
                    binding.OtpEditText.setText(smsOTP)
                    Log.d("OTP_Verify",smsOTP)
                }else{
                    Log.d("OTP_Verify","Not Working")
                }

                //         signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
                Log.d("OTP_Verify",e.localizedMessage)
                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.


                // Save verification ID and resending token so we can use them later

                mVerificationId = verificationId
                mResendToken = token
            }
        }

    }


}


fun Context.createProgressDialogue(messege:String, isCancellable:Boolean) : ProgressDialog{
    return ProgressDialog(this).apply {
        setCancelable(isCancellable)
        setCanceledOnTouchOutside(false)
        setMessage(messege)
    }
}
