package com.loco.course.service

import com.loco.chatgpt.ChatGptService
import com.loco.course.ui.dto.CoursesResponseDto
import com.loco.course.ui.dto.FullCourseRequestDto
import com.loco.course.ui.dto.CourseResponseDto
import com.loco.course.ui.dto.OneCourseRequestDto
import com.loco.exception.ErrorType
import com.loco.exception.ExceptionResponse
import com.loco.exception.LocoException
import com.loco.global.PlaceEnum
import com.loco.place.PlaceService
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
class CourseService(val chatGptService: ChatGptService, val placeService: PlaceService) {
    fun createFullCourse(fullCourseRequestDto: FullCourseRequestDto): CoursesResponseDto {
        checkPlace(fullCourseRequestDto.place)
        val fullCourse = chatGptService.getFullCourse(fullCourseRequestDto)

        var total_budget: Long = 0
        val courseResult: MutableList<CourseResponseDto> = mutableListOf()

        for (course in fullCourse.result){
            var place = placeService.findPlace(course.activity_name, fullCourseRequestDto.place)
            var prior_list: MutableList<String> = mutableListOf()

            var cnt = 0
            while(place == null){
                cnt += 1
                prior_list.add(course.activity_name)

                val placeCourseRequestDto = OneCourseRequestDto("", fullCourseRequestDto.place,
                    toLocalDateTime(course.start_time), toLocalDateTime(course.end_time), "", prior_list)

                val changedCourse = chatGptService.getOneCourse(placeCourseRequestDto)
                place = placeService.findPlace(changedCourse.activity_name, placeCourseRequestDto.place)

                if(cnt > 5){
                    throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.KAKAO_PLACE_NOT_FOUND.errorCode, ErrorType.KAKAO_PLACE_NOT_FOUND.message))
                }
            }
            courseResult.add(CourseResponseDto(course.start_time, course.end_time,
                fillCategoryGroupName(place),
                course.budget, course.description
            ))
            total_budget += course.budget
        }
        if(courseResult.size == 0){
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.KAKAO_PLACE_NOT_FOUND.errorCode, ErrorType.KAKAO_PLACE_NOT_FOUND.message))
        }
        return CoursesResponseDto(fullCourseRequestDto.place, courseResult, total_budget)
    }


    fun createOneCourse(oneCourseRequestDto: OneCourseRequestDto): CourseResponseDto {
        checkPlace(oneCourseRequestDto.place)

        var changedCourse = chatGptService.getOneCourse(oneCourseRequestDto)
        var activity = placeService.findPlace(changedCourse.activity_name, oneCourseRequestDto.place)
        var cnt = 0
        while(activity == null){
            cnt += 1
            var prior_list = oneCourseRequestDto.prior_places
            prior_list.add(changedCourse.activity_name)

            val placeCourseRequestDto = OneCourseRequestDto(oneCourseRequestDto.prior_activity_name, oneCourseRequestDto.place,
                oneCourseRequestDto.start_time, oneCourseRequestDto.end_time, oneCourseRequestDto.user_request, prior_list)

            changedCourse = chatGptService.getOneCourse(placeCourseRequestDto)
            activity = placeService.findPlace(changedCourse.activity_name, placeCourseRequestDto.place)
            if(cnt > 5){
                throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.KAKAO_PLACE_NOT_FOUND.errorCode, ErrorType.KAKAO_PLACE_NOT_FOUND.message))
            }
        }
        return CourseResponseDto(
            changedCourse.start_time, changedCourse.end_time,
            activity, changedCourse.budget, changedCourse.description)
    }

    fun checkPlace(place: String){
        var array = enumValues<PlaceEnum>().filter { it.value == place }
        if(array.isEmpty()){
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.PLACE_NOT_EXISTS.errorCode, ErrorType.PLACE_NOT_EXISTS.message))
        }
    }

    fun toLocalDateTime(dateString: String):LocalDateTime{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return LocalDateTime.parse(dateString, formatter)
    }

    /**
     * category_group_name 채우는 함수
     */
    fun fillCategoryGroupName(activity: PlaceResponseDto): PlaceResponseDto{
        if(activity.category_group_name.length == 0){
            val split = activity.category_name.split("")
            if(split.size == 4){
                activity.category_group_name = split[2]
            }
            else{
                activity.category_group_name = split[1]
            }
        }
        return activity
    }
}