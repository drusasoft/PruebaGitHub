package com.aar.pruebagithub.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aar.pruebagithub.databinding.LayoutPaginaInsertarDbBinding
import com.aar.pruebagithub.databinding.LayoutPaginaMostrarDbBinding
import com.aar.pruebagithub.models.PantallaPruebaBDViewModel


class ViewPagerFragment:Fragment()
{

    private lateinit var bindingInsertar:LayoutPaginaInsertarDbBinding
    private lateinit var bindingMostrar:LayoutPaginaMostrarDbBinding

    private val model:PantallaPruebaBDViewModel by viewModels()

    private var numPagina = 0


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Se obtiene los paramatros de entrada pasados al Fragment
        numPagina = requireArguments().getInt("pagina")
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        if(numPagina == 0)
        {
            bindingInsertar = LayoutPaginaInsertarDbBinding.inflate(inflater, container, false)

            setObservers()

            return bindingInsertar.root

        }


        if(numPagina == 1)
        {
            bindingMostrar = LayoutPaginaMostrarDbBinding.inflate(inflater, container, false)


            bindingMostrar.btnMostrar.setOnClickListener { model.pruebaCulo2() }


            return bindingMostrar.root
        }


       return null

    }



    private fun setObservers()
    {

        model.pruebaLiveData.observe(viewLifecycleOwner){

            Log.e("PruebaLiveData", it)
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

    }

}