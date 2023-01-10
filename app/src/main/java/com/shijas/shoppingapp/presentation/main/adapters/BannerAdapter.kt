package com.shijas.shoppingapp.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shijas.shoppingapp.app.util.getRandomLightColors
import com.shijas.shoppingapp.databinding.ItemBannerBinding
import com.shijas.shoppingapp.databinding.ItemCategoryBinding
import com.shijas.shoppingapp.domian.model.Banner
import com.shijas.shoppingapp.domian.model.Category

class BannerAdapter : ListAdapter<Banner,RecyclerView.ViewHolder>(BannerDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BannerViewHolder).bind(getItem(position))
    }

    inner class BannerViewHolder(private val binding : ItemBannerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(banner: Banner){
            binding.bannerImage.load(banner.image)
            binding.imageBackground.setCardBackgroundColor(itemView.context.getRandomLightColors())
        }
    }
}
object BannerDiff : DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

}