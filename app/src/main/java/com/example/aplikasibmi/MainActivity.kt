package com.example.aplikasibmi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var weightInput: EditText
    private lateinit var heightInput: EditText
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi view
        weightInput = findViewById(R.id.weightInput)
        heightInput = findViewById(R.id.heightInput)
        calculateButton = findViewById(R.id.calculateButton)

        calculateButton.setOnClickListener {
            val weightStr = weightInput.text.toString()
            val heightStr = heightInput.text.toString()

            if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("weight", weightStr)
                intent.putExtra("height", heightStr)
                startActivity(intent)
            }
        }
    }
}
