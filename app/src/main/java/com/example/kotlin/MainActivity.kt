package com.example.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar

import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dlay : DrawerLayout = findViewById(R.id.nav_drawer)
        val navView : NavigationView = findViewById(R.id.navView)
        val toolbar : Toolbar = findViewById(R.id.actionbar)

        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(this, dlay, toolbar, R.string.open,R.string.close)
        dlay.addDrawerListener(toggle)
        toggle.syncState()

        val imageView : ImageView = findViewById(R.id.logoutBtn)
        imageView.setOnClickListener {
            val sharedpref = getSharedPreferences("Login", MODE_PRIVATE)
            val editor = sharedpref.edit()
            editor.putBoolean("flag", false)
            editor.apply()

            val switch_activity = Intent(this, Login::class.java)
            startActivity(switch_activity)
        }


        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> Toast.makeText(applicationContext,"Home selected",Toast.LENGTH_SHORT).show()
                R.id.Trash -> Toast.makeText(applicationContext,"Trash selected",Toast.LENGTH_SHORT).show()
                R.id.Setting -> Toast.makeText(applicationContext,"Setting selected", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}