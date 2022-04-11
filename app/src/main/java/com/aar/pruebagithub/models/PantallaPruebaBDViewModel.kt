package com.aar.pruebagithub.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aar.pruebagithub.database.PersonaDB
import com.aar.pruebagithub.database.PersonaDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch





class PantallaPruebaBDViewModel(private val dataBase:PersonaDao):ViewModel()
{

    //**************Coroutina para realizar Operaciones en la BD*********************
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    //****************************Fin Coroutina**************************************

    //**************Variables LiveData***********
    private val _pruebaLiveData = MutableLiveData<String>()
    val pruebaLiveData: LiveData<String>
        get() = _pruebaLiveData
    //**************Fin Variables LiveData*******


    init {
        _pruebaLiveData.value = "Soy un Inutil"
    }


    //Se insertan los dato de una persona en la BD
    fun insertarDatosBD(datosPersona:PersonaDB)
    {
        coroutineScope.launch { dataBase.insertarPersona(datosPersona) }
    }

}