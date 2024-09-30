package com.example.avtorizcia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var sp = getSharedPreferences("PC", Context.MODE_PRIVATE)
        if(sp.getString("TY", "-9")!="-9"){
            startActivity(Intent(this, Glavnaya::class.java))
        } else {
            var email: TextView = findViewById(R.id.email_log)
            var password: TextView = findViewById(R.id.password_log)
            var db = Firebase.firestore
            var button: Button = findViewById(R.id.button_sign_in)
            var df = false
            button.setOnClickListener{
                db.collection("Users")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            if(document.getString("email")==email.text){
                                if(document.getString("password")==password.text){
                                    df = true
                                    sp.edit().putString("email",email.text.toString()).commit()
                                    startActivity(Intent(this,Glavnaya::class.java))
                                }

                            }

                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this,"Не получилось попробуйте позже!",Toast.LENGTH_LONG).show()
                    }
                var h = Handler()
                    h.postDelayed({
                        if(df == false){
                            Toast.makeText(this,"Данного емейла не обнаружено",Toast.LENGTH_LONG).show()
                        }
                    },1600)

            }
        }
    }
}