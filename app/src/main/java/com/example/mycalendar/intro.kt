package com.example.mycalendar

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class intro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hd = Handler()
        hd.postDelayed({
            startActivity(Intent(application, MainActivity::class.java))
            this@intro.finish()
        }, 1300)

    }

    override fun onBackPressed() {

    }
}
