package com.project.capstoneproject.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.project.capstoneproject.R
import com.project.capstoneproject.databinding.ActivityDetailBinding
import com.project.capstoneproject.utils.scope
import com.project.capstoneproject.utils.showSnackBar
import com.project.core.domain.model.Credit
import com.project.core.domain.model.Genre
import com.project.core.domain.model.Movie
import com.project.core.domain.model.TvShow
import com.project.core.ui.CreditAdapter
import com.project.core.ui.GenreAdapter
import com.project.core.utils.BASE_URL_API_IMAGE
import com.project.core.utils.POSTER_SIZE_W185
import com.project.core.utils.POSTER_SIZE_W780
import com.project.core.utils.loadImage
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTransparentStatusBar()

        getDataIntent()
    }

    private fun setTransparentStatusBar() {
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    private fun getDataIntent() {
        binding.apply {
            intent?.let {
                val data = it.getIntExtra(EXTRA_DATA, 0)

                when (it.getStringExtra(EXTRA_TYPE)) {
                    TYPE[0] -> {
                        viewModel.getMovieById(data).observe(this@DetailActivity) { movie ->
                            imgPoster.loadImage("$BASE_URL_API_IMAGE$POSTER_SIZE_W780${movie?.posterPath}")
                            imgBackdropPath.loadImage("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${movie?.backdropPath}")
                            tvTitle.text = movie?.title.toString()
                            tvReleaseDate.text = movie?.releaseDate.toString()
                            tvOverview.text = movie?.overview.toString()
                            
                            setUpToolbar(movie?.title)

                            statusFavorite(movie.isFavorite)
                            fabFavorite.setOnClickListener {
                                setFavorite(movie)
                            }

                            getCreditMovie(data)

                        }

                        viewModel.getDetailMovie(data).observe(this@DetailActivity){ movie ->
                            getGenres(movie?.genres)
                        }

                    }
                    TYPE[1] -> {
                        viewModel.getTvShowById(data).observe(this@DetailActivity) { tvshow ->
                            imgPoster.loadImage("$BASE_URL_API_IMAGE$POSTER_SIZE_W780${tvshow?.posterPath}")
                            imgBackdropPath.loadImage("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${tvshow?.backdropPath}")
                            tvTitle.text = tvshow?.name.toString()
                            tvReleaseDate.text = tvshow?.firstAirDate.toString()
                            tvOverview.text = tvshow?.overview.toString()

                            setUpToolbar(tvshow?.name)

                            statusFavorite(tvshow.isFavorite)
                            fabFavorite.setOnClickListener {
                                setFavorite(tvshow)
                            }

                            getCreditTvShow(data)
                        }

                        viewModel.getDetailTvShow(data).observe(this@DetailActivity){ tvshow ->
                            getGenres(tvshow.genres)
                        }
                    }
                }
            }
        }
    }

    private fun setFavorite(data: Any?) {
        when(data){
            is Movie -> {
                if (data.isFavorite)
                    binding.root.showSnackBar(resources.getString(R.string.remove_favorite, data.title))
                else
                    binding.root.showSnackBar(resources.getString(R.string.add_favorite, data.title))

                scope.launch {
                    viewModel.setFavoriteMovie(data)
                }
            }
            is TvShow -> {
                if (data.isFavorite)
                    binding.root.showSnackBar(resources.getString(R.string.remove_favorite, data.name))
                else
                    binding.root.showSnackBar(resources.getString(R.string.add_favorite, data.name))

                scope.launch {
                    viewModel.setFavoriteTvShow(data)
                }
            }
        }
    }

    private fun statusFavorite(state: Boolean) {
        binding.apply {
            if (state){
                fabFavorite.setImageResource(R.drawable.ic_favorite_true)
                fabFavorite.imageTintList = ColorStateList.valueOf(Color.RED)
            }
            else{
                fabFavorite.setImageResource(R.drawable.ic_out_favorite_false)
                fabFavorite.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimaryDark))
            }
        }
    }

    private fun setUpToolbar(title: String?) {
        binding.apply {
            toolbar.title = title
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            fabFavorite.elevation = 0f
            collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.TitleToolbar)
        }
    }

    private fun getCreditMovie(id: Int) {
        val adapter = CreditAdapter()
        viewModel.getCreditMovie(id).observe(this@DetailActivity) { credit ->
            adapter.credits = credit as MutableList<Credit>
        }
        binding.rvTopCast.apply {
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun getCreditTvShow(id: Int) {
        val adapter = CreditAdapter()
        viewModel.getCreditTvShow(id).observe(this@DetailActivity) { credit ->
            adapter.credits = credit as MutableList<Credit>
        }
        binding.rvTopCast.apply {
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun getGenres(genres: List<Genre>?) {
        val adapter = GenreAdapter()
        adapter.genres = genres as MutableList<Genre>
        binding.rvGenres.apply {
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_TYPE = "extra_reflect_class"
        val TYPE = arrayOf("movie", "tvshow")
    }
}