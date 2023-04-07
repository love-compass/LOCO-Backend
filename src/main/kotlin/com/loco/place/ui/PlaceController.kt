package com.loco.place.ui

import com.loco.place.PlaceService
import com.loco.place.ui.dto.PlaceResponseDto
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class PlaceController(val placeService: PlaceService) {
    @GetMapping("/activity/{activity_name}/place/{place_name}")
    fun getPlace(@PathVariable("activity_name") activity_name: String, @PathVariable("place_name") place_name: String): ResponseEntity<PlaceResponseDto> {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(placeService.findPlace(activity_name, place_name))
    }
}