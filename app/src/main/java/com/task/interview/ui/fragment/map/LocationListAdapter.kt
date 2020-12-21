package com.task.interview.ui.fragment.map

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.task.interview.R
import com.task.interview.model.PlaceInfo
import kotlinx.android.synthetic.main.location_cell.view.*


class LocationListAdapter internal constructor(
    context: Context?,
    private val delegate: LocationItemListener
) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    private val mData = ArrayList<PlaceInfo>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = mInflater.inflate(R.layout.location_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = mData[position]

        holder.itemView.setOnClickListener {
            delegate.onClick(item,holder.itemView.imageView)
        }

        if (item.background_photo.isNotEmpty()) {
            val imageUrl = item.background_photo
            Glide
                .with(holder.itemView.context)
                .load(imageUrl)
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholderloading)
                .into(holder.itemView.imageView)
        }

        holder.itemView.locationName.text = item.name
        holder.itemView.locationType.text = item.type
        holder.itemView.locationRate.text = item.rate.toString()
        holder.itemView.locationTime.text = "${item.open} ${item.close}"
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun addItems(locations :ArrayList<PlaceInfo>) {
        mData.addAll(locations)
        notifyDataSetChanged()
    }
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}


class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
    RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int =
        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }
}

interface LocationItemListener {
    fun onClick(item: PlaceInfo, imageView: ImageView)
}
