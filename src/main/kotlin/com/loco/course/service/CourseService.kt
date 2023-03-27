package com.loco.course.service

import com.loco.course.ui.dto.FullCourseRequestDto
import com.loco.course.ui.dto.CourseResponseDto
import com.loco.course.ui.dto.OneCourseRequestDto
import com.loco.exception.ErrorType
import com.loco.exception.ExceptionResponse
import com.loco.exception.LocoException
import com.loco.global.PlaceEnum
import com.loco.place.ui.dto.PlaceResponseDto
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@RequiredArgsConstructor
@Slf4j
class CourseService {
    fun createFullCourse(fullCourseRequestDto: FullCourseRequestDto): List<CourseResponseDto> {
        checkPlace(fullCourseRequestDto.place)
        val placeResponseDto = PlaceResponseDto(
            "제주특별자치도 제주시 용담삼동 2572-4", "CE7", "카페",
            "음식점 > 카페 > 커피전문점 > 스타벅스", "", "https://place.map.kakao.com/1897430820",
            "1522-3232", "스타벅스 제주용담DT점", "http://place.map.kakao.com/26102947",
            "제주특별자치도 제주시 서해안로 380", "126.484480056159", "33.5124867330564", ""
        )
        val t_start: LocalDateTime = LocalDateTime.now()

        val t_end: LocalDateTime = LocalDateTime.now().plusHours(2)

        val courseResponseDto = CourseResponseDto(
            t_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            t_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            placeResponseDto, "제주도에서 해변을 보며 즐겨보세요.")
        return listOf(courseResponseDto)
    }

    fun checkPlace(place: String){
        var array = enumValues<PlaceEnum>().filter { it.value == place }
        if(array.isEmpty()){
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.PLACE_NOT_EXISTS.errorCode, ErrorType.PLACE_NOT_EXISTS.message))
        }
    }

    fun createOneCourse(oneCourseRequestDto: OneCourseRequestDto): CourseResponseDto {
        val activity = PlaceResponseDto(
            "제주특별자치도 제주시 용담삼동 2572-4", "CE7", "카페",
            "음식점 > 카페 > 커피전문점 > 스타벅스", "", "https://place.map.kakao.com/1897430820",
            "1522-3232", "스타벅스 제주용담DT점", "http://place.map.kakao.com/26102947",
            "제주특별자치도 제주시 서해안로 380", "126.484480056159", "33.5124867330564", ""
        )
        val t_start: LocalDateTime = LocalDateTime.of(2021, 9, 27, 17, 37, 39)
        val t_end: LocalDateTime = LocalDateTime.of(2021, 9, 27, 19, 32, 39)

        return CourseResponseDto(
            t_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            t_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            activity, "제주도에서 해변을 보며 즐겨보세요.")
    }
}