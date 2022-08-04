package com.royalfoods.tastytables.data.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.validation.constraints.*;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private String address;
    private String roles;
    private String fullName;
}
