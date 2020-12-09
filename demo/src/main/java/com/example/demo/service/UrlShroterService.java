package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.web.dto.UrlShorterRequestDto;

@Service
public class UrlShroterService {
    public String makeShorter(UrlShorterRequestDto urlShorterRequestDto){
        return "abc";
    }
}
