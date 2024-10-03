package org.sluman.mvvmfragmentviews.presentation.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.textfield.TextInputEditText
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
    private lateinit var searchDialog: TextInputEditText

    private lateinit var homeViewModel: HomeViewModel

    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // this function is called before text is edited
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // this function is called when text is edited
            homeViewModel.search(s.toString())
        }

        override fun afterTextChanged(s: Editable) {
            // this function is called after text is edited
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModelFactory = HomeViewModel.Factory
        homeViewModel = ViewModelProvider(this,viewModelFactory)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        searchDialog = binding.searchDialog
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

        searchDialog.addTextChangedListener(textWatcher)


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

