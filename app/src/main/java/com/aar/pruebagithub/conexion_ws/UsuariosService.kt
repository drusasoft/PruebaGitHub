package com.aar.pruebagithub.conexion_ws

import retrofit2.http.GET

interface UsuariosService
{
    @GET("/?results=10")
    suspend fun getUsuarios():DatosUsuarioGson
}