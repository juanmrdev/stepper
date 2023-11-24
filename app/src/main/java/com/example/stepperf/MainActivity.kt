package com.example.stepperf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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