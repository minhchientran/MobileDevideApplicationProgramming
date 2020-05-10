package com.example.week2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        do_not_hav.setOnClickListener() {
            val intent = Intent(this, Login :: class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Connecxion :: class.java)
        startActivity(intent)
    }
}
