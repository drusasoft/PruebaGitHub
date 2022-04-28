package com.aar.pruebagithub.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertarUsuario(usuario: UsuarioDB)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertarUsuarios(usuarios: List<UsuarioDB>)

    @Update
    fun modificarUsuario(usuario: UsuarioDB)

    @Delete
    fun borrarUsuario(usuario: UsuarioDB)

    @Delete
    fun borrarUsuarios(usuarios: List<UsuarioDB>)

    @Query("Select * from Usuarios")
    fun getUsuariosLive():LiveData<List<UsuarioDB>>

    @Query("Update Usuarios set seleccionado = 0")
    fun deseleccionarUsuarios()

}