package com.shijas.shoppingapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shijas.shoppingapp.data.source.local.dao.BannerDao
import com.shijas.shoppingapp.data.source.local.dao.CategoryDao
import com.shijas.shoppingapp.data.source.local.dao.ProductDao
import com.shijas.shoppingapp.data.source.local.entity.BannerEntity
import com.shijas.shoppingapp.data.source.local.entity.CategoryEntity
import com.shijas.shoppingapp.data.source.local.entity.ProductEntity

@Database(entities = [CategoryEntity::class,BannerEntity::class,ProductEntity::class], version = 1)
abstract class ShoppingAppDatabase : RoomDatabase(){
    abstract val categoryDao : CategoryDao
    abstract val bannerDao : BannerDao
    abstract val productDao : ProductDao
}