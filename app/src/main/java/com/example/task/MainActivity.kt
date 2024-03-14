package com.example.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.task.databinding.ActivityMainBinding
import com.example.task.di.ServiceLocator

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var binding : ActivityMainBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.setContext(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                as NavHostFragment
        val controller = navHostFragment.navController
    }

}