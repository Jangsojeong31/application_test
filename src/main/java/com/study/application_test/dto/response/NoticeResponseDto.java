package com.study.application_test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NoticeResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
}
