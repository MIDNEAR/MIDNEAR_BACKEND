package com.midnear.midnearshopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.midnear.midnearshopping.exception.ApiResponse;

import com.midnear.midnearshopping.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuth2Controller {
    private final OAuthService oAuthService;
    //Oauth
    @GetMapping("/login/oauth/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        try{
            return ResponseEntity.ok(new ApiResponse(true,"카카오 로그인 성공", oAuthService.googleLogin(decodedCode)));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
        } catch (JsonProcessingException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
    //Oauth
    @GetMapping("/login/oauth/google")
    public ResponseEntity<?> googleLogin(@RequestParam String code){
        final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        try{
            return ResponseEntity.ok(new ApiResponse(true,"구글 로그인 성공", oAuthService.googleLogin(decodedCode)));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
        } catch (JsonProcessingException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }

    }
    //Oauth
    @GetMapping("/login/oauth/naver")
    public ResponseEntity<?> naverLogin(@RequestParam String code, @RequestParam String state){
        final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        try{
            return ResponseEntity.ok(new ApiResponse(true,"네이버 로그인 성공", oAuthService.naverLogin(decodedCode, state)));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item Not Found");
        } catch (JsonProcessingException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
}
