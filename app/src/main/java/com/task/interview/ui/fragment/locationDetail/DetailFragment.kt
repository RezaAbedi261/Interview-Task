package com.task.interview.ui.fragment.locationDetail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.task.interview.R
import com.task.interview.base.BaseFragment
import com.task.interview.databinding.FragmentDetailBinding
import com.task.interview.ui.customview.CommentView
import kotlinx.android.synthetic.main.fragment_detail.*
import java.lang.StringBuilder


class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentVM>() {

    override val viewModel: DetailFragmentVM by viewModels()
    val args: DetailFragmentArgs by navArgs()
    private lateinit var recyclerViewAdapter: ImagesRecyclerViewAdapter

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
        val locationInfo = args.placeInfo
        Glide.with(this).load(locationInfo.background_photo).into(imageView)

        tvName.text = "${locationInfo.name}"
        tvType.text = locationInfo.type
        tvTime.text = "ساعات کاری : ${locationInfo.open}  ${locationInfo.close}"
        tvAddress.text = "آدرس : ${locationInfo.address}"
        var options = StringBuilder()
        locationInfo.options.forEach {
           options.append(it+"\n")
        }
        tvOptions.text = "ویژگی‌ها :\n$options"
        imagesRecyclerView

        recyclerViewAdapter = ImagesRecyclerViewAdapter(locationInfo.photos)
        imagesRecyclerView.adapter= recyclerViewAdapter

        locationInfo.comments.forEach {
            llComments.addView(CommentView(requireContext()).setData(it))
        }
    }


}