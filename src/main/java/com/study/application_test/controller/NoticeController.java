package com.study.application_test.controller;

import com.study.application_test.dto.request.NoticeRequestDto;
import com.study.application_test.dto.request.PostRequestDto;
import com.study.application_test.dto.response.NoticeResponseDto;
import com.study.application_test.dto.response.PostResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(@RequestBody NoticeRequestDto dto){
        ResponseDto<NoticeResponseDto> notice = noticeService.createNotice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(notice);
    }
}
