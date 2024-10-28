package com.mornhouse.numbersinfoapp.presentation.search

import android.os.Bundle
import com.mornhouse.numbersinfoapp.R
import com.mornhouse.numbersinfoapp.databinding.ActivityFrameBinding
import com.mornhouse.numbersinfoapp.presentation.ActivityUtils
import com.mornhouse.numbersinfoapp.presentation.BaseActivity

class SearchNumberInfoActivity : BaseActivity() {

    lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // add SearchNumberInfoFragment
        var searchNumbersInfoFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as SearchNumberInfoFragment?
        if (searchNumbersInfoFragment == null) {
            searchNumbersInfoFragment = SearchNumberInfoFragment()
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager, searchNumbersInfoFragment, R.id.contentFrame)
        }
    }

}