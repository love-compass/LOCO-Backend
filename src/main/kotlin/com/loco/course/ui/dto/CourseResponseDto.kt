package com.loco.course.ui.dto

import com.loco.place.ui.dto.PlaceResponseDto
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.Date

data class CourseResponseDto (
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val start_time: String,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val end_time: String,
    val activity: PlaceResponseDto,
    val budget: Long,
    val description: String
){
}