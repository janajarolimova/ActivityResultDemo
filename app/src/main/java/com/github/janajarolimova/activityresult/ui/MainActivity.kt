package com.github.janajarolimova.activityresult.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.janajarolimova.activityresult.R
import com.github.janajarolimova.activityresult.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

    }

}