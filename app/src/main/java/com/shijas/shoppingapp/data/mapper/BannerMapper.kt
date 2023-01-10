package com.shijas.shoppingapp.data.mapper

import com.shijas.shoppingapp.data.source.local.entity.BannerEntity
import com.shijas.shoppingapp.data.source.local.entity.CategoryEntity
import com.shijas.shoppingapp.data.source.remote.dto.Value
import com.shijas.shoppingapp.domian.model.Banner

fun BannerEntity.toBanner(): Banner {
    return Banner(
        id = id, image = image
    )
}
fun Value.toBannerEntity(): BannerEntity {
    return BannerEntity(
        id = id ?: 0,
        image = bannerUrl ?: ""
    )
}