package com.study.application_test.dto.user.response;

import com.study.application_test.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInResponseDto {
    private String token;
    private User user;
    private int exprTime;

}
