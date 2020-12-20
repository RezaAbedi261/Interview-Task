package com.task.interview.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.task.interview.App
import com.task.interview.di.inject
import com.task.interview.ui.dialog.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    LifecycleOwner, CoroutineScope by CoroutineScope(
        Dispatchers.Main
    ) {

    @LayoutRes
    abstract fun layout(): Int

    protected abstract val viewModel: VM
    protected lateinit var binding: VB
    lateinit var loadingView: LoadingDialog
    var isLoadingDialog: Boolean = false

    val app: App by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, layout(), container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner

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

        loadingView = LoadingDialog(requireActivity())
        loadingView.setOnShowListener {
            isLoadingDialog = true
        }

        loadingView.setOnDismissListener {
            isLoadingDialog = false
        }
        return binding.root

    }


    open fun liveDataObservers() {}

    fun <T> observe(livaData: MutableLiveData<T>, function: (T) -> Unit) {
        livaData.observe(this@BaseFragment) {
            function.invoke(it)
        }
    }
    fun <T> observe(livaData: LiveData<T>, function: (T) -> Unit) {
        livaData.observe(this@BaseFragment) {
            function.invoke(it)
        }
    }



    fun navigate(destination: NavDirections, options: NavOptions?) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination,options) }
    }

    fun navigate(destination: NavDirections, options: NavOptions?, navigatorExtras:Navigator.Extras?) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination.actionId, null, options, navigatorExtras) }
//        navigate(destination.actionId, null,options, navigatorExtras)
    }


}
