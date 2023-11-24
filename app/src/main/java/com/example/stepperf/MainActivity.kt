package com.example.stepperf

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.stepperf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.stepper.initStepper(
            listOf(StepIconEnum.SHIPPING, StepIconEnum.PICKUP, StepIconEnum.PAYMENT, StepIconEnum.REVIEW)
        )
    }
}

fun changeDrawableColor(context: Context, textView: AppCompatTextView, colorResId: Int) {
    // Get the drawable from the TextView
    val drawables: Array<Drawable?> = textView.compoundDrawablesRelative

    // Change the color of each drawable
    for (drawable in drawables) {
        drawable?.let {
            // Use DrawableCompat to tint the drawable
            val wrappedDrawable = DrawableCompat.wrap(it)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, colorResId))
            DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_IN)
        }
    }

    // Apply the modified drawables back to the TextView
    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3])
}