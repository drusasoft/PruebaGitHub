package com.aar.pruebagithub.models

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PantallaPruebaBDViewModel():ViewModel()
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
        Log.e("PantallaPruebaBDViewModel", "Init")
        _pruebaLiveData.value = "Soy un Inutil"
    }


    fun pruebaCulo(){
        Log.e("Yo", "Soy un Inutil Fracasado")
        _pruebaLiveData.value = "Soy un Inutil Fracasado"
    }

    fun pruebaCulo2(){ Log.e("Yo", "Toda mi familia me ve como un mierda")}

}