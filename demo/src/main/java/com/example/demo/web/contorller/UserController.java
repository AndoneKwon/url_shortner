package com.example.demo.web.contorller;

import com.example.demo.domain.login.UserRepository;
import com.example.demo.service.LoginService;
import com.example.demo.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class UserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LoginService loginService;

    @PostMapping("/login")
    LoginResponseDto loginController(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto returnValue= loginService.login(loginRequestDto);
        return returnValue;
    }

    @PostMapping("/join")
    JoinResponseDto joinController(@RequestBody JoinRequestDto joinRequestDto){
        return loginService.join(joinRequestDto);
    }

    @GetMapping("/myInfo")
    MyInfoResponseDto myInfoController(@RequestHeader String token){
        return loginService.getMyInfo(token);
    }

}
