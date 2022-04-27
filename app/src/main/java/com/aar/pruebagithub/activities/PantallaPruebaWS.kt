package com.aar.pruebagithub.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aar.pruebagithub.R
import com.aar.pruebagithub.databinding.LayoutPantallaPruebawsBinding





class PantallaPruebaWS:AppCompatActivity()
{

    private lateinit var binding: LayoutPantallaPruebawsBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = LayoutPantallaPruebawsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarPantallaPruebaWS)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        //Se instancia el objeto NavController
        navController = Navigation.findNavController(this, R.id.NavHostFragmentConexionWS)

        //Añadimos el navContrller a la Toolbar(Actionbar), Para que se muestre la flecha volver y el titulo del frgament en la toolbar cuando se navega a otros fragments desde el fragment home
        NavigationUI.setupWithNavController(binding.toolBarPantallaPruebaWS, navController)

        navController.addOnDestinationChangedListener{ controller, destination, arguments ->

            when(destination.id)
            {
                controller.graph.startDestinationId->{ supportActionBar!!.setTitle(R.string.titFragmentMostrarUsuarios) }

                R.id.fragmentDetallesUsuario->{ supportActionBar!!.setTitle(R.string.titFragmentDetalleUsuario) }
            }

        }

    }

}