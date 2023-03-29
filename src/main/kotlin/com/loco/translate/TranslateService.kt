package com.loco.translate

import com.loco.config.WebClientConfig
import com.loco.exception.ErrorType
import com.loco.exception.ExceptionResponse
import com.loco.exception.LocoException
import com.loco.translate.ui.dto.TranslateMessageResponseDto
import com.loco.translate.ui.dto.TranslateRequestDto
import com.loco.translate.ui.dto.TranslateResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import java.net.URLEncoder

@Service
class TranslateService {
    @Value("\${api.naver.id}")
    val naverId: String = "1"

    @Value("\${api.naver.secret}")
    val naverSecret: String = "1"

    val translateUrl: String = "https://openapi.naver.com/v1/papago/n2mt"

    fun englishToKorean(eng:String): String {

        val text = URLEncoder.encode(eng, "UTF-8");

        val client3 = WebClientConfig()

        val translateResult = client3.webClient()
            .post()
            .uri(translateUrl)
            .header("X-Naver-Client-Id", naverId)
            .header("X-Naver-Client-Secret", naverSecret)
            .body(BodyInserters.fromValue(TranslateRequestDto("en", "ko", text)))
            .retrieve()
            .bodyToMono(TranslateResponseDto::class.java)
            .block()
        if(translateResult == null || translateResult.message == null)
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.NAVER_TRANSLATE_ERROR.errorCode, ErrorType.NAVER_TRANSLATE_ERROR.message))

        return translateResult.message.result.translatedText
    }

    fun koreanToEnglish(kor:String): String{

        val text = URLEncoder.encode(kor, "UTF-8");

        val client3 = WebClientConfig()

        val translateResult = client3.webClient()
            .post()
            .uri(translateUrl)
            .header("X-Naver-Client-Id", naverId)
            .header("X-Naver-Client-Secret", naverSecret)
            .body(BodyInserters.fromValue(TranslateRequestDto("ko", "en", text)))
            .retrieve()
            .bodyToMono(TranslateResponseDto::class.java)
            .block()

        if(translateResult == null || translateResult.message == null)
            throw LocoException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.NAVER_TRANSLATE_ERROR.errorCode, ErrorType.NAVER_TRANSLATE_ERROR.message))
        return translateResult.message.result.translatedText
    }
}


