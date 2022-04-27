package com.aar.pruebagithub.models_factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aar.pruebagithub.database.UsuarioDao
import com.aar.pruebagithub.models.FragmentUsuariosViewModel

class FragmentUsuariosModelFactory(private val context: Context, private val dataBase:UsuarioDao):ViewModelProvider.Factory
{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(FragmentUsuariosViewModel::class.java))
            return FragmentUsuariosViewModel(context, dataBase) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}