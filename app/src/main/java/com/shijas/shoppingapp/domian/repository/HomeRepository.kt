package com.shijas.shoppingapp.domian.repository

import com.shijas.shoppingapp.app.util.Resource
import com.shijas.shoppingapp.domian.model.Banner
import com.shijas.shoppingapp.domian.model.Category
import com.shijas.shoppingapp.domian.model.Product
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getHomeScreenItems() : Flow<Resource<Triple<Flow<List<Category>>,Flow<List<Banner>>,Flow<List<Product>>>>>
}