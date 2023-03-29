package com.loco.course.ui.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class FullCourseRequestDto (
    val question : String,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val start_time: LocalDateTime,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val end_time: LocalDateTime,
    val budget: Long,
    val place: String
){
}