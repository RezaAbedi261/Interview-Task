package com.task.interview.ui.fragment.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.task.interview.R
import com.task.interview.base.BaseFragment
import com.task.interview.databinding.FragmentSplashBinding
import com.task.interview.utils.NavigationAnimations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<FragmentSplashBinding, SplashFragmentVM>() {

    override val viewModel: SplashFragmentVM by viewModels()

    override fun layout() = R.layout.fragment_splash;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            navigate(SplashFragmentDirections.actionSplashToMap(), NavigationAnimations.noAnim())
        }
    }


}