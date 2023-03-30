package com.loco.place


import com.loco.config.WebClientConfig
import com.loco.exception.ErrorType
import com.loco.exception.ExceptionResponse
import com.loco.exception.LocoException
import com.loco.place.ui.dto.KakaoResponseDto
import com.loco.place.ui.dto.NaverResponseDto
import com.loco.place.ui.dto.PlaceResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PlaceService {
    @Value("\${api.kakao.key}")
    val kakaoKey: String = "1"

    @Value("\${api.naver.id}")
    val naverId: String = "1"

    @Value("\${api.naver.secret}")
    val naverSecret: String = "1"

    fun findPlace(place_name: String): PlaceResponseDto{
        val kakakoUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + place_name

        val client3 = WebClientConfig()

        val kakaoResult = client3.webClient()
            .get()
            .uri(kakakoUrl)
            .header("Authorization", kakaoKey)
            .retrieve()
            .bodyToMono(KakaoResponseDto::class.java)
            .block();

        if (kakaoResult == null || kakaoResult.documents.isEmpty()) {
            println(place_name)
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.KAKAO_PLACE_NOT_FOUND.errorCode, ErrorType.KAKAO_PLACE_NOT_FOUND.message))
        }
        var result = kakaoResult.documents.get(0)
        result.image_url = findImage(result.place_name)
        return result

    }

    fun findImage(place_name: String):String?{

        val naverurl = "https://openapi.naver.com/v1/search/image?query=" + place_name
        val client3 = WebClientConfig()

        val result = client3.webClient()
            .get()
            .uri(naverurl)
            .header("X-Naver-Client-Id", naverId)
            .header("X-Naver-Client-Secret", naverSecret)
            .retrieve()
            .bodyToMono(NaverResponseDto::class.java)
            .block();
        if(result == null || result.items.isEmpty()){
            return null
        }
        return result.items.get(0).link
    }
}
