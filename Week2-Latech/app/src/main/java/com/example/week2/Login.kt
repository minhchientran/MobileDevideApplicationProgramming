package com.example.week2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        already_hav2.setOnClickListener() {
            val intent = Intent(this, SignUp :: class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Connecxion :: class.java)
        startActivity(intent)
    }
}
