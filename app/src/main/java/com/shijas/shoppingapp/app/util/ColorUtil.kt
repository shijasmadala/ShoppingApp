package com.shijas.shoppingapp.app.util

import android.content.Context
import com.shijas.shoppingapp.R

fun Context.getRandomLightColors(): Int {
    return resources.getIntArray(R.array.categoryColors).random()
}