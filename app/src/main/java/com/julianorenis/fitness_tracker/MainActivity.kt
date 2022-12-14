package com.julianorenis.fitness_tracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var btnIMC: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIMC = findViewById(R.id.btn_imc)

        btnIMC.setOnClickListener{
            var i = Intent(this, ImcActivity::class.java)
            startActivity(i)

        }

    }
}