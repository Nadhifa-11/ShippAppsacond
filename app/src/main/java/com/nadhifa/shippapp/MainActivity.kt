package com.nadhifa.shippapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.content.Intent
import android.os.TestLooperManager
import android.provider.ContactsContract
import android.util.Patterns
import android.widget.Toast
import com.nadhifa.shippapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null)
        {
            println(mAuth!!.currentUser?.displayName)
            startActivity(Intent(this, ContactsContract.Profile::class.java))
            finish()
            return
        }

        binding.login.setOnClickListener {
            loginIn()
        }

        binding.register.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }



    }

    private fun loginIn()
    {
        val email = binding.tvEmail.text.toString().trim()
        val password = binding.tvPassword.text.toString().trim()

        if (email.isEmpty())
        {
            binding.tvEmail.error = "Email Cannot Be Empty"
            binding.tvEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.tvEmail.error = "Provide a Valid Email"
            binding.tvEmail.requestFocus()
            return
        }

        if (password.isEmpty())
        {
            binding.tvPassword.error = "Password Cannot Be Empty"
            binding.tvPassword.requestFocus()
            return
        }

        if (password.length < 6)
        {
            binding.tvPassword.error = "The Password Should Be At Least 6 Characters"
            binding.tvPassword.requestFocus()
            return
        }

        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnSuccessListener {
                val user = FirebaseAuth.getInstance().currentUser

                if (user!!.isEmailVerified){
                    Toast.makeText(this, "Signed In", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, ContactsContract.Profile::class.java))
                    finish()
                }else{
                    user.sendEmailVerification()
                    Toast.makeText(this, "Check Your Inbox", Toast.LENGTH_LONG).show()
                }


            }?.addOnFailureListener {
                Toast.makeText(this, "Fail to Sign In", Toast.LENGTH_LONG).show()
            }

    }
}