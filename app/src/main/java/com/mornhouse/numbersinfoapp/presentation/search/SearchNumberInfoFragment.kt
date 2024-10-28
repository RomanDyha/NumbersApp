package com.mornhouse.numbersinfoapp.presentation.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mornhouse.numbersinfoapp.databinding.FragmentSearchNumberBinding
import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo
import com.mornhouse.numbersinfoapp.presentation.details.NumberDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchNumberInfoFragment : Fragment(), NumbersRecyclerAdapter.OnClickListener {

    private var binding: FragmentSearchNumberBinding? = null
    private val viewModel by viewModel<SearchNumberInfoViewModel>()
    private var numbersRecyclerAdapter: NumbersRecyclerAdapter? = null

    companion object {
        val NUMBER_EXTRA_KEY = "NUMBER_EXTRA_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSearchNumberBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        performSearch()
        initSearchButton()
        updateUI()
    }

    private fun performSearch() {
        binding?.searchInputView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 0 && newText.length <= 19)
                    binding?.searchNumberButton?.isEnabled = true
                else
                    binding?.searchNumberButton?.isEnabled = false
                return true
            }
        })
    }

    private fun initSearchButton() {
        binding?.searchNumberButton?.setOnClickListener {
            viewModel.searchNumberInfo(binding?.searchInputView?.query.toString().toLong())
        }

        binding?.randomNumberButton?.setOnClickListener {
            viewModel.searchRandomNumberInfo()
        }
    }

    private fun initNumbersRecyclerAdapter(numbersInfoList: List<NumberInfo>) {
        if (numbersRecyclerAdapter == null) {
            binding?.numbersRecyclerView?.layoutManager = LinearLayoutManager(activity)
            binding?.numbersRecyclerView?.setHasFixedSize(true)
            numbersRecyclerAdapter = NumbersRecyclerAdapter(numbersInfoList)
            binding?.numbersRecyclerView?.adapter = numbersRecyclerAdapter
            numbersRecyclerAdapter!!.setOnClickListener(this)
        } else {
            numbersRecyclerAdapter!!.updateNumbersInfo(numbersInfoList)
        }
    }

    private fun updateUI() {
        binding?.apply {
            viewModel.numberInfo.observe(viewLifecycleOwner) {
                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        progressBar.visibility = VISIBLE
                    }

                    DataStatus.Status.SUCCESS -> {
                        progressBar.visibility = GONE
                        numberInfoTextView.text = it.data?.description
                        viewModel.getSearchHistory()
                    }

                    DataStatus.Status.ERROR -> {
                        binding?.progressBar?.visibility = GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding?.apply {
            viewModel.numbersInfoList.observe(viewLifecycleOwner) {
                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        progressBar.visibility = VISIBLE
                    }

                    DataStatus.Status.SUCCESS -> {
                        progressBar.visibility = GONE
                        it.data?.let { it1 -> initNumbersRecyclerAdapter(it1) }
                    }

                    DataStatus.Status.ERROR -> {
                        binding?.progressBar?.visibility = GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onClick(number: Long) {
        openMovieDetailsActivity(number)
    }

    // show number details screen
    private fun openMovieDetailsActivity(number: Long) {
        startActivity(
            Intent(requireActivity(), NumberDetailsActivity::class.java)
                .putExtra(NUMBER_EXTRA_KEY, number)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}