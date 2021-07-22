package com.globallogic.thespaceapp.presentation.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.databinding.ItemRecyclerviewBinding
import com.globallogic.thespaceapp.domain.model.LaunchesEntity

class LaunchesAdapter constructor(
    private val glide: RequestManager,
    private val onItemClick: (LaunchesEntity) -> Unit
) : RecyclerView.Adapter<LaunchesAdapter.LaunchViewHolder>() {

    inner class LaunchViewHolder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    var launches: List<LaunchesEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val binding = ItemRecyclerviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        with(holder) {
            with(launches[position]) {
                binding.tvItemHeadline.text = this.name
                binding.tvItemCaption.text = this.date
                glide
                    .load(this.image)
                    .placeholder(R.drawable.ic_launch_placeholder)
                    .into(binding.ivItem)

                binding.itemRecyclerview.setOnClickListener { onItemClick(this) }
            }
        }
    }

    override fun getItemCount() = launches.size
}