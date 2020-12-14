package com.example.demo.web.dto;

import com.example.demo.domain.url.UrlHistroy;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MyInfoResponseDto {
    String uid;
    List<UrlHistroy> histroys;

    MyInfoResponseDto(String uid, List<UrlHistroy> histroys){
        this.uid=uid;
        this.histroys=histroys;
    }
}
