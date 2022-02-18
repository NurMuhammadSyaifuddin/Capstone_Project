package com.project.favorite.tvshow

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
import com.project.core.domain.model.TvShow
import com.project.core.ui.TvShowAdapter
import com.project.core.utils.Type
import com.project.favorite.FavoriteViewModel
import com.project.favorite.databinding.FragmentFavoriteTvShowBinding
import com.project.favorite.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteTvShowFragment : Fragment() {

    private var _binding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _binding as FragmentFavoriteTvShowBinding

    private lateinit var tvshowAdapter: TvShowAdapter

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        // init
        tvshowAdapter = TvShowAdapter(Type.ALL)

        getFavoriteTvShow()

        onAction()
    }

    private fun onAction() {
        tvshowAdapter.onClick {
            Intent(activity, DetailActivity::class.java).also { intent ->
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE[1])
                startActivity(intent)
            }
        }
    }

    private fun getFavoriteTvShow() {
        binding.apply {
            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    rvFavoriteTvshow.gone()
                    showEmptyFavorite(
                        imgEmptyState,
                        titleEmptyState,
                        descEmptyState
                    )

                } else {
                    rvFavoriteTvshow.visible()
                    hideEmptyFavorite(
                        imgEmptyState,
                        titleEmptyState,
                        descEmptyState
                    )
                    tvshowAdapter.tvshow = it as MutableList<TvShow>

                }
            }

            rvFavoriteTvshow.setHasFixedSize(true)
            rvFavoriteTvshow.adapter = tvshowAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}