package com.aar.pruebagithub.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonaDao
{
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarPersona(persona:PersonaDB)

    @Delete
    fun borrarPersona(persona:PersonaDB)

    @Delete
    fun borrarPersonas(listaPersonas:List<PersonaDB>)

    @Update
    fun modificarPersona(persona:PersonaDB)

    @Query("Select * from Personas")
    fun getPersonas():LiveData<List<PersonaDB>>

}