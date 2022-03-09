package com.aar.pruebagithub

import android.app.Application
import android.widget.Toast





class PruebaGitHub:Application()
{

    override fun onCreate() {
        super.onCreate()

        Toast.makeText(this, "Culo De Pavo", Toast.LENGTH_LONG).show()
    }

}