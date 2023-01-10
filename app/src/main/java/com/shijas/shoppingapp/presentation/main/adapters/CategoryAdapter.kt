package com.shijas.shoppingapp.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shijas.shoppingapp.app.util.getRandomLightColors
import com.shijas.shoppingapp.databinding.ItemCategoryBinding
import com.shijas.shoppingapp.domian.model.Category

class CategoryAdapter : ListAdapter<Category,RecyclerView.ViewHolder>(CategoryDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding : ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category){
            binding.imageBackground.setCardBackgroundColor(itemView.context.getRandomLightColors())
            binding.categoryImage.load(category.image)
            binding.categoryName.text = category.name
        }
    }
}
object CategoryDiff : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}