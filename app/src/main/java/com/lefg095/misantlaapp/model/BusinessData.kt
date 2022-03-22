package com.lefg095.misantlaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessData(
    var nombre: String,
    var descripcion: String,
    var ubicacion: String,
    var url_img: String,

    var desLong: String? = "",
    val horario: String? = "",
    val telefono: String? = "",
    val whatsapp: String? = "",
    val facebook: String? = "",
    val instagram: String? = "",
    val servLocal: String? = "",
    val servEntrega: String? = "",
    val catalogo_menu: String? = ""
): Parcelable