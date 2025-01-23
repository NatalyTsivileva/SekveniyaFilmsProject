package com.tcivileva.nata.sekveniya.films.project.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tcivileva.nata.sekveniya.films.project.R
import com.tcivileva.nata.sekveniya.films.project.data.AdapterDataType
import com.tcivileva.nata.sekveniya.films.project.data.FilmGenre
import com.tcivileva.nata.sekveniya.films.project.data.FilmHeader
import com.tcivileva.nata.sekveniya.films.project.data.FilmSkeleton
import com.tcivileva.nata.sekveniya.films.project.data.IAdapterData
import com.tcivileva.nata.sekveniya.films.project.databinding.ItemGenreBinding
import com.tcivileva.nata.sekveniya.films.project.databinding.ItemHeaderBinding

class FilmGenreAdapter:ListAdapter<IAdapterData,FilmGenreAdapter.FilmGenreViewHolder>(genreDiffUtil) {
    override fun getItemViewType(position: Int): Int {
        val data = currentList[position]
        return when(data) {
            is FilmHeader->AdapterDataType.HEADER.ordinal
            is FilmGenre->AdapterDataType.GENRE.ordinal
            is FilmSkeleton->AdapterDataType.SKELETON.ordinal
            else -> -1
        }
    }

    class FilmGenreViewHolder(private val binding: ViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: IAdapterData){
            when {
                binding is ItemGenreBinding && data is FilmGenre ->{
                    val backgroundColorRes = if (data.isSelected) R.color.orange else android.R.color.transparent
                    val backgroundColor = binding.root.resources.getColor(backgroundColorRes,null)
                    binding.genreText.text = data.genre
                    binding.genreText.setBackgroundColor(backgroundColor)
                }

                binding is ItemHeaderBinding && data is FilmHeader->{
                    binding.headerText.text = data.text
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmGenreViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == AdapterDataType.GENRE.ordinal){
           ItemGenreBinding.inflate(inflater,parent,false)
        }else{
           ItemHeaderBinding.inflate(inflater,parent,false)
        }
        return FilmGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmGenreViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object{
        val genreDiffUtil = object : DiffUtil.ItemCallback<IAdapterData>() {
            override fun areItemsTheSame(oldItem: IAdapterData, newItem: IAdapterData): Boolean {
                return oldItem.areItemsTheSame(newItem)
            }

            override fun areContentsTheSame(oldItem: IAdapterData, newItem: IAdapterData): Boolean {
                return oldItem.areContentsTheSame(newItem)
            }

        }
    }
}