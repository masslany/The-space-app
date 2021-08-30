package com.masslany.thespaceapp.presentation.dragons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.ItemRecyclerviewBinding
import com.masslany.thespaceapp.domain.model.DragonModel

class DragonsAdapter constructor(
    private val glide: RequestManager,
    private val onItemClick: (DragonModel) -> Unit
) : ListAdapter<DragonModel, DragonsAdapter.LaunchViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<DragonModel>() {

        override fun areItemsTheSame(oldItem: DragonModel, newItem: DragonModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DragonModel, newItem: DragonModel) =
            oldItem == newItem
    }

    inner class LaunchViewHolder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val binding = ItemRecyclerviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        with(holder) {
            with(currentList    [position]) {
                binding.tvItemHeadline.text = this.name
                binding.tvItemCaption.text =
                    itemView.context.getString(R.string.first_launched, this.firstFlight)
                glide
                    .load(this.flickrImages.first())
                    .placeholder(R.drawable.ic_launch_placeholder)
                    .into(binding.ivItem)

                binding.itemRecyclerview.setOnClickListener { onItemClick(this) }
            }
        }
    }

    override fun getItemCount() = currentList.size
}