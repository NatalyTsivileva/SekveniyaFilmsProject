package com.tcivileva.nata.sekveniya.films.project.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.tcivileva.nata.sekveniya.films.project.collectFlow
import com.tcivileva.nata.sekveniya.films.project.databinding.FragmentFilmDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FilmDetailsFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null

    private val args: FilmDetailsFragmentArgs by navArgs()

    private val viewModel: FilmDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        viewModel.getFilmInfo(args.filmID)

        collectFlow(viewModel.filmsDetails){
            _binding?.testText?.text = it.nameRu
        }
    }
}