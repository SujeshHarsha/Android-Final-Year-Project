package com.example.myfirst.assignment

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SplashActivity : AppCompatActivity() {
    var myLayout: ConstraintLayout? = null
    var animationDrawable: AnimationDrawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        myLayout = findViewById<View>(R.id.mySLayout) as ConstraintLayout
        animationDrawable = myLayout!!.background as AnimationDrawable
        animationDrawable!!.setEnterFadeDuration(4500)
        animationDrawable!!.setExitFadeDuration(4500)
        animationDrawable!!.start()
        val handler = Handler()
        handler.postDelayed({ preferences }, 2000)
    }

    private val preferences: Unit
        private get() {
            val sharedPreferences =
                getSharedPreferences("User", Context.MODE_PRIVATE)
            val user = sharedPreferences.getString("username", "")
            val pass = sharedPreferences.getString("password", "")
            if (user == "admin" && pass == "admin") {
                val intent = Intent(this@SplashActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
}