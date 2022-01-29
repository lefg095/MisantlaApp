package com.lefg095.misantlaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SliderData(
    var imgUrl: String,
    var nameBusiness: String
): Parcelable
