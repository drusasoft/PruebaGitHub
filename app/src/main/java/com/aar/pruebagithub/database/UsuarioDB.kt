package com.aar.pruebagithub.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Usuarios")
data class UsuarioDB(

    @PrimaryKey()
    var id:String,

    @ColumnInfo(name = "nombre")
    var nombre:String,

    @ColumnInfo(name = "apellidos")
    var apellidos:String,

    @ColumnInfo(name = "genero")
    var genero:String,

    @ColumnInfo(name = "email")
    var email:String,

    @ColumnInfo(name = "ciudad")
    var ciudad:String,

    @ColumnInfo(name = "estado")
    var estado:String,

    @ColumnInfo(name = "pais")
    var pais:String,

    @ColumnInfo(name = "imagenSmall")
    var imagenSmall:String,

    @ColumnInfo(name = "imagenMedium")
    var imagenMedium:String,

    @ColumnInfo(name = "imagenBig")
    var imagenBig:String,

    @ColumnInfo(name = "seleccionado")
    var seleccionado:Boolean

) : Parcelable