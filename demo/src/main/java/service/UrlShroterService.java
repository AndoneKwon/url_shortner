package service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import web.dto.UrlShorterRequestDto;

@Service
public class UrlShroterService {
    public String makeShorter(UrlShorterRequestDto urlShorterRequestDto){
        return "abc";
    }
}
