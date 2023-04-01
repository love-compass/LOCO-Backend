package com.loco.place.ui.dto

data class PlaceResponseDto(
    val address_name: String,
    var category_group_name: String,
    var category_name: String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: String,
    val y: String,
    var image_url: String?
) {
}