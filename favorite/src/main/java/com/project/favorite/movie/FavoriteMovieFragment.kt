package com.project.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.capstoneproject.ui.detail.DetailActivity
import com.project.capstoneproject.utils.gone
import com.project.capstoneproject.utils.hideEmptyFavorite
import com.project.capstoneproject.utils.showEmptyFavorite
import com.project.capstoneproject.utils.visible
import com.project.core.domain.model.Movie
import com.project.core.ui.MovieAdapter
import com.project.core.utils.Type
import com.project.favorite.FavoriteViewModel
import com.project.favorite.databinding.FragmentFavoriteMovieBinding
import com.project.favorite.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteMovieFragment : Fragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding as FragmentFavoriteMovieBinding

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        // init
        movieAdapter = MovieAdapter(Type.ALL)

        getFavoriteMovies()

        onAction()
    }

    private fun onAction() {
        movieAdapter.onClick {
            Intent(activity, DetailActivity::class.java).also { intent ->
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE[0])
                startActivity(intent)
            }
        }
    }

    private fun getFavoriteMovies() {
        binding.apply {

            binding.rvFavoriteMovie.setHasFixedSize(true)
            binding.rvFavoriteMovie.adapter = movieAdapter

            viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    rvFavoriteMovie.gone()
                    showEmptyFavorite(
                        imgEmptyState,
                        titleEmptyState,
                        descEmptyState
                    )
                } else {
                    rvFavoriteMovie.visible()
                    hideEmptyFavorite(
                        imgEmptyState,
                        titleEmptyState,
                        descEmptyState
                    )
                    movieAdapter.listMovies = it as MutableList<Movie>

                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}