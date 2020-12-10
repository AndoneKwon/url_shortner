package com.example.demo.web.dto;

import com.example.demo.domain.url.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlShorterRequestDto {
    private String url;

    public Url toEntity(){
        return Url.builder()
                .originalUrl(url)
                .build();
    }
}
