package com.aar.pruebagithub

import android.app.Application
import androidx.room.Room
import com.aar.pruebagithub.database.PruebaGitHubDataBase


class PruebaGitHub:Application()
{

    private lateinit var database:PruebaGitHubDataBase

    override fun onCreate() {
        super.onCreate()

        //Se instancia la Base de Datos Room
        database = Room.databaseBuilder(this, PruebaGitHubDataBase::class.java, "PruebaGitHubDataBase")
            .fallbackToDestructiveMigration()
            .build()
    }

    //Se obtiene la BD de Personas
    fun getPersonaDataBase() = database.personaDao()

    //Se obtiene la BD de Usuarios
    fun getUsuarioDataBase() = database.usuarioDao()

}