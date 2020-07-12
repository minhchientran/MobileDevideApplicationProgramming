package com.piyushsatija.pollutionmonitor.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.piyushsatija.pollutionmonitor.R
import com.piyushsatija.pollutionmonitor.model.PollutionLevels

class AboutUsDialog internal constructor(private val context1: Context) : Dialog(context1) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.about_us)
        val window = window
        if (window != null) {
            window.attributes.windowAnimations = R.style.DialogAnimation
            window.setBackgroundDrawableResource(R.color.transparentDark)
        }
    }



}