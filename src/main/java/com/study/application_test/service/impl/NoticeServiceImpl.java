package com.study.application_test.service.impl;

import com.study.application_test.common.ResponseMessage;
import com.study.application_test.dto.request.NoticeRequestDto;
import com.study.application_test.dto.response.NoticeResponseDto;
import com.study.application_test.dto.response.PostResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.entity.Notice;
import com.study.application_test.entity.Post;
import com.study.application_test.entity.User;
import com.study.application_test.repository.NoticeRepository;
import com.study.application_test.repository.UserRepository;
import com.study.application_test.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseDto<NoticeResponseDto> createNotice(NoticeRequestDto dto) {
        NoticeResponseDto responseDto = null;

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXIST_POST + dto.getUserId()));

        Notice newNotice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        Notice savedNotice = noticeRepository.save(newNotice);

        responseDto = NoticeResponseDto.builder()
                .id(savedNotice.getId())
                .title(savedNotice.getTitle())
                .content(savedNotice.getContent())
                .userId(savedNotice.getUser().getId())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }
}
