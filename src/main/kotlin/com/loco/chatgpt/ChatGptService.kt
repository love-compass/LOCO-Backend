package com.loco.chatgpt

import com.loco.chatgpt.ui.dto.GptActivityDto
import com.loco.chatgpt.ui.dto.GptCourseChangeRequestDto
import com.loco.chatgpt.ui.dto.GptFullCourseRequestDto
import com.loco.chatgpt.ui.dto.GptResponseDto
import com.loco.config.WebClientConfig
import com.loco.course.ui.dto.FullCourseRequestDto
import com.loco.course.ui.dto.OneCourseRequestDto
import com.loco.exception.ErrorType
import com.loco.exception.ExceptionResponse
import com.loco.exception.LocoException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import java.time.format.DateTimeFormatter

@Service
class ChatGptService() {
    fun getFullCourse(fullCourseRequestDto: FullCourseRequestDto): GptResponseDto {
        val url = "http://3.39.3.82:5000/course/full"
        val client3 = WebClientConfig()

        val req = GptFullCourseRequestDto(
            fullCourseRequestDto.user_request,
            fullCourseRequestDto.start_time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            fullCourseRequestDto.start_time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            fullCourseRequestDto.budget,
            fullCourseRequestDto.place)


        val result = client3.webClient()
            .post()
            .uri(url)
            .body(BodyInserters.fromValue(req))
            .retrieve()
            .bodyToMono(GptResponseDto::class.java)
            .block();

        if (result == null || result.result.isEmpty()){
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.GPT_SERVER_ERROR.errorCode, ErrorType.GPT_SERVER_ERROR.message))
        }

        return result
    }

    fun getOneCourse(oneCourseRequestDto: OneCourseRequestDto): GptActivityDto {
        val url = "http://3.39.3.82:5000/course"
        val client3 = WebClientConfig()

        val req = GptCourseChangeRequestDto(
            oneCourseRequestDto.prior_activity_name,
            oneCourseRequestDto.place,
            oneCourseRequestDto.start_time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            oneCourseRequestDto.end_time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            oneCourseRequestDto.user_request,
            oneCourseRequestDto.prior_places)

        var result = client3.webClient()
            .post()
            .uri(url)
            .body(BodyInserters.fromValue(req))
            .retrieve()
            .bodyToMono(GptActivityDto::class.java)
            .block();

        if (result == null){
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.GPT_SERVER_ERROR.errorCode, ErrorType.GPT_SERVER_ERROR.message))
        }

        return result
    }

}