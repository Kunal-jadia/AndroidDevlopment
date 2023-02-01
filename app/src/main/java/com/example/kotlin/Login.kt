package com.example.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlin.databinding.ActivityLoginBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var AppDb : UsersDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppDb = UsersDatabase.getDatabase(this)


        binding.loginBtn.setOnClickListener {
            val mobNo = binding.etMob.text.toString()
            val pass = binding.etPass.text.toString()
            if (mobNo.isNotEmpty() && pass.isNotEmpty()) {
                readData(mobNo, pass)
            }
            else {
                Toast.makeText(this,"Enter Data", Toast.LENGTH_LONG).show()
            }
        }

        val reg_link = binding.registerLink
        reg_link.setOnClickListener {
            val switch_activity = Intent(this, Register::class.java)
            startActivity(switch_activity)
        }
    }

//    private suspend fun displayData(users : Users) {
//        withContext(Dispatchers.Main) {
//            binding.momo.text = users.mob_no.toString()
//            binding.paso.text = users.pass
//        }
//    }

fun readData(MobNo :String, pass:String){

        lateinit var fet_data : Users

        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch {
            fet_data = AppDb.UserDao().getInfo(MobNo.toInt())
            // displayData(fet_data)
            if(MobNo.equals(fet_data.mob_no.toString()) && pass.equals(fet_data.pass)) {

                val sharedpref = getSharedPreferences("Login", MODE_PRIVATE)
                val editor = sharedpref.edit()
                editor.putBoolean("flag",true)
                editor.apply()
                val switch_activity = Intent(this@Login, MainActivity::class.java)
                startActivity(switch_activity)
            }
           else {
                Toast.makeText(this@Login, "Mobile not registered", Toast.LENGTH_LONG).show()
                binding.etMob.text.clear()
                binding.etPass.text.clear()
            }
        }
    }
}