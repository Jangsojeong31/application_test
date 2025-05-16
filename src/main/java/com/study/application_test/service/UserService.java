package com.study.application_test.service;

import com.study.application_test.dto.request.UserRequestDto;
import com.study.application_test.dto.response.GetUserResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.dto.response.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseDto<GetUserResponseDto> getUserInfo(String userEmail);

}
