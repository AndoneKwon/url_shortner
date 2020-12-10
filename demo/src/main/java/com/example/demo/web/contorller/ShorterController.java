package com.example.demo.web.contorller;

import com.example.demo.web.dto.UrlShorterRequestDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UrlShroterService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ShorterController {
    private final UrlShroterService urlShroterService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @PostMapping("/urlShorter")
    public String urlShorter(@NonNull @RequestBody UrlShorterRequestDto requestDto){
        logger.info(requestDto.getUrl());
        return urlShroterService.makeShorter(requestDto);
    }
    @GetMapping("/{shortUrl}")
    public void redirection(@PathVariable("shortUrl") String shortUrl, HttpServletResponse httpServletResponse) throws IOException {
        logger.info(shortUrl);
        String redirectionUrl = urlShroterService.getOrigin(shortUrl);
        if(redirectionUrl.compareTo("No Url")==0) {
            httpServletResponse.sendRedirect("http://localhost:8080/#/NoUrl");
            return;
        }
        httpServletResponse.sendRedirect(redirectionUrl);
    }
}
