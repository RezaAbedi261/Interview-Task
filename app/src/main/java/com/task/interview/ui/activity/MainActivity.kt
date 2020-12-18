package com.task.interview.ui.activity

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.task.interview.R
import com.task.interview.base.BaseActivity
import com.task.interview.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }


        }
    }

    override fun layout(): Int {
        return R.layout.activity_main
    }

    override val viewModel: MainViewModel by viewModels()

    override fun liveDataObservers() {

    }
}