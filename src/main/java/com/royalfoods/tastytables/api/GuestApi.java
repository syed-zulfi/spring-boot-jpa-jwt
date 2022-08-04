package com.royalfoods.tastytables.api;

import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.factory.*;
import com.royalfoods.tastytables.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;


@RestController
@RequestMapping("guest/v1")
public class GuestApi {
    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;




    @GetMapping("/welcome")
    ResponseEntity<String> hello(){
        return ResponseEntity.ok().body("Hello...");
    }

   /* @PostMapping("/authenticate")
    ResponseEntity<String> login(@RequestBody LoginDto login, HttpServletRequest request, HttpServletResponse response){

        return ApiResponseBuilder.create(loginService.authenticate(login,request,response)).build();
    }*/
    @PostMapping("/register")
    ResponseEntity register(@RequestBody @Valid UserDto user){
        return ApiResponseBuilder
                .create(userService
                        .createUser(user))
                .build();
    }







}
