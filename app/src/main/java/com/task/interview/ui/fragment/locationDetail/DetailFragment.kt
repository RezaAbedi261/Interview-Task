package com.task.interview.ui.fragment.locationDetail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.task.interview.R
import com.task.interview.base.BaseFragment
import com.task.interview.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentVM>() {

    override val viewModel: DetailFragmentVM by viewModels()
    val args: DetailFragmentArgs by navArgs()

    override fun layout() = R.layout.fragment_detail;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.transitionName = "imageView"
        }
        Glide.with(this).load(args.placeInfo.background_photo).into(imageView)
    }


}