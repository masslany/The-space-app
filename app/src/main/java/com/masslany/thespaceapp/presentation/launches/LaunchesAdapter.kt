package com.masslany.thespaceapp.presentation.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.ItemRecyclerviewBinding
import com.masslany.thespaceapp.databinding.ItemRecyclerviewEmptyBinding
import com.masslany.thespaceapp.databinding.ItemRecyclerviewHeaderBinding
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.utils.toDateSting

class LaunchesAdapter constructor(
    private val glide: RequestManager,
    private val onItemClick: (LaunchModel) -> Unit
) : ListAdapter<LaunchAdapterItem, LaunchesAdapter.BaseLaunchViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<LaunchAdapterItem>() {

        override fun areItemsTheSame(oldItem: LaunchAdapterItem, newItem: LaunchAdapterItem) =
            oldItem.launchModel?.id == newItem.launchModel?.id

        override fun areContentsTheSame(oldItem: LaunchAdapterItem, newItem: LaunchAdapterItem) =
            oldItem == newItem
    }

    abstract class BaseLaunchViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LaunchViewHolder(binding: ItemRecyclerviewBinding) :
        BaseLaunchViewHolder(binding)

    inner class LaunchHeaderViewHolder(binding: ItemRecyclerviewHeaderBinding) :
        BaseLaunchViewHolder(binding)

    inner class LaunchEmptyViewHolder(binding: ItemRecyclerviewEmptyBinding) :
        BaseLaunchViewHolder(binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseLaunchViewHolder {
        when (viewType) {
            R.id.item_recyclerview -> {

                val binding = ItemRecyclerviewBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return LaunchViewHolder(binding)
            }
            R.id.item_recyclerview_header -> {

                val binding = ItemRecyclerviewHeaderBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return LaunchHeaderViewHolder(binding)
            }
            R.id.item_recyclerview_empty -> {

                val binding = ItemRecyclerviewEmptyBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return LaunchEmptyViewHolder(binding)
            }
            else -> {
                throw RuntimeException("Wrong view holder type!")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseLaunchViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.id.item_recyclerview -> {
                val binding: ItemRecyclerviewBinding = holder.binding as ItemRecyclerviewBinding

                with(currentList[position]) {
                    if (this.launchModel == null) {
                        return
                    }
                    binding.tvItemHeadline.text = this.launchModel.name
                    binding.tvItemCaption.text = this.launchModel.date.toDateSting()
                    glide
                        .load(this.launchModel.image)
                        .placeholder(R.drawable.ic_launch_placeholder)
                        .into(binding.ivItem)

                    binding.itemRecyclerview.setOnClickListener { onItemClick(this.launchModel) }
                }
            }
            R.id.item_recyclerview_header -> {
                val binding: ItemRecyclerviewHeaderBinding =
                    holder.binding as ItemRecyclerviewHeaderBinding

                with(currentList[position]) {
                    binding.tvItemHeadline.text = this.header
                }
            }
            R.id.item_recyclerview_empty -> {
                // No action
            }
        }
    }

    override fun getItemCount() = currentList.size

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
    }
}