package com.shijas.shoppingapp.data.mapper

import com.shijas.shoppingapp.data.source.local.entity.CategoryEntity
import com.shijas.shoppingapp.data.source.remote.dto.Value
import com.shijas.shoppingapp.domian.model.Category

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id, name = name, image = image
    )
}

fun Value.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id ?: 0,
        name = name ?: "",
        image = imageUrl ?: ""
    )
}