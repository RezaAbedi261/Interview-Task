package com.task.interview.ui.fragment.locationDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.task.interview.R
import com.task.interview.base.BaseFragment
import com.task.interview.databinding.FragmentDetailBinding


class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentVM>() {

    override val viewModel: DetailFragmentVM by viewModels()

    override fun layout() = R.layout.fragment_detail;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}