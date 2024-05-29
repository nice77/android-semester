package com.example.task

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding : ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                as NavHostFragment
        val controller = navHostFragment.navController
        permissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

}