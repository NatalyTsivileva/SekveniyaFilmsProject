package com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.inner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tcivileva.nata.sekveniya.films.project.R
import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.databinding.ItemFilmBinding
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.OnClickListener

class FilmItemAdapter(private val listener: OnClickListener<Film>):
    ListAdapter<Film, FilmItemAdapter.FilmsViewHolder>(filmsDiffUtil) {

    class FilmsViewHolder(private val binding:ItemFilmBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:Film, clickListener: OnClickListener<Film>){
            val context = binding.root.context

            Glide.with(context)
                .load(data.image)
                .placeholder(R.drawable.img_placeholder)
                .into(binding.filmImage);

            binding.filmName.text = data.nameRu

            binding.root.setOnClickListener {
                clickListener.onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater,parent,false)
        return FilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.bind(currentList[position], listener)
    }


    companion object{
        val filmsDiffUtil = object : DiffUtil.ItemCallback<Film>() {

            override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
                return oldItem==newItem
            }

        }

    }

}