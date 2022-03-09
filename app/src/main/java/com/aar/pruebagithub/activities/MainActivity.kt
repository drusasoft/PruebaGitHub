package com.aar.pruebagithub.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aar.pruebagithub.databinding.ActivityMainBinding





class MainActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Se configura la ToolBar
        setSupportActionBar(binding.toolBarMain)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        binding.btnBaseDatos.setOnClickListener {
            val intent = Intent(this, PantallaPruebaBD::class.java)
            startActivity(intent)
        }
    }

}