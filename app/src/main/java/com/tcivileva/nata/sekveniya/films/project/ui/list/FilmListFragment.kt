package com.tcivileva.nata.sekveniya.films.project.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.tcivileva.nata.sekveniya.films.project.collectFlow
import com.tcivileva.nata.sekveniya.films.project.databinding.FragmentFilmListBinding
import com.tcivileva.nata.sekveniya.films.project.ui.LoadingState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmListFragment : Fragment() {

    private var _binding: FragmentFilmListBinding? = null

    private val viewModel: FilmListViewModel by viewModel()

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

        collectFlow(viewModel.filmsListFlow){ state->
            when(state){
                is LoadingState.Loading -> {
                    toggleProgressIndicator(true)
                }

                is LoadingState.Success -> {
                    toggleProgressIndicator(false)
                }

                is LoadingState.ConnectionError -> {
                    toggleProgressIndicator(false)
                }

                is LoadingState.Error -> {
                    toggleProgressIndicator(false)
                }
            }
        }

        viewModel.getFilmsList()
    }


    fun toggleProgressIndicator(isOn:Boolean){
        _binding?.filmsProgressbar?.isVisible = isOn
    }
}