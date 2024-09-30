package com.example.avtorizcia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class RegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg)
        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE).edit()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var email:TextView = findViewById(R.id.email_reg)
        var password:TextView = findViewById(R.id.password_reg)
        var button: Button = findViewById(R.id.button_reg)
        button.setOnClickListener{
            if(email.text.isEmpty() || !email.text.contains("@")) {
                Toast.makeText(this,"Проверьте поле Email!",Toast.LENGTH_LONG).show()
            }
            else if(password.text.isEmpty() || password.text.length<6){
                Toast.makeText(this,"Пароль должен быть больше пяти символов",Toast.LENGTH_LONG).show()
            }
            else {
                var db = Firebase.firestore
                // Create a new user with a first and last name
                val user = hashMapOf(
                    "email" to email.text.toString(),
                    "password" to password.text.toString()
                )

// Add a new document with a generated ID
                    db.collection("Users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                        sp.putString("email", email.text.toString()).commit()
                        startActivity(Intent(this, Glavnaya::class.java))
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Не получилось, попробуйте позже!", Toast.LENGTH_LONG).show()

                    }
            }
        }
    }
}