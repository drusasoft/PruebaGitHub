package com.aar.pruebagithub.fragments

import android.content.DialogInterface
import android.os.Bundle
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
           when(model.numUsuariosSelecc())
           {
               0->{//Si no hay ningun usuario seleccionado actualmente se navega al frgament de Detalles Usuario
                   val bundle = Bundle()
                   bundle.putParcelable("DatosUsuario", usuarioDB)
                   navController.navigate(R.id.irFragmentDetallesUsuario, bundle)
               }

               else->{//Si hay usuarios seleccionados, se deseleccionan todos
                   model.deseleccionarTodos()
               }
           }


        },{usuarioDB->

            //Lambda que se ejecuta cuando se hace una pulsacion larga sobre un elemento del Recycler
            model.selecc_deselecc_usuario(usuarioDB)

            true
        })

        binding.recyclerUsuarios.adapter = adapter



        //********** Se gestionan los ClickListener **********

        binding.btnConexion.setOnClickListener { model.conerxionWS() }

        binding.btnBorrar.setOnClickListener { mostrarDialogEliminar() }

        //********** Fin ClickListener **********



        //********** Se gestionan ScrollListener del RecyclerView **********

        //Este listener se ejecuta cuando se hace scroll hacia arriba o abajo de los  elementos mostrados en el recycle
        //y segun ese scroll muestro los Floating buttons con estilo Extendido y reducido
        binding.recyclerUsuarios.addOnScrollListener(object: RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
            {
                super.onScrolled(recyclerView, dx, dy)

                when
                {
                    (dy > 10 && binding.btnConexion.isExtended)->{ binding.btnConexion.shrink() }

                    (dy < -10 && !binding.btnConexion.isExtended)->{ binding.btnConexion.extend() }

                    (dy > 10 && binding.btnBorrar.isExtended)->{ binding.btnBorrar.shrink() }

                    (dy < -10 && !binding.btnBorrar.isExtended)->{ binding.btnBorrar.extend() }
                }
            }

        })

        //********** Fin ScrollListener del RecyclerView **********


        setObservers()//Se registran los Observers para las Variable de Tipo LiveData definidas en el ViewModel


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //Se instancia el objeto NavController
        navController = findNavController()
    }


    //Se muestra el cuadro de Dialogo preguntando al usuario si quiere borrar los uusarios seleccionados
    fun mostrarDialogEliminar()
    {
        MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
            .setTitle("Borrar Usuarios")
            .setMessage("¿Quiere borrar a los usuarios seleccionados?")
            .setPositiveButton(R.string.btnSi, DialogInterface.OnClickListener { dialog, which ->  model.borrarUsuarios()})
            .setNegativeButton(R.string.btnNo, DialogInterface.OnClickListener { dialog, which ->  model.deseleccionarTodos()})
            .create()
            .show()
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
            adapter.notifyDataSetChanged()

            when(model.numUsuariosSelecc())
            {
                0->{//Si no hay usuarios seleccionados se muestra el boton de Conectar con el WS
                    binding.btnConexion.show()
                    binding.btnBorrar.hide()
                }

                else->{//Si hay usuarios seleccionados se muestra el boton Borrar
                    binding.btnConexion.hide()
                    binding.btnBorrar.show()
                }
            }

        }

    }

    //**********************************************************************************************
                            //Fin Observers
    //**********************************************************************************************

}