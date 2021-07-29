package com.globallogic.thespaceapp.presentation.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.ItemRecyclerviewBinding
import com.globallogic.thespaceapp.databinding.ItemRecyclerviewHeaderBinding
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.utils.toDateSting

class LaunchesAdapter constructor(
    private val glide: RequestManager,
    private val onItemClick: (LaunchEntity) -> Unit
) : RecyclerView.Adapter<LaunchesAdapter.BaseLaunchViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return launches[position].type
    }

    abstract class BaseLaunchViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LaunchViewHolder(binding: ItemRecyclerviewBinding) :
        BaseLaunchViewHolder(binding)

    inner class LaunchHeaderViewHolder(binding: ItemRecyclerviewHeaderBinding) :
        BaseLaunchViewHolder(binding)


    var launches: List<LaunchAdapterItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
            else -> {
                throw RuntimeException("Wrong view holder type!")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseLaunchViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.id.item_recyclerview -> {
                val binding: ItemRecyclerviewBinding = holder.binding as ItemRecyclerviewBinding

                with(launches[position]) {
                    if (this.launchEntity == null) {
                        return
                    }
                    binding.tvItemHeadline.text = this.launchEntity.name
                    binding.tvItemCaption.text = this.launchEntity.date.toDateSting()
                    glide
                        .load(this.launchEntity.image)
                        .placeholder(R.drawable.ic_launch_placeholder)
                        .into(binding.ivItem)

                    binding.itemRecyclerview.setOnClickListener { onItemClick(this.launchEntity) }
                }
            }
            R.id.item_recyclerview_header -> {
                val binding: ItemRecyclerviewHeaderBinding =
                    holder.binding as ItemRecyclerviewHeaderBinding

                with(launches[position]) {
                    binding.tvItemHeadline.text = this.header
                }
            }
        }
    }

    override fun getItemCount() = launches.size
}