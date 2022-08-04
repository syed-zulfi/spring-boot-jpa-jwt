package com.royalfoods.tastytables.data.dto;

import com.fasterxml.jackson.databind.annotation.*;
import com.royalfoods.tastytables.data.dto.custom.*;
import lombok.*;

import java.time.*;

@Data
public abstract class AbstractResponseDto {
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    protected LocalDateTime timeStamp;
    protected String message;
}
