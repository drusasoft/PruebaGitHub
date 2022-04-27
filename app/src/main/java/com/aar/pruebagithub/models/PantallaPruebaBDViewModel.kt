package com.aar.pruebagithub.models

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aar.pruebagithub.database.PersonaDB
import com.aar.pruebagithub.database.PersonaDao
import kotlinx.coroutines.*


class PantallaPruebaBDViewModel(private val dataBase:PersonaDao):ViewModel()
{

    //**************Coroutina para realizar Operaciones en la BD*********************
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    //****************************Fin Coroutina**************************************

    //**************Variables LiveData***********
    private val _listaPersonasLiveData = dataBase.getPersonasLive()
    private val _listaPersonasSeleccLiveData = MutableLiveData<List<PersonaDB>>()

    val listaPersonasLiveData: LiveData<List<PersonaDB>>
        get() = _listaPersonasLiveData

    val listaPersonasSeleccLiveData: LiveData<List<PersonaDB>>
        get() = _listaPersonasSeleccLiveData
    //**************Fin Variables LiveData*******

    private val listaPersonasSelecc = mutableListOf<PersonaDB>()


    //Se ejecuta cuando se destruye el ViewModel
    override fun onCleared() { deseleccionarTodos() }



    //Se insertan los dato de una persona en la BD
    fun insertarDatosBD(context:Context, datosPersona:PersonaDB)
    {
        coroutineScope.launch { dataBase.insertarPersona(datosPersona) }

        Toast.makeText(context, "Datos Insertados", Toast.LENGTH_LONG).show()
    }



    //Se eliminan de la BD las personas seleccionadas
    fun borrarDatosBD()
    {
        coroutineScope.launch {

            dataBase.borrarPersonas(listaPersonasSelecc)
            listaPersonasSelecc.clear()
            actualizarSeleccionadosLiveData()
        }

    }



    //Se selecciona o deselecciona una persona cuando se hace una pulsacion larga sobre ella en el RecycleView
    fun seleccDesleccPersona(persona:PersonaDB)
    {
        when(persona.seleccionado)
        {
            true->{
               persona.seleccionado = false
               listaPersonasSelecc.remove(persona)
               _listaPersonasSeleccLiveData.value = listaPersonasSelecc
            }

            else->{
               persona.seleccionado = true
                listaPersonasSelecc.add(persona)
                _listaPersonasSeleccLiveData.value = listaPersonasSelecc
            }
        }

        //Se cambia en la BD el valor del campo seleccionado
        coroutineScope.launch { dataBase.modificarPersona(persona) }
    }



    //Se deseleccionan todos las personas que estuvieran seleccionadas
    fun deseleccionarTodos()
    {
        if(listaPersonasSelecc.size > 0)
        {
            listaPersonasSelecc.clear()
            _listaPersonasSeleccLiveData.value = listaPersonasSelecc
            coroutineScope.launch { dataBase.deseleccionarTodos() } //Para ello se cambia el valor del campo seleccionado a false en la BD a todos los registros
        }

    }


    //******* Metodo Llamado desde el Metodo borrarDatosBD *******
    //Una vez eliminados los datos selecionadso de la BD, se actualiza la variable LiveData que contiene a klas personas seleccionadas
    //Esto lo hago asi ya que el contenido de una variable de tipo solo se puede actualizar en el hilo principal
    private fun actualizarSeleccionadosLiveData()
    {
        GlobalScope.launch {
            withContext(Dispatchers.Main){ _listaPersonasSeleccLiveData.value = listaPersonasSelecc }
        }
    }

}