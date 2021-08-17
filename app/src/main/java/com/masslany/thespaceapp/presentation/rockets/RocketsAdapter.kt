package com.masslany.thespaceapp.presentation.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.databinding.ItemRecyclerviewBinding
import com.masslany.thespaceapp.domain.model.RocketEntity
import com.masslany.thespaceapp.presentation.rockets.RocketsAdapter.RocketsViewHolder

class RocketsAdapter(
    private val glide: RequestManager,
    private val onItemClick: (RocketEntity) -> Unit
) : RecyclerView.Adapter<RocketsViewHolder>() {

    inner class RocketsViewHolder(val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    var rockets = listOf<RocketEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder {
        val binding = ItemRecyclerviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        with(holder) {
            with(rockets[position]) {
                binding.tvItemHeadline.text = this.name
                binding.tvItemCaption.text =
                    itemView.context.getString(R.string.first_launched, this.firstFlight)
                glide
                    .load(this.flickrImages.first())
                    .placeholder(R.drawable.rocket_placeholder)
                    .into(binding.ivItem)

                binding.itemRecyclerview.setOnClickListener { onItemClick(this) }
            }
        }
    }

    override fun getItemCount(): Int = rockets.size
}