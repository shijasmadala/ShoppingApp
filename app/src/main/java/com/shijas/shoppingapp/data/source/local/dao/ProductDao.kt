package com.shijas.shoppingapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shijas.shoppingapp.data.source.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products : List<ProductEntity>)

    @Query("SELECT * FROM product_table")
    fun getProducts() : Flow<List<ProductEntity>>
}