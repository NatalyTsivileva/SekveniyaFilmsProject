package com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.outer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tcivileva.nata.sekveniya.films.project.data.Genre
import com.tcivileva.nata.sekveniya.films.project.data.GenreWithHeader
import com.tcivileva.nata.sekveniya.films.project.databinding.ItemGenreListBinding
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.OnClickListener
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.inner.GenreItemAdapter

class GenreListAdapter(private val listener:OnClickListener<Genre>) :ListAdapter<GenreWithHeader, GenreListAdapter.GenreListViewHolder>(genreListDiffUtils) {

    class GenreListViewHolder(
        private val binding: ItemGenreListBinding,
        private val listener: OnClickListener<Genre>
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(data: GenreWithHeader){
            binding.headerText.text = binding.root.context.getText(data.headerRes)

            val innerAdapter = GenreItemAdapter(listener)
            binding.genresRecycler.adapter = innerAdapter
            binding.genresRecycler.layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.VERTICAL,false)
            innerAdapter.submitList(data.genres)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
       val inflater = LayoutInflater.from(parent.context)
       val binding = ItemGenreListBinding.inflate(inflater,parent,false)
       return GenreListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val genreListDiffUtils = object : DiffUtil.ItemCallback<GenreWithHeader>() {
            override fun areItemsTheSame(
                oldItem: GenreWithHeader,
                newItem: GenreWithHeader
            ): Boolean {
                return oldItem.genres == newItem.genres
            }

            override fun areContentsTheSame(
                oldItem: GenreWithHeader,
                newItem: GenreWithHeader
            ): Boolean {
              return  oldItem == newItem
            }

        }
    }
}