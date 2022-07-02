package com.whatsappclone.whatsappclone.ui.actvities.auth

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.whatsappclone.whatsappclone.ui.actvities.MainActivity
import com.whatsappclone.whatsappclone.data.User
import com.whatsappclone.whatsappclone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    lateinit var pickImages: ActivityResultLauncher<String>

    val storage by lazy { FirebaseStorage.getInstance() }
    lateinit var downloadUrl: String

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val database by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

            pickImages =
                registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                    uri?.let { it ->
                        // The image was saved into the given Uri -> do something with it
                        binding.ProfileImage.setImageURI(it)
                        uploadImage(it)

                    }

                }

        binding.ProfileImage.setOnClickListener {
            checkPermissionForImage()
        }

        binding.NextButton.setOnClickListener{

            binding.NextButton.isEnabled = false

            val name = binding.NameEditText.text.toString()
            if(name.isNullOrBlank()){
                Toast.makeText(this,"Name can not be Empty.",Toast.LENGTH_LONG)
            }else if(!::downloadUrl.isInitialized){
                Toast.makeText(this,"Please select an Profile picture.",Toast.LENGTH_LONG)
            }else{
                val user = User(name,downloadUrl,downloadUrl,auth.uid!!)
                database.collection("users").document(auth.uid!!).set(user).addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener{
                    binding.NextButton.isEnabled = true
                }
            }

        }

    }

    private fun uploadImage(it: Uri) {
      val ref = storage.reference.child("uploads/"+auth.uid.toString())
        val uploadTask = ref.putFile(it)
        uploadTask.continueWithTask(Continuation{ task ->
            if(!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
          return@Continuation ref.downloadUrl
        }).addOnCompleteListener{ task ->
            binding.NextButton.isEnabled = true

            if(task.isSuccessful){
                downloadUrl = task.result.toString()
            }
        }
    }


    private fun checkPermissionForImage() {
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, 1000)

            val permission2 = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission2, 1001)
        } else {
            pickImages.launch("image/*")
        }
    }


}