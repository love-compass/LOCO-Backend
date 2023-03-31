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
            courseResult.add(CourseResponseDto(course.start_time, course.end_time,
                fillCategoryGroupName(placeService.findPlace(course.activity_name)),
                course.budget, course.description
            ))
            total_budget += course.budget
        }
        return CoursesResponseDto(fullCourseRequestDto.place, courseResult, total_budget)
    }


    fun createOneCourse(oneCourseRequestDto: OneCourseRequestDto): CourseResponseDto {
        checkPlace(oneCourseRequestDto.place)

        val changedCourse = chatGptService.getOneCourse(oneCourseRequestDto)
        val activity = placeService.findPlace(changedCourse.activity_name)

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