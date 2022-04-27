package com.aar.pruebagithub.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aar.pruebagithub.database.UsuarioDB
import com.aar.pruebagithub.databinding.LayoutFragmentDetallesUsuarioBinding
import com.squareup.picasso.Picasso





class FragmentDetallesUsuario: Fragment()
{

    private lateinit var binding: LayoutFragmentDetallesUsuarioBinding
    private var datosUsuario:UsuarioDB? = null



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Se obtiene el objeto del Tipo UsuarioDB pasado como parametro al fragment
        datosUsuario =  arguments?.getParcelable<UsuarioDB>("DatosUsuario")
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = LayoutFragmentDetallesUsuarioBinding.inflate(inflater, container, false)


        //Se muestran la imagen y los datos del usuario
        Picasso.get()
            .load(datosUsuario?.imagenBig)
            .into(binding.imageViewDetalleUsuario)

        binding.txtNombreUsuario.text = datosUsuario?.nombre
        binding.txtApellidosUsuario.text = datosUsuario?.apellidos
        binding.txtGeneroUsuario.text = datosUsuario?.genero
        binding.txtEmailUsuario.text = datosUsuario?.email
        binding.txtCiudadUsuario.text = datosUsuario?.ciudad
        binding.txtPaisUsuario.text = datosUsuario?.pais


        return binding.root

    }

}