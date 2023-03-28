package com.loco.place.ui.dto

data class PlaceResponseDto(
    val address_name: String,
    val category_group_code: String,
    val category_group_name: String,
    val category_name: String,
   val distance: String,
    val id:String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: String,
    val y: String,
    var image_url: String?
) {
}