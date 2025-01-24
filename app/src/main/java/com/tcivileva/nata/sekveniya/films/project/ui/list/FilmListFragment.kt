package com.tcivileva.nata.sekveniya.films.project.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tcivileva.nata.sekveniya.films.project.R
import com.tcivileva.nata.sekveniya.films.project.collectFlow
import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.data.Genre
import com.tcivileva.nata.sekveniya.films.project.databinding.FragmentFilmListBinding
import com.tcivileva.nata.sekveniya.films.project.ui.LoadingState
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.OnClickListener
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.outer.FilmListAdapter
import com.tcivileva.nata.sekveniya.films.project.ui.list.adapter.outer.GenreListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment() {

    private var _binding: FragmentFilmListBinding? = null

    private val viewModel: FilmListViewModel by viewModel()

    private var filmAdapter: FilmListAdapter? = null
    private var genreAdapter: GenreListAdapter? = null

    private val filmClickListener = object : OnClickListener<Film>{
        override fun onClick(item: Film) {
            val action = FilmListFragmentDirections
                .actionFilmListFragmentToFilmDetailsFragment(filmID = item.id)

            findNavController().navigate(action)
        }
    }

  private val genreClickListener = object : OnClickListener<Genre> {
      override fun onClick(item: Genre) {
         viewModel.getFilms(item.genre)
      }
  }

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
                    showErrorSnackbar(state.message)
                }

                is LoadingState.Error -> {
                    toggleProgressIndicator(false)
                    showErrorSnackbar(state.message)
                }
            }
        }

        collectFlow(viewModel.genresListFlow){ genres->
           genreAdapter?.submitList(genres)
        }

        viewModel.loadData()
    }

    private fun setupRecycler(){
        _binding?.filmsRecycler?.let { recycler->
            recycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

            filmAdapter = FilmListAdapter(filmClickListener)
            genreAdapter = GenreListAdapter(genreClickListener)

            recycler.adapter = ConcatAdapter(
                genreAdapter,
                filmAdapter
            )
        }
    }

    private fun toggleProgressIndicator(isOn:Boolean){
        _binding?.filmsProgressbar?.isVisible = isOn
    }

    private fun showErrorSnackbar(text:String){
        _binding?.root?.let {
            val snack = Snackbar.make(it,text,Snackbar.LENGTH_SHORT)
            val btnText = it.context.getText(R.string.btn_reload)
            snack.setAction(btnText){
                viewModel.loadData()
            }
            snack.show()
        }
    }
}