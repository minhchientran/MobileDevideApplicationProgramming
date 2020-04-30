package com.example.week2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_connecxion.*

class Connecxion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connecxion)

        buttonCreateAccount.setOnClickListener() {
            val intent = Intent(this, SignUp :: class.java)
            startActivity(intent)
        }

        already_hav.setOnClickListener() {
            val intent = Intent(this, Login :: class.java)
            startActivity(intent)
        }
    }
}
