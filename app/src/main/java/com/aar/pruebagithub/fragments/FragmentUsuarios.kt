package com.aar.pruebagithub.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aar.pruebagithub.PruebaGitHub
import com.aar.pruebagithub.R
import com.aar.pruebagithub.adapters.UsuariosAdapter
import com.aar.pruebagithub.databinding.LayoutFragmentUsuariosBinding
import com.aar.pruebagithub.models.FragmentUsuariosViewModel
import com.aar.pruebagithub.models_factory.FragmentUsuariosModelFactory





class FragmentUsuarios:Fragment()
{

    private lateinit var binding:LayoutFragmentUsuariosBinding
    private lateinit var navController: NavController
    private lateinit var adapter: UsuariosAdapter

    private val model:FragmentUsuariosViewModel by viewModels{ FragmentUsuariosModelFactory(requireContext(), (activity?.applicationContext as PruebaGitHub).getUsuarioDataBase()) }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = LayoutFragmentUsuariosBinding.inflate(inflater, container, false)


        //Se instancia el RecyclerView que muestra la lista de usuarios
        adapter = UsuariosAdapter({usuarioDB->

            //Lambda que se ejecuta cuando se puylsa sobre un elemento del Recycler
            val bundle = Bundle()
            bundle.putParcelable("DatosUsuario", usuarioDB)
            navController.navigate(R.id.irFragmentDetallesUsuario, bundle)

        })

        binding.recyclerUsuarios.adapter = adapter


        //********** Se gestionan los ClickListener **********

        binding.btnConexion.setOnClickListener { model.conerxionWS() }

        //********** Fin ClickListener **********


        //********** Se gestionan ScrollListener del RecyclerView **********

        //Este listener se ejecuta cuando se hace scroll hacia arriba o abajo de los  elementos mostrados en el recycle
        //y segun ese scroll muestro el Floating button con estilo Extendido i reducido
        binding.recyclerUsuarios.addOnScrollListener(object: RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
            {
                super.onScrolled(recyclerView, dx, dy)

                when
                {
                    (dy > 10 && binding.btnConexion.isExtended)->{ binding.btnConexion.shrink() }

                    (dy < -10 && !binding.btnConexion.isExtended)->{ binding.btnConexion.extend() }
                }
            }

        })

        //********** Fin ScrollListener del RecyclerView **********


        setObservers()


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //Se instancia el objeto NavController
        navController = findNavController()
    }




    //**********************************************************************************************
                            //Observers de Variables LiveData
    //**********************************************************************************************

    fun setObservers()
    {

        //Se registra el Observer para la variable LiveData que contiene los datos de los usuarios alamacenados en la BD
        model.datosUsuariosCache.observe(viewLifecycleOwner){datosUsuario->

            //Log.e("Observer", "${datosUsuario}")
            adapter.submitList(datosUsuario.sortedBy { it.nombre })

        }

    }

    //**********************************************************************************************
                            //Fin Observers
    //**********************************************************************************************

}