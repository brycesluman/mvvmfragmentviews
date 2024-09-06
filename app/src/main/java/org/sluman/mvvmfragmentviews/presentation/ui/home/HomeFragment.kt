package org.sluman.mvvmfragmentviews.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.sluman.mvvmfragmentviews.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var countryView: RecyclerView
    private lateinit var loadingView: View
    private lateinit var errorView: View
    private lateinit var retryButton: Button
    private lateinit var errorText: TextView

    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModelFactory = HomeViewModel.Factory
        homeViewModel = ViewModelProvider(this,viewModelFactory)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        countryView = binding.recyclerviewCountries
        loadingView = binding.progressBar
        errorView = binding.errorView
        errorText = binding.errorText
        retryButton = binding.retryButton

        retryButton.setOnClickListener {
            homeViewModel.getData()
        }

        val countryAdapter = CountryAdapter {
            Toast.makeText(this.context, "${it.name} clicked", Toast.LENGTH_SHORT).show()
        }

        countryView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = countryAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.state.collect { state ->

                if (state.error != "" && state.dataItems.isEmpty()) {
                    errorView.visibility = View.VISIBLE
                    errorText.text = state.error
                } else {
                    errorView.visibility = View.GONE
                }

                if (state.isLoading) {
                    loadingView.visibility = View.VISIBLE
                } else {
                    loadingView.visibility = View.GONE
                }

                countryAdapter.submitList(state.dataItems)

            }
        }
        return root
    }

    override fun onPause() {
        super.onPause()
        homeViewModel.scrollPosition =
            (countryView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
    }

    override fun onResume() {
        super.onResume()
        (countryView.layoutManager as LinearLayoutManager).scrollToPosition(homeViewModel.scrollPosition)
        homeViewModel.scrollPosition = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

