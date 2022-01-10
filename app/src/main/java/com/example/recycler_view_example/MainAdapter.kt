package com.example.recycler_view_example

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view_example.databinding.RowEntryBinding
import com.squareup.picasso.Picasso

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val cellForRow = RowEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video = homeFeed.videos[position]
        holder.vidName.text = video.name
        holder.chanName.text = video.channel.name + " * " + video.numberOfViews.toString() + " views"
        val tiv = holder.thumbnailImageView
        val civ = holder.channelImageView
        Picasso.get().load(video.imageUrl).into(tiv)
        Picasso.get().load(video.channel.profileimageUrl).into(civ)
        holder.video = video
    }
}

class CustomViewHolder(private val binding: RowEntryBinding, var video: Video? = null): RecyclerView.ViewHolder(binding.root) {
    companion object {
        val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }

    val vidName: TextView = binding.textViewVideoTitle
    val chanName: TextView = binding.textViewChannelName
    val thumbnailImageView: ImageView = binding.imageViewVideoThumbnail
    val channelImageView: ImageView = binding.imageViewChannelProfile

    init {
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, CourseDetailActivity::class.java)
            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)
            binding.root.context.startActivity(intent)
        }
    }
}
