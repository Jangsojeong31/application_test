package com.study.application_test.controller;

import com.study.application_test.dto.request.UserRequestDto;
import com.study.application_test.dto.response.GetUserResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.dto.response.UserResponseDto;
import com.study.application_test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final String GET_USER_INFO = "/me";

    // 내 정보 조회
    @GetMapping(GET_USER_INFO)
    public ResponseEntity<ResponseDto<GetUserResponseDto>> getUserInfo(
            @AuthenticationPrincipal String userEmail
            // SecurityContextHolder에 저장된 인증 객체의 principal을 가져와서 사용
            // : 현재 인증된(로그인된) 사용자의 정보를 가져오는 애너테이션
    ) {
        ResponseDto<GetUserResponseDto> response = userService.getUserInfo(userEmail);
        return ResponseEntity.ok(response); // ok 안에 response(body)가 담김
    }

}
