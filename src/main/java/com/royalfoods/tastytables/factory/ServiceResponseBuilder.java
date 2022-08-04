package com.royalfoods.tastytables.factory;

import com.royalfoods.tastytables.data.dto.*;
import org.springframework.http.*;

import java.util.Optional;
import static java.util.Optional.ofNullable;


@FunctionalInterface
public interface ServiceResponseBuilder<T> {
    Optional<ApiResponseDto> build();

     static <T> ServiceResponseBuilder create(HttpStatus status, String message, T body){

         ServiceResponseBuilder srBuilder = ()->{
             return ofNullable(new ApiResponseDto(status,message,body));
         };
         return srBuilder;
    }

}
