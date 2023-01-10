package com.shijas.shoppingapp.presentation.main.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shijas.shoppingapp.R
import com.shijas.shoppingapp.app.util.getRandomLightColors
import com.shijas.shoppingapp.databinding.ItemCategoryBinding
import com.shijas.shoppingapp.databinding.ItemProductBinding
import com.shijas.shoppingapp.domian.model.Category
import com.shijas.shoppingapp.domian.model.Product

class ProductAdapter : ListAdapter<Product,RecyclerView.ViewHolder>(ProductDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding : ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.apply {
                imageProduct.load(product.image)
                imageProduct.setBackgroundColor(itemView.context.getRandomLightColors())
                actualPrice.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
                productName.text = product.name
                actualPrice.text = product.actualPrice
                offerPrice.text = product.offerPrice
                expressDelivery.isVisible = product.isExpress
                if (product.offer != 0){
                    offerLayout.isVisible = true
                    offerPercentage.text = itemView.context.getString(R.string.offer_percentage,"${product.offer}%")
                }
                actualPrice.isVisible = product.actualPrice != product.offerPrice

            }
        }
    }
}
object ProductDiff : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}