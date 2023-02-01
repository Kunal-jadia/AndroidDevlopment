@file:Suppress("DEPRECATION")

package com.example.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.kotlin.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp_logo = findViewById<ImageView>(R.id.sp_logo)

        sp_logo.alpha = 0f
        sp_logo.animate().setDuration(2000).alpha(1f)
//            .withEndAction {
//            val i =  Intent(this, Login::class.java)
//            startActivity(i)
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//            finish()
//        }

        Handler().postDelayed({
            val sharedpref = getSharedPreferences("Login", MODE_PRIVATE)
            val editor = sharedpref.edit()
            val check: Boolean
            var inext : Intent
            editor.apply {
                check = sharedpref.getBoolean("flag",false)
                if(check){
                    inext = Intent(this@SplashScreen, MainActivity::class.java)
                } else {
                    inext = Intent(this@SplashScreen, Login::class.java)
                }
            }.apply()
            startActivity(inext)
        }, 3000)
    }
}