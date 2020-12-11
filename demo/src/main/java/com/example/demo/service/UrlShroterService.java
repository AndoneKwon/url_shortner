package com.example.demo.service;


import com.example.demo.domain.dao.RedisUrlDAO;
import com.example.demo.domain.url.Url;
import com.example.demo.domain.url.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.example.demo.web.dto.UrlShorterRequestDto;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UrlShroterService {
    final private UrlRepository urlRepository;
    final private RedisTemplate redisTemplate;
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
    }//Base62 Encoding

    public long decode(String code){
        long sum = 0;
        long pow = 1;
        for(int i=0;i<code.length();i++){
            sum+=BASE62.indexOf(code.charAt(i));
        }
        return sum;
    }//Base62 Decoding

    public void saveToRedis(String url,long id){
        redisTemplate.opsForValue().set(url,Long.toString(id));
        redisTemplate.expire(url,1, TimeUnit.DAYS);
    }//Save to Redis

    public String makeShorter(UrlShorterRequestDto urlShorterRequestDto){
        String url = urlShorterRequestDto.getUrl();
        Long id;
        String shortUrl;
        redisTemplate.opsForValue().get(url);
        id = Long.parseLong(redisTemplate.opsForValue().get(url).toString());
        if(id!=null){
            shortUrl = encode(id);
            return shortUrl;
        }

        Url findUrl = urlRepository.findFirstByOriginalUrl(url);//기존에 생성한 주소면 재사용

        if(findUrl==null){
            id=urlRepository.save(urlShorterRequestDto.toEntity()).getId();//기존에 생성한 Url이 아니면 DB에 저장 후 id 리턴
            shortUrl = encode(id);
            saveToRedis(url,id);
            return shortUrl;
        }else{
            shortUrl = encode(findUrl.getId());
            saveToRedis(url,findUrl.getId());
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
