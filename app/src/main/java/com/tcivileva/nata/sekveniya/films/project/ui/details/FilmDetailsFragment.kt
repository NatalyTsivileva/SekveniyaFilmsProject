package com.tcivileva.nata.sekveniya.films.project.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tcivileva.nata.sekveniya.films.project.R
import com.tcivileva.nata.sekveniya.films.project.collectFlow
import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.databinding.FragmentFilmDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class FilmDetailsFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null

    private val args: FilmDetailsFragmentArgs by navArgs()

    private val viewModel: FilmDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback {
            activity?.findNavController(R.id.nav_host_fragment)?.popBackStack()
        }
    }

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
        setupUI()

        viewModel.getFilmInfo(args.filmID)

        collectFlow(viewModel.filmsDetails){
            updateUI(it)
        }
    }

    private fun setupUI(){
        _binding?.filmsToolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateUI(film: Film){
        _binding?.let {
            Glide.with(it.root.context)
                .load(film.image)
                .placeholder(R.drawable.img_placeholder)
                .into(it.filmImage);

            it.nameRu.text = film.nameRu

            it.filmsToolbar.title = film.name

            //срединяем все жанры через запятую при этом делая первую букву каждого жанра маленькой
            var genreString = film.genres.joinToString(", ") { genre ->
                genre
                    .lowercase()
                    .replaceFirstChar { char -> char.lowercaseChar() }
            }

            genreString+=", ${film.year} год"

            it.genreAndYear.text = genreString

            it.rating.text = String.format(Locale.ROOT,"%.1f", film.rating)

            it.kinopoisText.text = it.root.context.getText(R.string.brand_kinopoisk)

            it.filmDescription.text = film.description
        }
    }
}