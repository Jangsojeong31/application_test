package com.study.application_test.service.impl;

import com.study.application_test.common.ResponseMessage;
import com.study.application_test.dto.request.UserRequestDto;
import com.study.application_test.dto.response.GetUserResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.dto.response.UserResponseDto;
import com.study.application_test.entity.User;
import com.study.application_test.repository.UserRepository;
import com.study.application_test.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    // 사용자 정보 조회
    @Override
    public ResponseDto<GetUserResponseDto> getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXIST_USER));

        GetUserResponseDto data = new GetUserResponseDto(user);

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}
