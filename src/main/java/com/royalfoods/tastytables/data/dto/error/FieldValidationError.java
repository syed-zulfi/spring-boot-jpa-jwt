package com.royalfoods.tastytables.data.dto.error;

import lombok.*;

import java.io.*;

@Builder
@Data
public class FieldValidationError extends SubError implements Serializable {
    private String fieldName;
    private String fieldType;
    private String entityName;
    private String fieldError;

}
