package com.shijas.shoppingapp.data.mapper

import com.shijas.shoppingapp.data.source.local.entity.ProductEntity
import com.shijas.shoppingapp.data.source.remote.dto.Value
import com.shijas.shoppingapp.domian.model.Product

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        image = image,
        actualPrice = actualPrice,
        offerPrice = offerPrice,
        offer = offer,
        isExpress = isExpress
    )
}

fun Value.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id ?: 0,
        name = name ?: "",
        image = image ?: "",
        actualPrice = actualPrice ?: "",
        offerPrice = offerPrice ?: "",
        offer = offer ?: 0,
        isExpress = isExpress ?: true,
    )
}