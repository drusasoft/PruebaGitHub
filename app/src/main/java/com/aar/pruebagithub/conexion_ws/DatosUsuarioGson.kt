package com.aar.pruebagithub.conexion_ws

import com.aar.pruebagithub.database.UsuarioDB

data class DatosUsuarioGson(
    val results: List<Results>
)

data class Results(

    val gender:String,
    val email:String,
    val id:Id,
    val name:Name,
    val location:Location,
    val picture:Picture
)

data class Id(

    val name:String,
    val value:String?
)

data class Name(

    val first:String,
    val last:String
)

data class Location(

    val city:String,
    val state:String,
    val country:String
)

data class Picture(

    val large:String,
    val medium:String,
    val thumbnail:String
)


//Funcion de Tranfromacion que se encarga de transformar los datos de Usarios recibidos del WS a la estructura
//de datos usada por la BD y asi poder almacenarlos
fun DatosUsuarioGson.toDatosUsuarioDB():List<UsuarioDB>
{

    val listaUsuariosDB = mutableListOf<UsuarioDB>()

    //Se recorre la lista de usuarios Obtenidos del WS y solo se guardan aquellos cuyo ID no sea nulo
    results.forEach {

        //Si el campo values es distinto de null
        if(it.id.value != null)
        {
            val usuarioDB = UsuarioDB(it.id.value, it.name.first, it.name.last, it.gender, it.email,
            it.location.city, it.location.state, it.location.country, it.picture.thumbnail, it.picture.medium, it.picture.large)

            listaUsuariosDB.add(usuarioDB)
        }
    }

    return listaUsuariosDB

}
