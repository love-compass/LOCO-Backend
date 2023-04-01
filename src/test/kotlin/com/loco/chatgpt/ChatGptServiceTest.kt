package com.loco.chatgpt

import com.loco.chatgpt.ui.dto.GptActivityDto
import com.loco.chatgpt.ui.dto.GptResponseDto
import com.loco.course.ui.dto.FullCourseRequestDto
import com.loco.course.ui.dto.OneCourseRequestDto
import com.loco.exception.ErrorType
import com.loco.exception.LocoException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ChatGptServiceTest {

    @InjectMocks
    private lateinit var chatGptService: ChatGptService

    @Mock
    private lateinit var webClient: WebClient

    private lateinit var fullCourseRequestDto: FullCourseRequestDto
    private lateinit var oneCourseRequestDto: OneCourseRequestDto
    private lateinit var gptActivityDto: GptActivityDto
    @BeforeEach
    fun setUp() {
        gptActivityDto = GptActivityDto("Sample Activity", "Sample Start Time",
            "Sample End Time", "Sample Description", 10000)

        fullCourseRequestDto = FullCourseRequestDto(
            "Sample Question",
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2),
            10000,
            "Sample Place"
        )

        oneCourseRequestDto = OneCourseRequestDto(
            "Sample Prior Activity",
            "Sample Place",
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(2),
            "Sample Question"
        )
    }

    @Test
    fun `getFullCourse should return GptResponseDto`() {
        val url = "http://3.39.3.82:5000/course/full"

        Mockito.`when`(webClient.post().uri(url).retrieve().bodyToMono(GptResponseDto::class.java).block())
            .thenReturn(GptResponseDto(listOf(gptActivityDto)))

        val response = chatGptService.getFullCourse(fullCourseRequestDto)

        assertNotNull(response)
        assertEquals(listOf(gptActivityDto), response.result)

        Mockito.verify(webClient.post().uri(url).retrieve().bodyToMono(GptResponseDto::class.java)).block()
    }

    @Test
    fun `getFullCourse should throw LocoException`() {
        val url = "http://3.39.3.82:5000/course/full"

        Mockito.`when`(webClient.post().uri(url).retrieve().bodyToMono(GptResponseDto::class.java).block())
            .thenReturn(GptResponseDto(listOf(gptActivityDto)))

        val exception = assertThrows<LocoException> {
            chatGptService.getFullCourse(fullCourseRequestDto)
        }

        assertEquals(ErrorType.GPT_SERVER_ERROR.errorCode, exception.body.errorCode)
        assertEquals(ErrorType.GPT_SERVER_ERROR.message, exception.body.message)
    }

    @Test
    fun `getOneCourse should return GptActivityDto`() {
        val url = "http://3.39.3.82:5000/course"

        Mockito.`when`(webClient.post().uri(url).retrieve().bodyToMono(GptActivityDto::class.java).block())
            .thenReturn(gptActivityDto)

        val response = chatGptService.getOneCourse(oneCourseRequestDto)

        assertNotNull(response)
        assertEquals("Sample Activity", response.activity_name)

        Mockito.verify(webClient.post().uri(url).retrieve().bodyToMono(GptActivityDto::class.java)).block()
    }
}