package com.aar.pruebagithub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aar.pruebagithub.R
import com.aar.pruebagithub.database.PersonaDB
import com.aar.pruebagithub.databinding.LayoutRecyclerPersonasBinding





class PersonasAdapter(private val onClickListener: (PersonaDB)->Unit, private val onLongClickListener:(PersonaDB)->Boolean):ListAdapter<PersonaDB, PersonasAdapter.PersonasViewHolder>(PersonaCallBack())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutRecyclerPersonasBinding.inflate(layoutInflater, parent, false)

        return PersonasViewHolder(parent.context, binding, onClickListener, onLongClickListener)
    }

    override fun onBindViewHolder(holder: PersonasViewHolder, position: Int) { holder.onBind(getItem(position)) }



    class PersonasViewHolder(private val context: Context, private val binding:LayoutRecyclerPersonasBinding,
                             private val onClickListener: (PersonaDB)->Unit, private val onLongClickListener:(PersonaDB)->Boolean):RecyclerView.ViewHolder(binding.root, )
    {

        fun onBind(datosPersona:PersonaDB)
        {
            binding.txtRecyclerPersonasNombre.text = datosPersona.nombre
            binding.txtRecyclerPersonasApellidos.text = datosPersona.apellidos

            //Si la persona esta seleccionada, se muestra de distinto color en el Recycler
            if(datosPersona.seleccionado)
            {
                binding.txtRecyclerPersonasNombre.setTextColor(context.getColor(R.color.purple_200))
                binding.txtRecyclerPersonasApellidos.setTextColor(context.getColor(R.color.purple_200))
                binding.layoutParentRecyclerPersonas.setBackgroundResource(R.drawable.borde_recycler_personas_selecc)

            }else
            {
                binding.txtRecyclerPersonasNombre.setTextColor(context.getColor(R.color.color_texto_recycler))
                binding.txtRecyclerPersonasApellidos.setTextColor(context.getColor(R.color.color_texto_recycler))
                binding.layoutParentRecyclerPersonas.setBackgroundResource(R.drawable.borde_recycler_personas)
            }

            itemView.setOnClickListener { onClickListener(datosPersona) }
            itemView.setOnLongClickListener { onLongClickListener(datosPersona) }
        }

    }

}




class PersonaCallBack: DiffUtil.ItemCallback<PersonaDB>()
{
    override fun areItemsTheSame(oldItem: PersonaDB, newItem: PersonaDB): Boolean { return oldItem.dni == newItem.dni }

    override fun areContentsTheSame(oldItem: PersonaDB, newItem: PersonaDB): Boolean { return oldItem == newItem }
}