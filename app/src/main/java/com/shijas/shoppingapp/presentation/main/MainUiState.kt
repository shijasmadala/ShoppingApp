package com.shijas.shoppingapp.presentation.main

import com.shijas.shoppingapp.domian.model.Banner
import com.shijas.shoppingapp.domian.model.Category
import com.shijas.shoppingapp.domian.model.Product

data class MainUiState(
    val loading : Boolean = false,
    val categories : List<Category> = emptyList(),
    val banners : List<Banner> = emptyList(),
    val products : List<Product> = emptyList(),
    val error : String? = null
)
