package com.example.week2
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart.setOnClickListener() {
            val intent = Intent(this, Connecxion :: class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true);
        exitProcess(-1)
    }
}
