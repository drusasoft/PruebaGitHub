package com.aar.pruebagithub.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aar.pruebagithub.PruebaGitHub
import com.aar.pruebagithub.R
import com.aar.pruebagithub.activities.PantallaPruebaBD
import com.aar.pruebagithub.adapters.PersonasAdapter
import com.aar.pruebagithub.database.PersonaDB
import com.aar.pruebagithub.databinding.LayoutPaginaInsertarDbBinding
import com.aar.pruebagithub.databinding.LayoutPaginaMostrarDbBinding
import com.aar.pruebagithub.models.PantallaPruebaBDViewModel
import com.aar.pruebagithub.models_factory.PantallaPruebaBDModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder





class ViewPagerFragment:Fragment()
{

    private lateinit var bindingInsertar:LayoutPaginaInsertarDbBinding
    private lateinit var bindingMostrar:LayoutPaginaMostrarDbBinding

    private val model:PantallaPruebaBDViewModel by viewModels{PantallaPruebaBDModelFactory((activity?.applicationContext as PruebaGitHub).getPersonaDataBase())}

    private lateinit var adapter:PersonasAdapter
    private var numPagina = 0




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Se obtiene los paramatros de entrada pasados al Fragment
        numPagina = requireArguments().getInt("pagina")

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        //*********Se muestra la IU de la pagina 1 en el Viewpager*********
        if(numPagina == 0)
        {
            bindingInsertar = LayoutPaginaInsertarDbBinding.inflate(inflater, container, false)

            bindingInsertar.btnInsertar.setOnClickListener {

                if(bindingInsertar.editTextDni.text!!.isNotEmpty() && bindingInsertar.editTextNombre.text!!.isNotEmpty() &&
                        bindingInsertar.editTextApellidos.text!!.isNotEmpty() && bindingInsertar.editTextEdad.text!!.isNotEmpty())
                {
                    val datosPersona = PersonaDB(bindingInsertar.editTextDni.text.toString().trim(), bindingInsertar.editTextNombre.text.toString().trim(),
                        bindingInsertar.editTextApellidos.text.toString().trim(), bindingInsertar.editTextEdad.text.toString().toInt(), false)

                    model.insertarDatosBD(requireContext(), datosPersona)

                    bindingInsertar.editTextDni.setText("")
                    bindingInsertar.editTextNombre.setText("")
                    bindingInsertar.editTextApellidos.setText("")
                    bindingInsertar.editTextEdad.setText("")

                }else
                {
                    Toast.makeText(context, "Debes introducir todos los datos", Toast.LENGTH_LONG).show()
                }

            }

            return bindingInsertar.root

        }//*********Fin pagina 1 del Viewpager*********



        //*********Se muestra la IU de la pagina 2 en el Viewpager*********
        if(numPagina == 1)
        {
            bindingMostrar = LayoutPaginaMostrarDbBinding.inflate(inflater, container, false)

            //**********Se instancia el RecyclerView donde se mostraran los datos de las Personas almacenadas en la BD**********
            adapter = PersonasAdapter({

                //Lambda que se ejecuta cuando se hace un ClickListener
                model.deseleccionarTodos()
                Toast.makeText(context, "ClickListener", Toast.LENGTH_LONG).show()

            },
            {

                //Lambda que se ejecuta cuando se hace un LongClickListener
                model.seleccDesleccPersona(it)

              true })

            bindingMostrar.recyclerPersonas.adapter = adapter
            //********** FIn Instancia del RecyclerView **********


            //********** Se gestionan los ClickListener **********
            bindingMostrar.btnBorrarPersona.setOnClickListener {

                //Se muestra un cuadro de dialogo para preguntar al usuario  si quiere eliminar a los usuarios seleccionados
                MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                    .setIcon(R.drawable.ic_delete_24)
                    .setTitle(R.string.titDialogEliminar)
                    .setMessage(R.string.txtDialogEliminar)
                    .setPositiveButton(R.string.btnSi, DialogInterface.OnClickListener { dialog, which ->  model.borrarDatosBD()})
                    .setNegativeButton(R.string.btnNo, DialogInterface.OnClickListener { dialog, which ->  model.deseleccionarTodos()})
                    .show()
            }
            //********** Fin ClickListener **********


            setObservers()


            return bindingMostrar.root

        }//*********Fin pagina 2 del Viewpager*********


       return null

    }



    //Metodo llamado desde el Listener registrado para el ViewPager (En la Activity) que se ejecuta cuando se cambia de pagina
    //Si nos posicionamos en la Pagina "Insertar", si hay personas seleccionadas previamente entonces deseleccionamnos todas
    fun deseleccionarTodos() { model.deseleccionarTodos() }




    //**********************************************************************************************
                                //Observers Variables LiveData
    //**********************************************************************************************

    //Observers de las variables LiveData definidfas en la clase ViewModel
    private fun setObservers()
    {

        //Observer de la Varible LiveData que contiene la lista de personas almacenadas en la BD
        model.listaPersonasLiveData.observe(viewLifecycleOwner){listaPersonas->
            adapter.submitList(listaPersonas)
        }

        //Observer de la variable LiveData que contiene la lista de personas seleccionadas
        model.listaPersonasSeleccLiveData.observe(viewLifecycleOwner){listaPersonasSelecc->

            adapter.notifyDataSetChanged()

            if(listaPersonasSelecc.size > 0)
            {
                bindingMostrar.btnBorrarPersona.show()
                (activity as PantallaPruebaBD).setTituloToolBar(listaPersonasSelecc.size.toString())
            }
            else{
                bindingMostrar.btnBorrarPersona.hide()
                (activity as PantallaPruebaBD).setTituloToolBar(getString(R.string.titPantallaPruebaBD))
            }


        }

    }

    //**********************************************************************************************
                                        //Fin Observers
    //**********************************************************************************************

}