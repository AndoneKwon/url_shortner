package com.example.demo.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {
    private String uid;
    private String password;

    JoinRequestDto(String uid, String password){
        this.uid=uid;
        this.password=password;
    }
}
