package com.study.application_test.service;

import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.dto.user.request.UserSignInRequestDto;
import com.study.application_test.dto.user.request.UserSignUpRequestDto;
import com.study.application_test.dto.user.response.UserSignInResponseDto;
import com.study.application_test.dto.user.response.UserSignUpResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto);

    ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto);
}
