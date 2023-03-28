package com.loco.translate.ui.dto

data class TranslateResultResponseDto(
    val srcLangType: String,
    val tarLangType:String,
    val translatedText: String
) {
}