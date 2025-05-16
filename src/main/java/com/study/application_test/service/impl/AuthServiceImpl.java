package com.study.application_test.service.impl;

import com.study.application_test.common.ResponseMessage;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.dto.user.request.UserSignInRequestDto;
import com.study.application_test.dto.user.request.UserSignUpRequestDto;
import com.study.application_test.dto.user.response.UserSignInResponseDto;
import com.study.application_test.dto.user.response.UserSignUpResponseDto;
import com.study.application_test.entity.Role;
import com.study.application_test.entity.User;
import com.study.application_test.provider.JwtProvider;
import com.study.application_test.repository.RoleRepository;
import com.study.application_test.repository.UserRepository;
import com.study.application_test.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    // 회원가입
    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();

        UserSignUpResponseDto data = null;
        User user = null;

        if (!password.equals(confirmPassword)) {
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        }

        if (userRepository.existsByUsername(username)) { // 중복되는 경우
            return ResponseDto.setFailed(ResponseMessage.EXIST_DATA);
        }

        String encodePassword = bCryptPasswordEncoder.encode(password);

        // 권한 정보 확인
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build())); // 없으면 생성후 가져오기

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);

        user = User.builder()
                .username(username)
                .password(encodePassword)
                .roles(roleSet)
                .build();

        userRepository.save(user);

        data = new UserSignUpResponseDto(user);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    // 로그인
    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        UserSignInResponseDto data = null;

        int exprTime = jwtProvider.getExpiration();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXIST_USER));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // .matches(평문 비밀번호, 암호화된 비밀번호)
            // : 평문 비밀번호(실제 비밀번호)와 암호화된 비밀번호를 비교하여 일치 여부 반환(boolean)
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        }

        Set<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                // role -> role.getRoleName 과 동일
                .collect(Collectors.toSet());

        String token = jwtProvider.generateJwtToken(username, roles);

        data = new UserSignInResponseDto(token, user, exprTime);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

    }
}
