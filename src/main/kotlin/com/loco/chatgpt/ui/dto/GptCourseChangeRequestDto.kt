package com.loco.chatgpt.ui.dto

data class GptCourseChangeRequestDto(
    val prior_activity_name: String,
    val place:String,
    val start_time: String,
    val end_time: String,
    val user_request: String,
    val prior_places: List<String>,
    val budget: Long
) {
}