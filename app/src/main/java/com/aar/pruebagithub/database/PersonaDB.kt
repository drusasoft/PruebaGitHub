package com.aar.pruebagithub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Personas")
data class PersonaDB(

    @PrimaryKey
    var dni: String,

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "apellidos")
    var apellidos: String,

    @ColumnInfo(name = "edad")
    var edad: Int,

    @ColumnInfo(name = "seleccionado")
    var seleccionado:Boolean
)
