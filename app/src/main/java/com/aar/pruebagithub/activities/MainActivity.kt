package com.aar.pruebagithub.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aar.pruebagithub.R
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
            Toast.makeText(this, "Culo de pavo", Toast.LENGTH_LONG).show()
        }
    }

}