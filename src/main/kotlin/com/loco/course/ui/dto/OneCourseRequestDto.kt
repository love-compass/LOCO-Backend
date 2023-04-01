package com.loco.course.ui.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class OneCourseRequestDto (
    val prior_activity_name: String,
    val place:String,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val start_time: LocalDateTime,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val end_time: LocalDateTime,
    val question: String
){
}