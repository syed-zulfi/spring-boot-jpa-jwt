package com.royalfoods.tastytables.api;

import com.royalfoods.tastytables.factory.*;
import com.royalfoods.tastytables.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/v1")
@AllArgsConstructor
public class UserApi {
    UserService userService;

    @GetMapping("/all")
    ResponseEntity listProducts(){
        return ApiResponseBuilder.create(userService.getAll()).build();
    }
    @GetMapping("/{email}")
    ResponseEntity singleUser(@PathVariable("email") String email){
       return ApiResponseBuilder.create(userService.getUserFrom(email)).build();
    }


}
