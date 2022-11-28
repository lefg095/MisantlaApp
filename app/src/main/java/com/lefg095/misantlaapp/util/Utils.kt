package com.lefg095.misantlaapp.util

import java.text.Normalizer

fun String.cleanText(): String {
    val strNormalize: String = Normalizer.normalize(this, Normalizer.Form.NFD)
    var strWithOutAccents  = strNormalize.replace("[^\\p{ASCII}]".toRegex(), "")
    strWithOutAccents = strWithOutAccents.replace(" ", "_").lowercase()

    return strWithOutAccents
}

fun String.validateText(): Boolean {
    return this.isNotBlank() || this.isNotEmpty() || this != ""
}

fun Boolean.zeroOrOneChange(): String {
    return if (this){
            "1"
        }else{
            "0"
        }
}