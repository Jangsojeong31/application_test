package com.study.application_test.service;

import com.study.application_test.dto.request.PostRequestDto;
import com.study.application_test.dto.response.PostResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    ResponseDto<PostResponseDto> createPost(PostRequestDto dto);

    ResponseDto<List<PostResponseDto>> getAllPosts();

    ResponseDto<PostResponseDto> getPostById(Long id);

    ResponseDto<PostResponseDto> updatePost(Long id, PostRequestDto dto);
}
