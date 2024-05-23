package com.example.task

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.databinding.ActivityMainBinding
import com.example.task.presentation.MainActivityViewModel
import com.example.task.presentation.di.ViewModelFactory
import com.example.task.utils.component

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding : ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel : MainActivityViewModel by viewModels {
        ViewModelFactory(this) {
            component.mainActivityViewModel().create()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                as NavHostFragment
        val controller = navHostFragment.navController
        val currentDestination = viewModel.getCurrentDestination()
        if (currentDestination != -1) {
            controller.navigate(currentDestination)
            viewModel.deleteCurrentDestination()
        } else if (viewModel.getFirstRun()) {
            controller.navigate(R.id.welcomeFragment)
        } else if (viewModel.checkIfAuthenticated()) {
            controller.navigate(R.id.holderFragment)
        } else {
            controller.navigate(R.id.authFragment)
        }
    }

}