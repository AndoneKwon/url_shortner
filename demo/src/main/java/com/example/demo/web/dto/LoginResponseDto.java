package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    int code;
    String message;
    String token;
    String refreshToken;


    LoginResponseDto(int code, String message){
        this.code=code;
        this.message=message;
    }


    LoginResponseDto(int code, String message, String token, String refreshToken){
        this.code=code;
        this.message=message;
        this.token=token;
        this.refreshToken=refreshToken;
    }
}
