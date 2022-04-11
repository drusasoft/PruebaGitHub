package com.aar.pruebagithub.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aar.pruebagithub.PruebaGitHub
import com.aar.pruebagithub.database.PersonaDB
import com.aar.pruebagithub.databinding.LayoutPaginaInsertarDbBinding
import com.aar.pruebagithub.databinding.LayoutPaginaMostrarDbBinding
import com.aar.pruebagithub.models.PantallaPruebaBDViewModel
import com.aar.pruebagithub.models_factory.PantallaPruebaBDModelFactory





class ViewPagerFragment:Fragment()
{

    private lateinit var bindingInsertar:LayoutPaginaInsertarDbBinding
    private lateinit var bindingMostrar:LayoutPaginaMostrarDbBinding

    private val model:PantallaPruebaBDViewModel by viewModels{PantallaPruebaBDModelFactory((activity?.applicationContext as PruebaGitHub).getPersonaDataBase())}

    private var numPagina = 0




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Se obtiene los paramatros de entrada pasados al Fragment
        numPagina = requireArguments().getInt("pagina")
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        //Se muestra la IU de la pagina 1 en el Viewpager
        if(numPagina == 0)
        {
            bindingInsertar = LayoutPaginaInsertarDbBinding.inflate(inflater, container, false)

            setObservers()

            bindingInsertar.btnInsertar.setOnClickListener {

                if(bindingInsertar.editTextDni.text!!.isNotEmpty() && bindingInsertar.editTextNombre.text!!.isNotEmpty() &&
                        bindingInsertar.editTextApellidos.text!!.isNotEmpty() && bindingInsertar.editTextEdad.text!!.isNotEmpty())
                {
                    val datosPersona = PersonaDB(bindingInsertar.editTextDni.text.toString().trim(), bindingInsertar.editTextNombre.text.toString().trim(),
                        bindingInsertar.editTextApellidos.text.toString().trim(), bindingInsertar.editTextEdad.text.toString().toInt())

                    model.insertarDatosBD(datosPersona)

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

        }


        //Se muestra la IU de la pagina 2 en el Viewpager
        if(numPagina == 1)
        {
            bindingMostrar = LayoutPaginaMostrarDbBinding.inflate(inflater, container, false)

            //Se instancia el RecyclerView donde se mostraran los datos de las Personas almacenadas en la BD
            //...

            return bindingMostrar.root
        }


       return null

    }




    //**********************************************************************************************
                                //Observers Variables LiveData
    //**********************************************************************************************

    private fun setObservers()
    {
        //Observers de las variables LiveData definidfas en la clase ViewModel

        model.pruebaLiveData.observe(viewLifecycleOwner){

            Log.e("PruebaLiveData", it)
        }

    }

    //**********************************************************************************************
                                        //Fin Observers
    //**********************************************************************************************

}