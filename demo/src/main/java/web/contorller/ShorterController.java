package web.contorller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UrlShroterService;
import web.dto.UrlShorterRequestDto;

@RestController
@RequiredArgsConstructor
public class ShorterController {
    private final UrlShroterService urlShroterService;

    @PostMapping("/urlShorter")
    public String urlShorter(@NonNull @RequestBody UrlShorterRequestDto requestDto){
        System.out.println();
        return urlShroterService.makeShorter(requestDto);
    }
}
