package com.loco.course.ui.dto

import io.swagger.annotations.ApiModelProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class FullCourseRequestDto (
    val user_request : String,
    @ApiModelProperty(example = "2023-01-01T18:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val start_time: LocalDateTime,

    @ApiModelProperty(example = "2023-01-01T18:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val end_time: LocalDateTime,

    val budget: Long,
    @ApiModelProperty(example = "강남/역삼/선릉")
    val place: String
){
}