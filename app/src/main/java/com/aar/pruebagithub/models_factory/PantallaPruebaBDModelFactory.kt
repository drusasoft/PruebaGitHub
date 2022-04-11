package com.aar.pruebagithub.models_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aar.pruebagithub.database.PersonaDao
import com.aar.pruebagithub.models.PantallaPruebaBDViewModel

class PantallaPruebaBDModelFactory(private val dataBase:PersonaDao):ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(PantallaPruebaBDViewModel::class.java))
            return PantallaPruebaBDViewModel(dataBase) as T

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}