package com.task.interview.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.task.interview.R
import com.task.interview.model.Comment
import kotlinx.android.synthetic.main.comment_layout.view.*


class CommentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.Comment, 0, 0)

        val name = a.getString(R.styleable.Comment_name)
        val comment = a.getString(R.styleable.Comment_comment)
        val dateTime = a.getString(R.styleable.Comment_dateTime)
        val userPhoto = a.getString(R.styleable.Comment_userPhoto)
        val rate = a.getInteger(R.styleable.Comment_rate, 0)


        a.recycle()

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.comment_layout, this, true)

        Glide
            .with(this)
            .load(userPhoto)
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholderloading)
            .error(R.drawable.ic_account)
            .into(ivUserImage)
        tvUserName.text = name
        tvComment.text = comment
        tvDateTime.text = dateTime
        tvRate.text = rate.toString()

    }

    fun setData(comment: Comment): CommentView {
        Glide
            .with(this)
            .load(comment.photo)
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholderloading)
            .error(R.drawable.ic_account)
            .into(ivUserImage)
        tvUserName.text = comment.name
        tvDateTime.text = comment.date
        tvRate.text = comment.rate
        tvComment.text = comment.text

        return this
    }

}