package com.royalfoods.tastytables.api;

import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.factory.*;
import com.royalfoods.tastytables.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1")
@AllArgsConstructor
public class AdminApi {
    private UserService userService;
    private RoleService roleService;

    @PostMapping("/assign-role")
    public ResponseEntity addRoleToUser(@RequestBody UserDto userDto) {
        return ApiResponseBuilder.create(userService.addRoleToUser(userDto)).build();
    }

    @PostMapping("/create-role")
    public ResponseEntity createRole(@RequestBody RoleDto roleDto) {
        return ApiResponseBuilder
                .create(roleService.createRole(roleDto)).build();
    }
}
