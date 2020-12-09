package com.example.demo.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlShorterRequestDto {
    private String url;

    public UrlShorterRequestDto(){

    }
    public UrlShorterRequestDto(String url){
        this.url=url;
    }
}
