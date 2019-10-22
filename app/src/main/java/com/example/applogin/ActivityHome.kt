package com.example.applogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activities_home.*

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_home)
        val user = intent.getStringExtra("username")
        id_txtWelcome.setText(id_txtWelcome.text.toString() +" "+ user)
    }
}