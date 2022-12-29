package com.julianorenis.fitness_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class TmbActivity : AppCompatActivity() {

        private lateinit var lifestyle: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        lifestyle = findViewById(R.id.auto_lifeStyle)
        val items = resources.getStringArray(R.array.tmb_lifeStyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,items)
        lifestyle.setAdapter(adapter)
    }
}