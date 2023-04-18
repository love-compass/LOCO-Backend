package com.loco.place.ui

import com.loco.place.PlaceService
import com.loco.place.ui.dto.PlaceResponseDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@ExtendWith(MockitoExtension::class)
class PlaceControllerTest{
    @InjectMocks
    private lateinit var placeController: PlaceController

    @Mock
    private lateinit var placeService: PlaceService

    private lateinit var placeResponseDto: PlaceResponseDto

    @BeforeEach
    fun setUp() {
        placeResponseDto = PlaceResponseDto(
            "Sample Address", "Sample Address", "Sample Description",
            "Sample Phone", "Sample Place", "Sample Place", "Sample Road Address",
            "x", "y", "Sample Image")
    }

    @Test
    fun `getPlace should return PlaceResponseDto`() {
        val placeName = "Sample Place"

        Mockito.`when`(placeService.findPlace(placeName)).thenReturn(placeResponseDto)

        val response: ResponseEntity<PlaceResponseDto> = placeController.getPlace(placeName)

        assert(response.statusCode == HttpStatus.ACCEPTED)
        assert(response.body == placeResponseDto)

        Mockito.verify(placeService).findPlace(placeName)
    }

}