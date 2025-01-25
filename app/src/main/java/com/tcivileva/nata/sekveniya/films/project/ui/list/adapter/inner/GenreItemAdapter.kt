package com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.inner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tcivileva.nata.sekveniya.films.project.R
import com.tcivileva.nata.sekveniya.films.project.data.Genre
import com.tcivileva.nata.sekveniya.films.project.databinding.ItemGenreBinding
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.OnClickListener

class GenreItemAdapter(private val listener: OnClickListener<Genre>): ListAdapter<Genre, GenreItemAdapter.GenreItemViewHolder>(genreDiffUtil) {

    class GenreItemViewHolder(
        private val binding: ItemGenreBinding,
        private val listener: OnClickListener<Genre>
    ):ViewHolder(binding.root){

        fun bind(data:Genre) {
            val backgroundColorRes = if (data.isSelected) R.color.orange else android.R.color.transparent
            val backgroundColor = binding.root.resources.getColor(backgroundColorRes,null)

            binding.genreText.text = data.genre
            binding.genreText.setBackgroundColor(backgroundColor)

            binding.root.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(inflater,parent,false)
        return GenreItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val genreDiffUtil = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {

                return oldItem.isSelected &&
                        newItem.isSelected &&
                        oldItem.genre==newItem.genre
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem==newItem
            }
        }
    }
}