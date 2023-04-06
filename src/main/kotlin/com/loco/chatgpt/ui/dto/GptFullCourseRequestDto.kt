package com.loco.chatgpt.ui.dto

data class GptFullCourseRequestDto(
    val user_request: String,
    val start_time: String,
    val end_time: String,
    val budget: Long,
    val place: String,
    val prior_places: List<String>
    ) {
}