package com.project.capstoneproject.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import org.koin.android.viewmodel.ext.android.viewModel
import com.project.capstoneproject.databinding.FragmentMovieBinding
import com.project.capstoneproject.ui.detail.DetailActivity
import com.project.capstoneproject.ui.seemore.SeeMoreActivity
import com.project.capstoneproject.utils.gone
import com.project.capstoneproject.utils.visible
import com.project.core.data.source.Resource
import com.project.core.domain.model.Movie
import com.project.core.ui.MovieAdapter
import com.project.core.utils.Type

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding as FragmentMovieBinding

    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var nowPlayingMovieAdapter: MovieAdapter
    private lateinit var upComingMovieAdapter: MovieAdapter

    private lateinit var listener: ((Int) -> Unit)

    private val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleBackPressed(requireActivity())

        onAction()

        // init
        popularMovieAdapter = MovieAdapter(Type.MAIN)
        nowPlayingMovieAdapter = MovieAdapter(Type.MAIN)
        upComingMovieAdapter = MovieAdapter(Type.MAIN)
        listener = {
            Intent(activity, DetailActivity::class.java).also { intent ->
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE[0])
                startActivity(intent)
            }
        }

        if (activity != null) {

            getPopularMovie()

            getNowPlayinhMovie()

            getUpComingMovie()
        }

        getSearchMovie()
        getViewSearch(false)
    }

    private fun handleBackPressed(requireActivity: FragmentActivity) {
        requireActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val textSearch = binding.searchViewMovie.query
            if (textSearch.isNullOrBlank()) {
                this.isEnabled = false
                requireActivity.onBackPressed()
            } else {
                binding.searchViewMovie.setQuery("", false)
                getViewSearch(false)
            }
        }
    }

    private fun getSearchMovie() {
        binding.searchViewMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    getViewSearch(false)
                } else {
                    getDataSearch(newText)
                }
                return true
            }

        })

    }

    private fun getDataSearch(query: String?) {
        viewModel.getSearchMovie(query.toString()).observe(viewLifecycleOwner) {
            setUpAdapterSearch(it)
        }
    }

    private fun setUpAdapterSearch(data: List<Movie>?) {
        val adapter = MovieAdapter(Type.ALL).apply {
            onClick {
                listener(it)
            }
        }
        adapter.listMovies = data as MutableList<Movie>
        binding.apply {
            rvSearchMovie.setHasFixedSize(true)
            rvSearchMovie.adapter = adapter
        }

        getViewSearch(true)
    }

    private fun getUpComingMovie() {
        upComingMovieAdapter.onClick { listener(it) }

        viewModel.getUpComingMovie("upcoming").observe(this) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> binding.progressBarUpComing.visible()
                    is Resource.Success -> {
                        binding.progressBarUpComing.gone()
                        upComingMovieAdapter.listMovies =
                            movie.data as MutableList<Movie>
                    }
                    is Resource.Error -> {
                        binding.progressBarUpComing.gone()
                        Toast.makeText(activity, movie.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvUpComingMovie) {
            setHasFixedSize(true)
            adapter = upComingMovieAdapter
        }
    }

    private fun getNowPlayinhMovie() {
        nowPlayingMovieAdapter.onClick { listener(it) }

        viewModel.getNowPlayingMovie("nowplaying").observe(this) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> binding.progressBarNowPlaying.visible()
                    is Resource.Success -> {
                        binding.progressBarNowPlaying.gone()
                        nowPlayingMovieAdapter.listMovies =
                            movie.data as MutableList<Movie>
                    }
                    is Resource.Error -> {
                        binding.progressBarNowPlaying.gone()
                        Toast.makeText(activity, movie.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvNowPlayingMovie) {
            setHasFixedSize(true)
            adapter = nowPlayingMovieAdapter
        }
    }

    private fun getPopularMovie() {
        popularMovieAdapter.onClick { listener(it) }

        viewModel.getPopularMovie("popular").observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> binding.progressBarPopular.visible()
                    is Resource.Success -> {
                        binding.progressBarPopular.gone()
                        popularMovieAdapter.listMovies =
                            movie.data as MutableList<Movie>
                    }
                    is Resource.Error -> {
                        binding.progressBarPopular.gone()
                        Toast.makeText(activity, movie.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvPopularMovie) {
            setHasFixedSize(true)
            adapter = popularMovieAdapter
        }
    }

    private fun onAction() {
        binding.apply {

            val intentToSeeMoreActivity: (String) -> Unit = {
                Intent(activity, SeeMoreActivity::class.java).also { intent ->
                    intent.putExtra(SeeMoreActivity.EXTRA_DATA, it)
                    startActivity(intent)
                }
            }

            tvSeeMorePopularMovie.setOnClickListener { intentToSeeMoreActivity(SeeMoreActivity.EXTRA_VALUE[0]) }
            tvSeeMoreNowPlayingMovie.setOnClickListener { intentToSeeMoreActivity(SeeMoreActivity.EXTRA_VALUE[1]) }
            tvSeeMoreUpComingMovie.setOnClickListener { intentToSeeMoreActivity(SeeMoreActivity.EXTRA_VALUE[2]) }
        }
    }

    private fun getViewSearch(state: Boolean) {
        binding.apply {
            if (state) {
                rvSearchMovie.visible()
                rvPopularMovie.gone()
                rvNowPlayingMovie.gone()
                rvUpComingMovie.gone()
                textViewPopular.gone()
                textViewNowPlaying.gone()
                textViewUpComing.gone()
                tvSeeMorePopularMovie.gone()
                tvSeeMoreNowPlayingMovie.gone()
                tvSeeMoreUpComingMovie.gone()
            } else {
                rvSearchMovie.gone()
                rvPopularMovie.visible()
                rvNowPlayingMovie.visible()
                rvUpComingMovie.visible()
                textViewPopular.visible()
                textViewNowPlaying.visible()
                textViewUpComing.visible()
                tvSeeMorePopularMovie.visible()
                tvSeeMoreNowPlayingMovie.visible()
                tvSeeMoreUpComingMovie.visible()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}