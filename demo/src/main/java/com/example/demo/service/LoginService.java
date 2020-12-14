package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.login.User;
import com.example.demo.domain.login.UserRepository;
import com.example.demo.domain.url.UrlHistroy;
import com.example.demo.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    Logger loggerFactory = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final UrlShroterService urlShroterService;
    private final RedisTemplate redisTemplate;

    @Value("${jwtsecret}")
    private String secret;

    public MyInfoResponseDto getMyInfo(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        long id = decodedJWT.getClaim("id").asLong();
        String uid = decodedJWT.getClaim("uid").asString();
        List<UrlHistroy> histroysList = urlShroterService.getHistroy(id);
        return MyInfoResponseDto.builder().histroys(histroysList).uid(uid).build();
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            User user=userRepository.findFirstByUid(loginRequestDto.getUid());
            if(user==null)
                return LoginResponseDto.builder().code(201).message("Id 또는 비밀번호를 확인해주세요.").build();
            else if(!BCrypt.checkpw(loginRequestDto.getPassword()+user.getSalt(),user.getPassword())){
                return LoginResponseDto.builder().code(202).message("Id 또는 비밀번호를 확인해주세요.").build();
            }
            else{
                String token = JWT.create().withClaim("id",user.getId())
                        .withClaim("status",user.getStatus())
                        .withClaim("uid",user.getUid())
                        .withIssuer("kwon")
                        .withExpiresAt(makeTime(0))
                        .sign(algorithm);

                String refreshToken = JWT.create()
                        .withIssuer("kwon")
                        .withExpiresAt(makeTime(1))
                        .sign(algorithm);
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                redisTemplate.opsForValue().set(Long.toString(user.getId()),refreshToken);

                return LoginResponseDto.builder().code(200).message("success").token(token).refreshToken(refreshToken).build();
            }
        }catch (JWTCreationException e){
            loggerFactory.error("Class {}",this.getClass(),e);
            return LoginResponseDto.builder().code(203).message("Create Error").build();
        }
    }

    public JoinResponseDto join(JoinRequestDto joinRequestDto){
        try{
            String salt = BCrypt.gensalt(8);
            String hashedPassword = BCrypt.hashpw(joinRequestDto.getPassword()+salt, BCrypt.gensalt());

            userRepository.save(User.builder().uid(joinRequestDto.getUid()).password(hashedPassword).salt(salt).build());
            return JoinResponseDto.builder().code(200).message("success").build();
        }catch (Exception e){
            loggerFactory.error("Class {}",getClass(),e);
            return JoinResponseDto.builder().code(201).message("join error").build();
        }
    }

    private Date makeTime(int timezone){
        Calendar cal = Calendar.getInstance();
        Date expire_date = new Date();
        cal.setTime(expire_date);
        if(timezone==0)
            cal.add(Calendar.MINUTE,40);
        else
            cal.add(Calendar.DATE,1);
        expire_date=cal.getTime();
        return expire_date;
    }
}
