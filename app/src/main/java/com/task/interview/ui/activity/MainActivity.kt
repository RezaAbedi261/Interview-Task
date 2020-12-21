package com.task.interview.ui.activity

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.task.interview.R
import com.task.interview.base.BaseActivity
import com.task.interview.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    var navController : NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController?.addOnDestinationChangedListener { _, destination, _ ->
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

    override fun onBackPressed() {
        val navDestination: NavDestination? = navController?.getCurrentDestination()
        if (navDestination != null
            && navDestination.id == R.id.map
        ) {
            finish()
            return
        }
        super.onBackPressed()
    }
}