package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.url.Url;
import com.example.demo.domain.url.UrlHistoryRepository;
import com.example.demo.domain.url.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import com.example.demo.domain.url.UrlHistroy;
import com.example.demo.web.dto.UrlShorterRequestDto;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UrlShroterService {
    final private UrlRepository urlRepository;
    final private UrlHistoryRepository urlHistoryRepository;
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
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(url,Long.toString(id));
        redisTemplate.expire(url,1, TimeUnit.DAYS);
    }//Save to Redis

    public void makeHistory(long uid,String originUrl, String shortUrl){
        urlHistoryRepository.save(UrlHistroy.builder().uid(uid).originalUrl(originUrl).shortUrl(shortUrl).build());
    }

    public long getUid(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        Long uid = decodedJWT.getClaim("id").asLong();
        return uid;
    }

    public String makeShorter(UrlShorterRequestDto urlShorterRequestDto,String token){
        String url = urlShorterRequestDto.getUrl();
        long uid=0;
        if(token!=null){
            uid=getUid(token);
        }
        Long id=null;
        String shortUrl;
        if(redisTemplate.opsForValue().get(url)!=null)
            id=Long.parseLong(redisTemplate.opsForValue().get(url).toString());

        if(id!=null){
            id = Long.parseLong(redisTemplate.opsForValue().get(url).toString());
            shortUrl = encode(id);
            if(token!=null)
                makeHistory(uid,url,shortUrl);
            return shortUrl;
        }//레디스에 존재하면


        Url findUrl = urlRepository.findFirstByOriginalUrl(url);//기존에 생성한 주소면 재사용

        if(findUrl==null){
            id=urlRepository.save(urlShorterRequestDto.toEntity()).getId();//기존에 생성한 Url이 아니면 DB에 저장 후 id 리턴
            shortUrl = encode(id);
            saveToRedis(url,id);
            if(token!=null)
                makeHistory(uid,url,shortUrl);
            return shortUrl;
        }else{
            shortUrl = encode(findUrl.getId());
            saveToRedis(url,findUrl.getId());
            if(token!=null)
                makeHistory(uid,url,shortUrl);
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

    public List<UrlHistroy> getHistroy(long uid){
        return urlHistoryRepository.findAllByUid(uid);
    }
}
