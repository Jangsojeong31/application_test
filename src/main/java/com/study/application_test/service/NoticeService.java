package com.study.application_test.service;

import com.study.application_test.dto.request.NoticeRequestDto;
import com.study.application_test.dto.response.NoticeResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface NoticeService {
    ResponseDto<NoticeResponseDto> createNotice(NoticeRequestDto dto);
}
