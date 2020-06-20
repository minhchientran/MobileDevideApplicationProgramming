package com.example.week9_animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    lateinit var normal: ImageView
    lateinit var dart: ImageView
    lateinit var purple: ImageView
    lateinit var red: ImageView
    lateinit var orange: ImageView
    lateinit var light: ImageView
    var isFadeIn = true
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        normal = findViewById(R.id.normal)
        dart = findViewById(R.id.dart)
        purple = findViewById(R.id.purple)
        red = findViewById(R.id.red)
        orange = findViewById(R.id.orange)
        light = findViewById(R.id.light)

        fadeIn(normal)
    }

    private fun fadeOut(view: ImageView) {
        val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f)
        animator.duration = 200
        animator.starNew()
        animator.start()
    }

    private fun fadeIn(view: ImageView) {
        val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f)
        animator.duration = 200
        animator.starNew()
        animator.start()
    }

    private fun ObjectAnimator.starNew() {
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationRepeat(animation)
                index += 1
                if (index == 6) {
                    index = 0
                    isFadeIn = !isFadeIn
                }
                val view = when(index) {
                    0 -> normal
                    1 -> dart
                    2 -> purple
                    3 -> red
                    4 -> orange
                    else -> light
                }
                if (isFadeIn) {
                    fadeIn(view)
                }
                else {
                    fadeOut(view)
                }

            }

        })
    }
}