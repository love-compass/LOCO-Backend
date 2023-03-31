package com.loco.chatgpt.ui.dto

import java.time.LocalDateTime

data class GptActivityDto (
    var activity_name: String,
    val start_time: String,
    val end_time: String,
    var description: String,
    val budget: Long
){
}