package com.lefg095.misantlaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessData(
    var descripcion: String,
    var nombre: String,
    var ubicacion: String,
    var url_img: String
): Parcelable