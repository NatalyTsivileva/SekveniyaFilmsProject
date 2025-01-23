package com.tcivileva.nata.sekveniya.films.project.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.tcivileva.nata.sekveniya.films.project.collectFlow
import com.tcivileva.nata.sekveniya.films.project.databinding.FragmentFilmListBinding
import com.tcivileva.nata.sekveniya.films.project.ui.LoadingState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment() {

    private var _binding: FragmentFilmListBinding? = null

    private val viewModel: FilmListViewModel by viewModel()

    private var filmAdapter: FilmListAdapter? = null
    private var genreAdapter: FilmGenreAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmListBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        setupRecycler()

        collectFlow(viewModel.filmsListFlow){ state->
            when(state){
                is LoadingState.Loading -> {
                    toggleProgressIndicator(true)
                }

                is LoadingState.Success -> {
                    toggleProgressIndicator(false)
                    filmAdapter?.submitList(state.data)
                }

                is LoadingState.ConnectionError -> {
                    toggleProgressIndicator(false)
                }

                is LoadingState.Error -> {
                    toggleProgressIndicator(false)
                }
            }
        }

        collectFlow(viewModel.genresListFlow){ genres->
            genreAdapter?.submitList(genres)
        }

        viewModel.getFilmsList()
    }

    private fun setupRecycler(){
        _binding?.filmsRecycler?.let { recycler->
            recycler.layoutManager = GridLayoutManager(context,2)

            filmAdapter = FilmListAdapter()
            genreAdapter = FilmGenreAdapter()

            recycler.adapter = ConcatAdapter(
                filmAdapter,
                genreAdapter
            )
        }
    }

    private fun toggleProgressIndicator(isOn:Boolean){
        _binding?.filmsProgressbar?.isVisible = isOn
    }
}