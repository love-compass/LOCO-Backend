package com.loco.course.ui.dto

data class CoursesResponseDto(
    val place: String,
    val courses: List<CourseResponseDto>,
    val total_budget: Long
) {
}