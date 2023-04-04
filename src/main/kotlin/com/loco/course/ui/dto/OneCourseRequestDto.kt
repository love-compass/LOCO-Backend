package com.loco.course.ui.dto

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class OneCourseRequestDto (
    val prior_activity_name: String,

    @ApiModelProperty(example = "강남/역삼/선릉")
    val place:String,

    @ApiModelProperty(example = "2023-01-01T18:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val start_time: LocalDateTime,

    @ApiModelProperty(example = "2023-01-01T18:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val end_time: LocalDateTime,

    val user_request: String,

    val prior_places: List<String>
){
}