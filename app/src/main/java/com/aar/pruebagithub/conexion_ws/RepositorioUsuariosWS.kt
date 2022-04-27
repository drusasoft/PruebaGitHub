package com.aar.pruebagithub.conexion_ws

import com.aar.pruebagithub.database.UsuarioDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create





class RepositorioUsuariosWS(private val dataBase:UsuarioDao)
{
    //Parte fija de la URL del WS usada por Retrofit (Ejemplo: https://api.randomuser.me/?results=5)
    private val BASE_URL = "http://api.randomuser.me/"

    private val retrofitService:UsuariosService


    val datosUsuarios = dataBase.getUsuariosLive()//Se obtiene la lista de usuarios almacenados en la BD, esta variable es de tipo LiveData por lo que simepre estara actualizada


    init {

        //Se instancai el Objeto Retrofit para poder conectar con el WS
        retrofitService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create()
    }



    @Throws(Exception::class)//Throws funciona como en java si se produce una excepcion capturo dicha excepcion en la funcion que llama a esta
    suspend fun conectarWS()
    {
        //Se obtienen los datos de Conversion de Monedas del WS
        val datosUsuariosWS = retrofitService.getUsuarios()

        //Se insertan los datos de los usuarios obtenidos del WS en la BD, para ello uso la funcion de tranformacion que he definido
        //que se encarga de crear la estructura de datos usada por BD a partir de los datos obtenidos del WS
        dataBase.insertarUsuarios(datosUsuariosWS.toDatosUsuarioDB())
    }

}