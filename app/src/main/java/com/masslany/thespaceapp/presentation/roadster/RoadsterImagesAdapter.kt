package com.masslany.thespaceapp.presentation.roadster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.ItemRoadsterImageBinding

class RoadsterImagesAdapter constructor(
    private val glide: RequestManager
) : ListAdapter<String, RoadsterImagesAdapter.RoadsterViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    inner class RoadsterViewHolder(val binding: ItemRoadsterImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoadsterViewHolder {
        val binding = ItemRoadsterImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RoadsterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoadsterViewHolder, position: Int) {
        with(holder) {
            with(currentList[position]) {
                glide
                    .load(this)
                    .placeholder(R.drawable.roadster_crop)
                    .into(binding.ivRoadster)
            }
        }
    }

    override fun getItemCount() = currentList.size
}