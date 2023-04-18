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

    fun findPlace(activity_name: String, place: String): PlaceResponseDto?{
        val arr = place.split("/")

        var kakakoUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query="

        if (activity_name.contains(arr[0])){
            kakakoUrl += activity_name
        }else{
            kakakoUrl = kakakoUrl + arr[0] + " " + activity_name
        }


        val client3 = WebClientConfig()

        val kakaoResult = client3.webClient()
            .get()
            .uri(kakakoUrl)
            .header("Authorization", kakaoKey)
            .retrieve()
            .bodyToMono(KakaoResponseDto::class.java)
            .block();


        if (kakaoResult == null || kakaoResult.documents.isEmpty()) {
            return null
        }
        var result = kakaoResult.documents.get(0)
        result.image_url = findImage(result.place_name)
        return result
    }

    fun findImage(activity_name: String):String?{

        val naverurl = "https://openapi.naver.com/v1/search/image?query=" + activity_name
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
