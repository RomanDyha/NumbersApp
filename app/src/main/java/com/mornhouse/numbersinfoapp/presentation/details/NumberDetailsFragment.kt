package com.mornhouse.numbersinfoapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mornhouse.numbersinfoapp.databinding.FragmentDetailsNumberBinding
import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.presentation.search.SearchNumberInfoFragment.Companion.NUMBER_EXTRA_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NumberDetailsFragment: Fragment() {

    private var binding: FragmentDetailsNumberBinding? = null
    private val viewModel by viewModel<NumberDetailsViewModel> {
        parametersOf(requireActivity().intent.getLongExtra(NUMBER_EXTRA_KEY, 0))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailsNumberBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        updateUI()
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
                        numberTextView.text = it.data?.number.toString()
                        numberDescriptionTextView.text = it.data?.description
                    }

                    DataStatus.Status.ERROR -> {
                        binding?.progressBar?.visibility = GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}