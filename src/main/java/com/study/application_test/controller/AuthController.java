package com.study.application_test.controller;


import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.dto.user.request.UserSignInRequestDto;
import com.study.application_test.dto.user.request.UserSignUpRequestDto;
import com.study.application_test.dto.user.response.UserSignInResponseDto;
import com.study.application_test.dto.user.response.UserSignUpResponseDto;
import com.study.application_test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    //
    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 1) 회원가입
    // - HTTP 메서드: POST
    // - URI 경로: /signup
    // @Params: UserSignUpRequestDto
    // @Return: UserSignUpResponseDto
    @PostMapping(POST_SIGN_UP)
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@RequestBody UserSignUpRequestDto dto) {
        ResponseDto<UserSignUpResponseDto> response = authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 로그인
    // - HTTP 메서드: POST
    // cf) GET vs POST 중 POST 사용 권장: 로그인 과정에서 사용자 이름과 비밀번호와 같은 개인 정보를 서버로 전송하기 때문
    // GET 요청은 URL에 데이터가 노출(보통 조회는 데이터 구분값(PK) 사용)
    // - URI 경로: /signin
    // @Params: UserSignInRequestDto
    // @Return: UserSignInResponseDto
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@RequestBody UserSignInRequestDto dto) {
        ResponseDto<UserSignInResponseDto> response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

