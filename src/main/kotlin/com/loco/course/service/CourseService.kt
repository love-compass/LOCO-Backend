package com.loco.course.service

import com.loco.course.ui.dto.CoursesResponseDto
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
    fun createFullCourse(fullCourseRequestDto: FullCourseRequestDto): CoursesResponseDto {
        checkPlace(fullCourseRequestDto.place)
        val placeResponseDto = PlaceResponseDto(
            "서울 종로구 세종로 1-58",  "문화유적",
            "여행 > 관광,명소 > 문화유적 > 문",  "02-2332-2222",
            "광화문", "http://place.map.kakao.com/8234642", "서울 종로구 사직로 161",
            "126.976861018866", "37.5759689663327", "http://imgnews.naver.net/image/5291/2022/12/04/0001681710_001_20221204105204154.jpg"
        )
        val t_start: LocalDateTime = LocalDateTime.now()

        val t_end: LocalDateTime = LocalDateTime.now().plusHours(2)

        val courseResponseDto = CourseResponseDto(
            t_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            t_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            placeResponseDto, "멋진 광화문을 구경해요.", 10000, "광화문은 조선의 유적지이다.")
        return CoursesResponseDto(fullCourseRequestDto.place, listOf(courseResponseDto), 10000)
    }

    fun checkPlace(place: String){
        var array = enumValues<PlaceEnum>().filter { it.value == place }
        if(array.isEmpty()){
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.PLACE_NOT_EXISTS.errorCode, ErrorType.PLACE_NOT_EXISTS.message))
        }
    }

    fun createOneCourse(oneCourseRequestDto: OneCourseRequestDto): CourseResponseDto {
        val activity = PlaceResponseDto(
            "서울 종로구 세종로 1-58",  "문화유적",
            "여행 > 관광,명소 > 문화유적 > 문",  "02-2332-2222",
            "광화문", "http://place.map.kakao.com/8234642", "서울 종로구 사직로 161",
            "126.976861018866", "37.5759689663327", "http://imgnews.naver.net/image/5291/2022/12/04/0001681710_001_20221204105204154.jpg"
        )
        val t_start: LocalDateTime = LocalDateTime.of(2021, 9, 27, 17, 37, 39)
        val t_end: LocalDateTime = LocalDateTime.of(2021, 9, 27, 19, 32, 39)

        return CourseResponseDto(
            t_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            t_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            activity, "제주도에서 해변을 보며 즐겨보세요.", 10000, "광화문은 조선의 유적지이다.")
    }
}