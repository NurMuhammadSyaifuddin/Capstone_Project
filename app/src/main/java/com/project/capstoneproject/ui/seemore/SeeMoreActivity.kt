package com.project.capstoneproject.ui.seemore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.capstoneproject.databinding.ActivitySeeMoreBinding
import com.project.capstoneproject.ui.detail.DetailActivity
import com.project.capstoneproject.ui.movie.MovieViewModel
import com.project.capstoneproject.ui.tvshow.TvShowViewModel
import com.project.core.data.source.Resource
import com.project.core.domain.model.Movie
import com.project.core.domain.model.TvShow
import com.project.core.ui.MovieAdapter
import com.project.core.ui.TvShowAdapter
import com.project.core.utils.Type
import org.koin.android.viewmodel.ext.android.viewModel

class SeeMoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeeMoreBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvShowAdapter: TvShowAdapter

    private lateinit var listenerMovie: (Int) -> Unit
    private lateinit var listenerTvShow: (Int) -> Unit

    private val viewModelMovie: MovieViewModel by viewModel()
    private val viewModelTvShow: TvShowViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        movieAdapter = MovieAdapter(Type.ALL)
        tvShowAdapter = TvShowAdapter(Type.ALL)
        listenerMovie = {
            Intent(this, DetailActivity::class.java).also { intent ->
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE[0])
                startActivity(intent)
            }
        }
        listenerTvShow = {
            Intent(this, DetailActivity::class.java).also { intent ->
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE[1])
                startActivity(intent)
            }
        }

        getDataIntent()

        setUpToolbar()

    }

    private fun getDataIntent() {
        binding.apply {
            intent?.let {
                when(it.extras?.getString(EXTRA_DATA)){
                    EXTRA_VALUE[0] -> {
                        binding.toolbar.title = EXTRA_VALUE[0]
                        getMovies("popular")
                    }
                    EXTRA_VALUE[1] -> {
                        binding.toolbar.title = EXTRA_VALUE[1]
                        getMovies("nowplaying")
                    }
                    EXTRA_VALUE[2] -> {
                        binding.toolbar.title = EXTRA_VALUE[2]
                        getMovies("upcoming")
                    }
                    EXTRA_VALUE[3] -> {
                        binding.toolbar.title = EXTRA_VALUE[3]
                        getTvShow("popular")
                    }
                    EXTRA_VALUE[4] -> {
                        binding.toolbar.title = EXTRA_VALUE[4]
                        getTvShow("airingtoday")
                    }
                    EXTRA_VALUE[5] -> {
                        binding.toolbar.title = EXTRA_VALUE[5]
                        getTvShow("toprate")
                    }
                }
            }
        }
    }

    private fun getTvShow(type: String) {
        tvShowAdapter.onClick { listenerTvShow(it) }

        when(type){
            "popular" -> {
                viewModelTvShow.getPopularTvShow(type).observe(this){ tvshow ->
                    if (tvshow != null){
                        when(tvshow){
                            is Resource.Loading -> {}
                            is Resource.Success -> tvShowAdapter.tvshow = tvshow.data as MutableList<TvShow>
                            is Resource.Error -> {
                            }
                        }
                    }
                }
            }
            "airingtoday" -> {
                viewModelTvShow.getAiringTodayTvShow(type).observe(this){ tvshow ->
                    if (tvshow != null){
                        when(tvshow){
                            is Resource.Loading -> {}
                            is Resource.Success -> tvShowAdapter.tvshow = tvshow.data as MutableList<TvShow>
                            is Resource.Error -> {
                            }
                        }
                    }
                }
            }
            "toprate" -> {
                viewModelTvShow.getPopularTvShow(type).observe(this){ tvshow ->
                    if (tvshow != null){
                        when(tvshow){
                            is Resource.Loading -> {}
                            is Resource.Success -> tvShowAdapter.tvshow = tvshow.data as MutableList<TvShow>
                            is Resource.Error -> {
                            }
                        }
                    }
                }
            }
        }

        with(binding.rvSeeMore){
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

    private fun getMovies(type: String) {
        movieAdapter.onClick { listenerMovie(it) }

        when(type){
            TYPE[0] -> {
                viewModelMovie.getPopularMovie(type).observe(this){ movie ->
                    if (movie != null){
                        when(movie){
                            is Resource.Loading -> {}
                            is Resource.Success -> movieAdapter.listMovies = movie.data as MutableList<Movie>
                            is Resource.Error -> {
                            }
                        }
                    }
                }
            }
            TYPE[1] -> {
                viewModelMovie.getNowPlayingMovie(type).observe(this){ movie ->
                    if (movie != null){
                        when(movie){
                            is Resource.Loading -> {}
                            is Resource.Success -> movieAdapter.listMovies = movie.data as MutableList<Movie>
                            is Resource.Error -> {
                            }
                        }
                    }
                }
            }
            TYPE[2] -> {
                viewModelMovie.getUpComingMovie(type).observe(this){ movie ->
                    if (movie != null){
                        when(movie){
                            is Resource.Loading -> {}
                            is Resource.Success -> movieAdapter.listMovies = movie.data as MutableList<Movie>
                            is Resource.Error -> {
                            }
                        }
                    }
                }
            }
        }

        with(binding.rvSeeMore){
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.elevation = 0f
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
        val EXTRA_VALUE = arrayOf("Popular Movie", "Now Playing Movie", "Up Coming Movie", "Popular Tv Show", "Airing Today Tv Show", "Top Rate Tv Show")
        val TYPE = arrayOf("popular", "nowplaying", "upcoming")
    }
}