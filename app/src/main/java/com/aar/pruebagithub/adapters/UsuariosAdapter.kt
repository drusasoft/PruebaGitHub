package com.aar.pruebagithub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aar.pruebagithub.R
import com.aar.pruebagithub.database.UsuarioDB
import com.aar.pruebagithub.databinding.LayoutRecyclerUsuariosBinding
import com.squareup.picasso.Picasso


class UsuariosAdapter(private val onClickListener:(UsuarioDB)->Unit, private val onLongClickListener:(UsuarioDB)->Boolean):ListAdapter<UsuarioDB, UsuariosAdapter.UsuariosViewHolder>(UsuarioCallBack())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutRecyclerUsuariosBinding.inflate(layoutInflater, parent, false)

        return UsuariosViewHolder(parent.context, binding, onClickListener, onLongClickListener)
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) { holder.onBind(getItem(position)) }



    class UsuariosViewHolder(private val context:Context, private val binding: LayoutRecyclerUsuariosBinding, private val onClickListener: (UsuarioDB) -> Unit,
        private val onLongClickListener: (UsuarioDB) -> Boolean):RecyclerView.ViewHolder(binding.root)
    {

        fun onBind(datosUsuario:UsuarioDB)
        {

            binding.txtRecyclerUsuariosNombreApellidos.text = "${datosUsuario.nombre} ${datosUsuario.apellidos}"
            binding.txtRecyclerUsuariosEMail.text = datosUsuario.email


            Picasso.get()
                .load(datosUsuario.imagenSmall)
                .into(binding.imageViewRecyclesUsuarios)


            //Si el usuario ha sido seleccionado se muestra de manera distinta en el Recycler
            if(datosUsuario.seleccionado)
            {
                binding.txtRecyclerUsuariosNombreApellidos.setTextColor(context.getColor(R.color.purple_200))
                binding.txtRecyclerUsuariosEMail.setTextColor(context.getColor(R.color.purple_200))
                binding.layoutParentRecyclerUsuarios.setBackgroundResource(R.drawable.borde_recycler_personas_selecc)
            }else
            {
                binding.txtRecyclerUsuariosNombreApellidos.setTextColor(context.getColor(R.color.color_texto_recycler))
                binding.txtRecyclerUsuariosEMail.setTextColor(context.getColor(R.color.color_texto_recycler))
                binding.layoutParentRecyclerUsuarios.setBackgroundResource(R.drawable.borde_recycler_personas)
            }


            //Se define el CLickListener y el LongClickListener para los elementos del Recycler
            itemView.setOnClickListener { onClickListener(datosUsuario) }
            itemView.setOnLongClickListener { onLongClickListener(datosUsuario) }
        }

    }

}


class UsuarioCallBack:DiffUtil.ItemCallback<UsuarioDB>()
{
    override fun areItemsTheSame(oldItem: UsuarioDB, newItem: UsuarioDB): Boolean { return oldItem.id == oldItem.id }

    override fun areContentsTheSame(oldItem: UsuarioDB, newItem: UsuarioDB): Boolean { return oldItem == newItem }
}