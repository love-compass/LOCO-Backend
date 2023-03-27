package com.loco.place.ui.dto

data class NaverResponseDto(
    val total: Long,
    val display: Long,
    val items: List<ImageResponseDto>,
) {

}