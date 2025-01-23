package com.tcivileva.nata.sekveniya.films.project.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tcivileva.nata.sekveniya.films.project.data.FilmData
import com.tcivileva.nata.sekveniya.films.project.databinding.ItemFilmBinding

class FilmListAdapter: ListAdapter<FilmData, FilmListAdapter.FilmsViewHolder>(filmsDiffUtil) {

    class FilmsViewHolder(private val binding:ItemFilmBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:FilmData){
            val context = binding.root.context

            Glide.with(context)
                .load(data.image)
                .into(binding.filmImage);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater,parent,false)
        return FilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object{
        val filmsDiffUtil = object : DiffUtil.ItemCallback<FilmData>() {

            override fun areItemsTheSame(oldItem: FilmData, newItem: FilmData): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: FilmData, newItem: FilmData): Boolean {
                return oldItem==newItem
            }

        }

    }

}