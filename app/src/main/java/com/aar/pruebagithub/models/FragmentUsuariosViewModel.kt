package com.aar.pruebagithub.models

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.aar.pruebagithub.conexion_ws.RepositorioUsuariosWS
import com.aar.pruebagithub.database.UsuarioDB
import com.aar.pruebagithub.database.UsuarioDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception





class FragmentUsuariosViewModel(private val context:Context, private val dataBase:UsuarioDao):ViewModel()
{

    //**************Coroutina para realizar Operaciones en la BD*********************
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)
    //****************************Fin Coroutina**************************************

    private val repositorioUsuarios = RepositorioUsuariosWS(dataBase)//Repositorio donde se obtiene los datos del WS y se guardan en la BD

    //**************Variables LiveData***********
    val datosUsuariosCache = repositorioUsuarios.datosUsuarios//Variable LiveData con los datos de los usuarios alamacenados en la BD
    //**************Fin LiveDatas**************

    private val usuariosSeleccionados = mutableListOf<UsuarioDB>()



    //Se ejecuta cuando se destruye el ViewModel
    override fun onCleared()
    {
        super.onCleared()

        //Se deseleccionan todos los usuarios
        deseleccionarTodos()

        //Se cancela el Job de la Coroutina donde se ejecuta Retrofit
        viewModelJob.cancel()
    }




    //Devuelve el numero de Usuarios que hay seleccionados actualmente
    fun numUsuariosSelecc() = usuariosSeleccionados.size



    //Se conecta con el WS para obtener la lista de Usuarios
    fun conerxionWS(){

        try
        {
            coroutineScope.launch { repositorioUsuarios.conectarWS() }

        }catch (exception:Exception) { Toast.makeText(context, "Error conexion con Servidor", Toast.LENGTH_LONG).show() }

    }



    //Se borran todos los usuarios que esten seleccionados
    fun borrarUsuarios()
    {
        coroutineScope.launch {
            dataBase.borrarUsuarios(usuariosSeleccionados)
            usuariosSeleccionados.clear()
        }
    }



    //Se seleecciona/deselecciona el usaurio indicado al realizar una pulsacion larga sobre el
    fun selecc_deselecc_usuario(usuario:UsuarioDB)
    {

        if(usuario.seleccionado)
        {
            usuario.seleccionado = false
            usuariosSeleccionados.remove(usuario)

        }else
        {
            usuario.seleccionado = true
            usuariosSeleccionados.add(usuario)
        }

        //se modifica los datos del usuario en la BD
        coroutineScope.launch { dataBase.modificarUsuario(usuario) }
    }



    //Se deselecciona todos los Usuarios de la BD
    fun deseleccionarTodos()
    {
        coroutineScope.launch {
            dataBase.deseleccionarUsuarios()
            usuariosSeleccionados.clear()
        }
    }


}