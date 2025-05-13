package com.example.aplikasibmi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var bmiResultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        bmiResultText = findViewById(R.id.bmiResult)

        val weightStr = intent.getStringExtra("weight") ?: ""
        val heightStr = intent.getStringExtra("height") ?: ""

        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            val weight = weightStr.toDouble()
            val height = heightStr.toDouble() / 100 // convert to meters
            val bmi = weight / (height * height)

            // Set BMI result
            bmiResultText.text = "BMI: %.2f".format(bmi)
        }
    }
}
