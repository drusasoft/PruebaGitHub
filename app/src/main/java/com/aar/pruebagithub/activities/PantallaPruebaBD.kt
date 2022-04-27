package com.aar.pruebagithub.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.aar.pruebagithub.R
import com.aar.pruebagithub.databinding.LayoutPantallaPruebabdBinding
import com.aar.pruebagithub.fragments.ViewPagerFragment
import com.google.android.material.tabs.TabLayoutMediator


class PantallaPruebaBD: AppCompatActivity()
{

    private lateinit var binding: LayoutPantallaPruebabdBinding
    private lateinit var pagerFragment:ViewPagerFragment

    private val NUM_PAGINAS = 2

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = LayoutPantallaPruebabdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Se configura la ToolBar
        setSupportActionBar(binding.toolBarPantallaPrueba)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)


        //Se instancia el ViewPager con su TabLayout
        val pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerBD.adapter = pagerAdapter

        //Se asocia el Tablayout con el Viewpager2 usando la clase TabLayoutMediator para que se muestren las pestañas con sus nombres
        TabLayoutMediator(binding.tabs, binding.viewPagerBD){tab, position->
            val nomTabLayout = arrayOf(getString(R.string.titPagina_1), getString(R.string.titPagina_2))
            tab.text = nomTabLayout.get(position)
        }.attach()


        //Se registra un listener que se ejecuta cuando se campia dde pagina el el ViewPager2
        binding.viewPagerBD.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position)
                {
                    0->{ pagerFragment.deseleccionarTodos()}
                }

            }
        })

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {

        when(item.itemId)
        {
            android.R.id.home->onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }


    //Metodo llamado desde el Fragment ViewPagerFragment para mostrar en la ToolBar el numero de elementos Seleccionados
    fun setTituloToolBar(titulo:String){
        supportActionBar!!.setTitle(titulo)
    }


    //**********************************************************************************************
                            //Clase PageAdapter usada por el ViewPager
    //**********************************************************************************************

    inner class PagerAdapter(fm: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle)
    {
        override fun getItemCount(): Int { return NUM_PAGINAS }

        override fun createFragment(position: Int): Fragment
        {
            val bundle = Bundle()
            bundle.putInt("pagina", position)

            pagerFragment = ViewPagerFragment()
            pagerFragment.arguments = bundle

            return pagerFragment
        }


    }


    //**********************************************************************************************
                            //Fin Clase PageAdapter usada por el ViewPager
    //**********************************************************************************************

}