package com.shijas.shoppingapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import android.util.TimeUtils
import com.google.android.material.timepicker.TimeFormat
import com.shijas.shoppingapp.app.util.Resource
import com.shijas.shoppingapp.app.util.networkBoundResource
import com.shijas.shoppingapp.data.mapper.*
import com.shijas.shoppingapp.data.source.local.dao.BannerDao
import com.shijas.shoppingapp.data.source.local.dao.CategoryDao
import com.shijas.shoppingapp.data.source.local.dao.ProductDao
import com.shijas.shoppingapp.data.source.remote.dto.HomeData
import com.shijas.shoppingapp.data.source.remote.service.HomeService
import com.shijas.shoppingapp.domian.model.Banner
import com.shijas.shoppingapp.domian.model.Category
import com.shijas.shoppingapp.domian.model.Product
import com.shijas.shoppingapp.domian.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val bannerDao: BannerDao,
    private val productDao: ProductDao,
    private val homeService: HomeService,
    private val sharedPreferences: SharedPreferences
) : HomeRepository {

    override fun getHomeScreenItems(): Flow<Resource<Triple<Flow<List<Category>>, Flow<List<Banner>>, Flow<List<Product>>>>> {
        return networkBoundResource(
            query = { getLocalHomeScreenData() },
            fetch = { homeService.getHomeScreenItems() },
            saveFetchResult = { homeDataDto ->
                saveProducts(homeDataDto.homeData)
                sharedPreferences.edit().putLong(CACHE_KEY, System.currentTimeMillis()).apply()
            },
            shouldFetch = { isStaleCache() }
        )
    }

    private fun getLocalHomeScreenData(): Flow<Triple<Flow<List<Category>>, Flow<List<Banner>>, Flow<List<Product>>>> =
        flow {
            val categories = getCategories()
            val banners = getBanners()
            val products = getProducts()
            val value = Triple(categories, banners, products)
            emit(value)
        }

    private fun getCategories(): Flow<List<Category>> {
        return categoryDao.getCategories()
            .map { categoryEntities -> categoryEntities.map { it.toCategory() } }
    }

    private fun getBanners(): Flow<List<Banner>> {
        return bannerDao.getBanners().map { bannerEntities -> bannerEntities.map { it.toBanner() } }
    }

    private fun getProducts(): Flow<List<Product>> {
        return productDao.getProducts()
            .map { productEntities -> productEntities.map { it.toProduct() } }
    }

    private suspend fun saveProducts(homeData: List<HomeData>?) {
        val categoryEntities =
            homeData?.firstOrNull { it.type == CATEGORY_TYPE }?.values?.map { it.toCategoryEntity() }
                ?: emptyList()
        val bannerEntities =
            homeData?.firstOrNull { it.type == BANNER_TYPE }?.values?.map { it.toBannerEntity() }
                ?: emptyList()
        val productEntities =
            homeData?.firstOrNull { it.type == PRODUCT_TYPE }?.values?.map { it.toProductEntity() }
                ?: emptyList()
        categoryDao.insertCategories(categoryEntities)
        bannerDao.insertBanners(bannerEntities)
        productDao.insertProducts(productEntities)
    }

    private fun isStaleCache(): Boolean {
        val lastSyncTimeInMillis = sharedPreferences.getLong(CACHE_KEY, 0)
        val timeAfterLastSync = System.currentTimeMillis() - lastSyncTimeInMillis
        val staleTime =  TimeUnit.MINUTES.toMillis(1)
        return (lastSyncTimeInMillis == 0L || timeAfterLastSync > staleTime)
    }

    companion object {
        const val CATEGORY_TYPE = "category"
        const val BANNER_TYPE = "banners"
        const val PRODUCT_TYPE = "products"
        const val CACHE_KEY = "cache_key"
    }
}