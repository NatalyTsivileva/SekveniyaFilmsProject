package com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.outer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.data.FilmsWithHeader
import com.tcivileva.nata.sekveniya.films.project.databinding.ItemFilmListBinding
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.OnClickListener
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.inner.FilmItemAdapter

class FilmListAdapter(private val listener:OnClickListener<Film>): ListAdapter<FilmsWithHeader,FilmListAdapter.FilmListViewHolder>(filmListDiffUtils) {

    class FilmListViewHolder(
        private val binding: ItemFilmListBinding,
        private val listener: OnClickListener<Film>
    ):ViewHolder(binding.root) {
       fun bind(data:FilmsWithHeader){
           binding.headerText.text = binding.root.context.getText(data.headerRes)
           val innerAdapter = FilmItemAdapter(listener = listener )
           binding.genresRecycler.adapter = innerAdapter
           binding.genresRecycler.layoutManager = GridLayoutManager(binding.root.context,2)
           innerAdapter.submitList(data.films)
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmListBinding.inflate(inflater,parent,false)
        return FilmListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val filmListDiffUtils = object : DiffUtil.ItemCallback<FilmsWithHeader>() {
            override fun areItemsTheSame(
                oldItem: FilmsWithHeader,
                newItem: FilmsWithHeader
            ): Boolean {
                return oldItem.films == newItem.films
            }

            override fun areContentsTheSame(
                oldItem: FilmsWithHeader,
                newItem: FilmsWithHeader
            ): Boolean {
                return  oldItem == newItem
            }

        }
    }
}