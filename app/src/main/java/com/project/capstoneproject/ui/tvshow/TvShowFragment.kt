package com.project.capstoneproject.ui.tvshow

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
import com.project.capstoneproject.databinding.FragmentTvShowBinding
import com.project.capstoneproject.ui.detail.DetailActivity
import com.project.capstoneproject.ui.seemore.SeeMoreActivity
import com.project.capstoneproject.utils.gone
import com.project.capstoneproject.utils.visible
import com.project.core.data.source.Resource
import com.project.core.domain.model.TvShow
import com.project.core.ui.TvShowAdapter
import com.project.core.utils.Type
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding as FragmentTvShowBinding

    private lateinit var popularAdapter: TvShowAdapter
    private lateinit var airingTodayAdapter: TvShowAdapter
    private lateinit var topRateAdapter: TvShowAdapter

    private lateinit var listener: ((Int) -> Unit)

    private val viewModel: TvShowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvShowBinding.inflate(LayoutInflater.from(container?.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleBackPressed(requireActivity())

        onAction()

        // init
        popularAdapter = TvShowAdapter(Type.MAIN)
        airingTodayAdapter = TvShowAdapter(Type.MAIN)
        topRateAdapter = TvShowAdapter(Type.MAIN)
        listener = {
            Intent(activity, DetailActivity::class.java).also { intent ->
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE[1])
                startActivity(intent)
            }
        }

        if (activity != null){
            getPopularTvShow()
            getAiringTodayTvShow()
            getTopRateTvShow()
        }

        getSearchTvShow()
        getViewSearch(false)
    }

    private fun handleBackPressed(requireActivity: FragmentActivity) {
        requireActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val textSearch = binding.searchViewTvshow.query
            if (textSearch.isNullOrBlank()){
                this.isEnabled = false
                requireActivity.onBackPressed()
            }else{
                binding.searchViewTvshow.setQuery("", false)
                getViewSearch(false)
            }
        }
    }

    private fun getSearchTvShow() {
        binding.searchViewTvshow.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()){
                    getViewSearch(false)
                }else{
                    getDataSearch(newText)
                }
                
                return true
            }

        })
    }

    private fun getDataSearch(newText: String) {
        viewModel.getSearchTvShow(newText).observe(viewLifecycleOwner){
            setUpAdapterSearch(it)
        }
    }

    private fun setUpAdapterSearch(data: List<TvShow>){
        val adapter = TvShowAdapter(Type.ALL).apply {
            onClick {
                listener(it)
            }
        }
        adapter.tvshow = data as MutableList<TvShow>
        binding.apply {
            rvSearchTvshow.setHasFixedSize(true)
            rvSearchTvshow.adapter = adapter
        }

        getViewSearch(true)
    }

    private fun getTopRateTvShow() {
        topRateAdapter.onClick { listener(it) }

        viewModel.getTopRateTvShow("toprate").observe(viewLifecycleOwner){ tvshow ->
            if (tvshow != null){
                when(tvshow){
                    is Resource.Loading -> binding.progressBarTopRate.visible()
                    is Resource.Success -> {
                        binding.progressBarTopRate.gone()
                        topRateAdapter.tvshow = tvshow.data as MutableList<TvShow>
                    }
                    is Resource.Error -> {
                        binding.progressBarTopRate.gone()
                        Toast.makeText(activity, tvshow.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvTopRateTvshow){
            setHasFixedSize(true)
            adapter = topRateAdapter
        }
    }

    private fun getAiringTodayTvShow() {
        airingTodayAdapter.onClick { listener(it) }

        viewModel.getAiringTodayTvShow("airingtoday").observe(viewLifecycleOwner){ tvshow ->
            if (tvshow != null){
                when(tvshow){
                    is Resource.Loading -> binding.progressBarAiringToday.visible()
                    is Resource.Success -> {
                        binding.progressBarAiringToday.gone()
                        airingTodayAdapter.tvshow = tvshow.data as MutableList<TvShow>
                    }
                    is Resource.Error -> {
                        binding.progressBarAiringToday.gone()
                        Toast.makeText(activity, tvshow.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvAiringTodayTvshow){
            setHasFixedSize(true)
            adapter = airingTodayAdapter
        }
    }

    private fun getPopularTvShow() {
        popularAdapter.onClick { listener(it) }

        viewModel.getPopularTvShow("popular").observe(viewLifecycleOwner){ tvshow ->
            if (tvshow != null){
                when(tvshow){
                    is Resource.Loading -> binding.progressBarPopular.visible()
                    is Resource.Success -> {
                        binding.progressBarPopular.gone()
                        popularAdapter.tvshow = tvshow.data as MutableList<TvShow>
                    }
                    is Resource.Error -> {
                        binding.progressBarPopular.gone()
                        Toast.makeText(activity, tvshow.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        with(binding.rvPopularTvshow){
            setHasFixedSize(true)
            adapter = popularAdapter
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

            tvSeeMorePopularTvshow.setOnClickListener { intentToSeeMoreActivity(SeeMoreActivity.EXTRA_VALUE[3]) }
            tvSeeMoreAiringTodayTvshow.setOnClickListener { intentToSeeMoreActivity(SeeMoreActivity.EXTRA_VALUE[4]) }
            tvSeeMoreTopRateTvshow.setOnClickListener { intentToSeeMoreActivity(SeeMoreActivity.EXTRA_VALUE[5]) }
        }
    }

    private fun getViewSearch(state: Boolean) {
        binding.apply {
            if (state){
                rvSearchTvshow.visible()
                rvAiringTodayTvshow.gone()
                rvPopularTvshow.gone()
                rvTopRateTvshow.gone()
                textViewPopular.gone()
                textViewAiringToday.gone()
                textViewTopRateTvshow.gone()
                tvSeeMoreAiringTodayTvshow.gone()
                tvSeeMorePopularTvshow.gone()
                tvSeeMoreTopRateTvshow.gone()
            }else{
                rvSearchTvshow.gone()
                rvAiringTodayTvshow.visible()
                rvPopularTvshow.visible()
                rvTopRateTvshow.visible()
                textViewPopular.visible()
                textViewAiringToday.visible()
                textViewTopRateTvshow.visible()
                tvSeeMoreAiringTodayTvshow.visible()
                tvSeeMorePopularTvshow.visible()
                tvSeeMoreTopRateTvshow.visible()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}