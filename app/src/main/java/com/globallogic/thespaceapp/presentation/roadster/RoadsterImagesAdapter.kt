package com.globallogic.thespaceapp.presentation.roadster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.ItemRoadsterImageBinding

class RoadsterImagesAdapter constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<RoadsterImagesAdapter.RoadsterViewHolder>() {

    inner class RoadsterViewHolder(val binding: ItemRoadsterImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    var images = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadsterViewHolder {
        val binding = ItemRoadsterImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RoadsterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoadsterViewHolder, position: Int) {
        with(holder) {
            with(images[position]) {
                glide
                    .load(this)
                    .placeholder(R.drawable.roadster_crop)
                    .into(binding.ivRoadster)
            }
        }
    }

    override fun getItemCount() = images.size
}