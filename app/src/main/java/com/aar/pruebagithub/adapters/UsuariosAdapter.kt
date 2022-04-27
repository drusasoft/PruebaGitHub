package com.aar.pruebagithub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aar.pruebagithub.database.UsuarioDB
import com.aar.pruebagithub.databinding.LayoutRecyclerUsuariosBinding
import com.squareup.picasso.Picasso


class UsuariosAdapter(private val onClickListener:(UsuarioDB)->Unit):ListAdapter<UsuarioDB, UsuariosAdapter.UsuariosViewHolder>(UsuarioCallBack())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutRecyclerUsuariosBinding.inflate(layoutInflater, parent, false)

        return UsuariosViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) { holder.onBind(getItem(position)) }



    class UsuariosViewHolder(private val binding: LayoutRecyclerUsuariosBinding, private val onClickListener: (UsuarioDB) -> Unit):RecyclerView.ViewHolder(binding.root)
    {

        fun onBind(datosUsuario:UsuarioDB)
        {

            binding.txtRecyclerUsuariosNombreApellidos.text = "${datosUsuario.nombre} ${datosUsuario.apellidos}"
            binding.txtRecyclerUsuariosEMail.text = datosUsuario.email

            Picasso.get()
                .load(datosUsuario.imagenSmall)
                .into(binding.imageViewRecyclesUsuarios)

            //Se define el CLickListener y el LongClickListener para los elementos del Recycler
            itemView.setOnClickListener { onClickListener(datosUsuario) }

        }

    }

}


class UsuarioCallBack:DiffUtil.ItemCallback<UsuarioDB>()
{
    override fun areItemsTheSame(oldItem: UsuarioDB, newItem: UsuarioDB): Boolean { return oldItem.id == oldItem.id }

    override fun areContentsTheSame(oldItem: UsuarioDB, newItem: UsuarioDB): Boolean { return oldItem == newItem }
}