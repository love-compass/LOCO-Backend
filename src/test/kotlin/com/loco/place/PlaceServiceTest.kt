package com.loco.place

import com.loco.exception.ErrorType
import com.loco.exception.LocoException
import com.loco.place.ui.dto.ImageResponseDto
import com.loco.place.ui.dto.KakaoResponseDto
import com.loco.place.ui.dto.NaverResponseDto
import com.loco.place.ui.dto.PlaceResponseDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient

@ExtendWith(MockitoExtension::class)
class PlaceServiceTest {

    @InjectMocks
    private lateinit var placeService: PlaceService

    @Mock
    private lateinit var webClient: WebClient

    private lateinit var kakaoResponseDto: KakaoResponseDto
    private lateinit var naverResponseDto: NaverResponseDto
    private lateinit var placeResponseDto: PlaceResponseDto
    private lateinit var imageResponseDto: ImageResponseDto

    @BeforeEach
    fun setUp() {
        placeResponseDto = PlaceResponseDto("Sample Place", "Sample Address", "Sample Category",
            "Sample Place", "Sample Place", "Sample URL", "Sample Place",
            "Sample Place", "Sample Place", "Sample Place")
        imageResponseDto = ImageResponseDto("Sample Place", "Sample Place", "Sample Place",
            "Sample Place", "Sample Place")
        kakaoResponseDto = KakaoResponseDto(listOf(placeResponseDto))
        naverResponseDto = NaverResponseDto(1, 1, listOf(imageResponseDto))
         }

    @Test
    fun `findPlace should return PlaceResponseDto`() {
        val placeName = "Sample Place"
        val kakaoUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=$placeName"
        val naverUrl = "https://openapi.naver.com/v1/search/image?query=$placeName"

        Mockito.`when`(webClient.get().uri(kakaoUrl).retrieve().bodyToMono(KakaoResponseDto::class.java).block())
            .thenReturn(kakaoResponseDto)
        Mockito.`when`(webClient.get().uri(naverUrl).retrieve().bodyToMono(NaverResponseDto::class.java).block())
            .thenReturn(naverResponseDto)

        val response: PlaceResponseDto = placeService.findPlace(placeName)

        assertNotNull(response)
        assertEquals(placeResponseDto, response)

        Mockito.verify(webClient.get().uri(kakaoUrl).retrieve().bodyToMono(KakaoResponseDto::class.java)).block()
        Mockito.verify(webClient.get().uri(naverUrl).retrieve().bodyToMono(NaverResponseDto::class.java)).block()
    }

    @Test
    fun `findPlace should throw LocoException`() {
        val placeName = "Sample Place"
        val kakaoUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=$placeName"

        Mockito.`when`(webClient.get().uri(kakaoUrl).retrieve().bodyToMono(KakaoResponseDto::class.java).block())
            .thenReturn(KakaoResponseDto(emptyList()))

        val exception = assertThrows<LocoException> {
            placeService.findPlace(placeName)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.httpStatus)
        assertEquals(ErrorType.KAKAO_PLACE_NOT_FOUND.errorCode, exception.body.errorCode)
        assertEquals(ErrorType.KAKAO_PLACE_NOT_FOUND.message, exception.body.message)
    }
}
