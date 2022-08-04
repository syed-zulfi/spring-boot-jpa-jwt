package com.royalfoods.tastytables.factory;

import com.royalfoods.tastytables.data.dto.*;
import org.springframework.http.*;

import java.net.*;

@FunctionalInterface
public interface ApiResponseBuilder<T> {
    ResponseEntity build();

     static <T> ApiResponseBuilder create(ApiResponseDto apiDto){
         ApiResponseBuilder apiResponseBuilder = ()->{
             return ResponseEntity
                     .status(apiDto.getStatus())
                     .body(apiDto);
         };
         return apiResponseBuilder;
     }

}
