package com.mornhouse.numbersinfoapp.presentation.details

import android.os.Bundle
import android.view.MenuItem
import com.mornhouse.numbersinfoapp.R
import com.mornhouse.numbersinfoapp.databinding.ActivityFrameBinding
import com.mornhouse.numbersinfoapp.presentation.ActivityUtils
import com.mornhouse.numbersinfoapp.presentation.BaseActivity

class NumberDetailsActivity: BaseActivity() {

    lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // add NumberDetailsFragment
        var numberDetailsFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as NumberDetailsFragment?
        if (numberDetailsFragment == null) {
            numberDetailsFragment = NumberDetailsFragment()
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager, numberDetailsFragment, R.id.contentFrame)
        }
        initActionBar()
    }

    private fun initActionBar(){
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)  // Show the "Back" button
            setDisplayShowHomeEnabled(true)  // Enable display of the icon
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Return to the previous activity
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}