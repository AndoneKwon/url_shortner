package com.example.demo.web.contorller;

import com.example.demo.web.dto.UrlShorterRequestDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UrlShroterService;

@RestController
@RequiredArgsConstructor
public class ShorterController {
    private final UrlShroterService urlShroterService;

    @PostMapping("/urlShorter")
    public String urlShorter(@RequestBody UrlShorterRequestDto requestDto){
        return urlShroterService.makeShorter(requestDto);
    }
}
