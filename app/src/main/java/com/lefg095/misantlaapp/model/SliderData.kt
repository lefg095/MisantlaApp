package com.lefg095.misantlaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SliderData(
    var dateEnd: String,
    var dateInit: String,
    var imgUrl: String,
    var businessUrl: String,
    var nameBusiness: String,
    var imgUrlDefault: String
): Parcelable
