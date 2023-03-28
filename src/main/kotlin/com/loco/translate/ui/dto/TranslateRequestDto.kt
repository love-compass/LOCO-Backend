package com.loco.translate.ui.dto

data class TranslateRequestDto(
    val source: String,
    val target: String,
    val text: String,
) {
}