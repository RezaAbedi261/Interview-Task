package com.task.interview.base

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.task.interview.R
import com.task.interview.didInitKoin
import com.task.interview.utils.AndroidUIUtils
import com.task.interview.utils.LocaleHelper
import com.task.interview.utils.UiHelper
import io.andref.rx.network.RxNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import rx.Subscription
import java.lang.ref.WeakReference


abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    LifecycleOwner, CoroutineScope by CoroutineScope(
        Dispatchers.Main
    ) {

    private lateinit var connectivityChanges: Subscription
    private var currentError: WeakReference<Snackbar>? = null
    var view: Fragment? = null

    @LayoutRes
    abstract fun layout(): Int

    protected abstract val viewModel: VM
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        LocaleHelper.changeAppLocaleFromSharedPrefIfNeeded(this, true)
        view?.view?.let { LocaleHelper.setLayoutDirectionBasedOnLocale(it) }

        binding = DataBindingUtil.setContentView(this, layout())
        binding.lifecycleOwner = this

        try {
            val setVmMethod = binding.javaClass.getMethod("setVm", viewModel.javaClass)
            setVmMethod.invoke(binding, viewModel)
        } catch (ignored: Throwable) {
        }

        try {
            val setViewMethod = binding.javaClass.getMethod("setView", this.javaClass)
            setViewMethod.invoke(binding, this)
        } catch (ignored: Throwable) {
        }

        liveDataObservers()
        observeNetworkState()
        resetStatusBarColor()

    }


    open fun liveDataObservers() {}

    fun <T> observe(livaData: MutableLiveData<T>, function: (T) -> Unit) {
        livaData.observe(this@BaseActivity) {
            function.invoke(it)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(this.application)//rtl

    }

    override fun attachBaseContext(newBase: Context) {
        if(didInitKoin) {
            val newContext = LocaleHelper.changeLocaleInContext(newBase)
            super.attachBaseContext(newContext)
        } else {
            super.attachBaseContext(newBase)
        }

    }


    private fun observeNetworkState() {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityChanges= RxNetwork.connectivityChanges(this, connectivityManager)
            .subscribe { connected ->
                viewModel.connectivity.value = connected
            }

    }

    fun resetStatusBarColor() {
        AndroidUIUtils.setStatusBarColorRes(this, R.color.white)
    }

    private fun dismissSnackBar() {
        if (currentError?.get() != null) {
            currentError?.get()?.dismiss()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        if(::connectivityChanges.isInitialized){
            connectivityChanges.unsubscribe()
        }
    }

}
