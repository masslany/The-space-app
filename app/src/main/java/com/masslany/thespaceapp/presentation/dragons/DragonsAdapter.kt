package com.masslany.thespaceapp.presentation.dragons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.ItemRecyclerviewBinding
import com.masslany.thespaceapp.domain.model.DragonModel

class DragonsAdapter constructor(
    private val glide: RequestManager,
    private val onItemClick: (DragonModel) -> Unit
) : RecyclerView.Adapter<DragonsAdapter.LaunchViewHolder>() {

    inner class LaunchViewHolder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    var dragons = listOf<DragonModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val binding = ItemRecyclerviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        with(holder) {
            with(dragons[position]) {
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

    override fun getItemCount() = dragons.size
}