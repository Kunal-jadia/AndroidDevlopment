package com.example.kotlin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import com.example.kotlin.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

//Previous Code
class Register : AppCompatActivity() {
    var cities = arrayOf<String?>("Select City", "Gondal", "Rajkot", "Surat", "Ahmedabad")

    lateinit var Selcity : String
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var appDb: UsersDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = UsersDatabase.getDatabase(this)

        val spinner = findViewById<Spinner>(R.id.spinner)
//        spinner.setSelection(0,false)
        val arradap = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities)
        arradap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arradap
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Selcity = arradap.getItem(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Selcity = "Select city"
            }
        }

        var agree : CheckBox = binding.CBox
        binding.regBtn.setOnClickListener {
            val flag : Boolean = validateInfo()
            if(flag) {
                if(agree.isChecked) {
                    writeData()
                } else {
                    Toast.makeText(this,"Select Checkbox",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val log_link = binding.loginLink
        log_link.setOnClickListener {
            val switch_activity = Intent(this, Login::class.java)
            startActivity(switch_activity)
        }
    }

    fun EmailValidate(text: String) : Boolean{
        val pattern : Pattern = Pattern.compile("you")//[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+")
        val matcher : Matcher = pattern.matcher(text)
        if(matcher.find()){return true} else { return false}
    }
    fun validateInfo() : Boolean {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.email.text.toString()
        val mob_no = binding.mobNum.text.toString()
        val pass = binding.regPass.text.toString()
        val city : String = Selcity

        if(firstName.length ==0) { Toast.makeText(this,"Enter First Name",Toast.LENGTH_SHORT).show(); return false }
        else if(lastName.length==0){ Toast.makeText(this,"Enter Last Name",Toast.LENGTH_SHORT).show(); return false }
        else if(email.length==0){ Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show(); return false }
        else if(mob_no.length==0){ Toast.makeText(this,"Enter Mobile No",Toast.LENGTH_SHORT).show();return false }
        else if(pass.length==0){ Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show(); return false }
        else if(city.equals("Select City")){ Toast.makeText(this,"Select City",Toast.LENGTH_SHORT).show(); return false }
        else { return true }
    }

    fun writeData() {

        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.email.text.toString()
        val mob_no = binding.mobNum.text.toString()
        val pass = binding.regPass.text.toString()
        val city = binding.spinner.onItemSelectedListener.toString()


        if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && mob_no.isNotEmpty() && pass.isNotEmpty() && city.isNotEmpty()) {
            val User = Users(null, firstName, lastName, email, mob_no.toInt(), pass, city)
            GlobalScope.launch(Dispatchers.IO) {
                appDb.UserDao().insert(User)
            }
            binding.firstName.text.clear()
            binding.lastName.text.clear()
            binding.email.text.clear()
            binding.mobNum.text.clear()
            binding.regPass.text.clear()
            binding.CBox.setChecked(false)
            binding.spinner.setSelection(0)

            Toast.makeText(this,"Registration Successfull", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this,"Enter Proper data", Toast.LENGTH_LONG).show()
        }
    }
}