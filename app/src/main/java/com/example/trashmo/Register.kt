package com.example.trashmo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trashmo.databinding.ActivityRegisterBinding
import com.example.trashmo.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class Register : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        database = Firebase.database.reference.child("user")

        val name = binding.tvNamaRegister1.text.toString().trim()
        val email = binding.tvEmailRegister1.text.toString()
        val password = binding.tvPasswordRegister1.text.toString().trim()

        binding.backBtnRegister1.setOnClickListener {
            val intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegister1.setOnClickListener {
            register(name, email, password)
        }

    }

    private fun register(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword("test1@gmail.com", "12345678")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newUser = User("Syawal","test1@gmail.com", "12345678")
                    val userId = auth.currentUser?.uid.toString()
                    database.child(userId).setValue(newUser)
                    val intent = Intent(this@Register, Login::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w("Firebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}