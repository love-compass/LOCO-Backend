package com.loco.course.ui

import com.loco.course.service.CourseService
import com.loco.course.ui.dto.CourseResponseDto
import com.loco.course.ui.dto.CoursesResponseDto
import com.loco.course.ui.dto.FullCourseRequestDto
import com.loco.course.ui.dto.OneCourseRequestDto

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class CourseController(val courseService: CourseService) {
    @Operation(summary = "데이트 코스 조회", description = "데이터 코스 생성 메서드입니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "데이터 코스 생성 메서드입니다.", content = [
            (Content(mediaType = "application/json", array = (
                    ArraySchema(schema = Schema(implementation = CourseResponseDto::class)))))]),
        ApiResponse(responseCode = "400", description = "...", content = [Content()]),
        ApiResponse(responseCode = "404", description = "...", content = [Content()])]
    )
    @PostMapping("/course")
    fun createCourse(@RequestBody fullCourseRequestDto: FullCourseRequestDto): ResponseEntity<CoursesResponseDto>{
        val result = courseService.createFullCourse(fullCourseRequestDto)
        return ResponseEntity.status(HttpStatus.OK).body(result)
    }

    @Operation(summary = "데이트 코스 변경", description = "데이터 코스 변경 메서드입니다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "...", content = [
            (Content(mediaType = "application/json", schema = Schema(implementation = CourseResponseDto::class)))]),
        ApiResponse(responseCode = "400", description = "...", content = [Content()]),
        ApiResponse(responseCode = "404", description = "...", content = [Content()])]
    )
    @PatchMapping("/course")
    fun updateOneCourse(@RequestBody oneCourseRequestDto: OneCourseRequestDto): ResponseEntity<CourseResponseDto>{
        val result = courseService.createOneCourse(oneCourseRequestDto)
        return ResponseEntity.status(HttpStatus.OK).body(result)
    }
}