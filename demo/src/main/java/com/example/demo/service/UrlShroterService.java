package com.example.demo.service;

import com.example.demo.domain.url.Url;
import com.example.demo.domain.url.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.web.dto.UrlShorterRequestDto;

@Service
@RequiredArgsConstructor
public class UrlShroterService {
    final private UrlRepository urlRepository;
    private String BASE62 ="ABCDEFGHIJKLMNOPQRSTUVWSYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String encode(long idx){
        StringBuffer sf = new StringBuffer();
        if(idx==0){
            sf.append("A");
        }
        while (idx>0){
            sf.append(BASE62.charAt((int) (idx%62)));
            idx/=62;
        }
        return sf.toString();
    }

    public long decode(String code){
        long sum = 0;
        long pow = 1;
        for(int i=0;i<code.length();i++){
            sum+=BASE62.indexOf(code.charAt(i));
        }
        return sum;
    }

    public String makeShorter(UrlShorterRequestDto urlShorterRequestDto){
        String url = urlShorterRequestDto.getUrl();
        long id;
        String shortUrl;
        Url findUrl = urlRepository.findFirstByOriginalUrl(url);
        if(findUrl==null){
            id=urlRepository.save(urlShorterRequestDto.toEntity()).getId();
            shortUrl = encode(id);
            return shortUrl;
        }else{
            shortUrl = encode(findUrl.getId());
            return shortUrl;
        }
    }

    public String getOrigin(String url){
        long idx = decode(url);
        Url originalUrl=urlRepository.findFirstById(idx);
        if(originalUrl==null){
            return "No Url";
        }else{
            return originalUrl.getOriginalUrl();
        }
    }
}
