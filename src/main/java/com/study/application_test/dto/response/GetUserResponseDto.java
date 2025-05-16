package com.study.application_test.dto.response;

import com.study.application_test.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponseDto {
    private Long id;
    private String username;


    public GetUserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
