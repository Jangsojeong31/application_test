package com.study.application_test.controller;

import com.study.application_test.dto.request.PostRequestDto;
import com.study.application_test.dto.response.PostResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user")
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(@RequestBody PostRequestDto dto){
        ResponseDto<PostResponseDto> post = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // 게시글 조회 (전체)
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<PostResponseDto>>> getAllPosts(){
        ResponseDto<List<PostResponseDto>> posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    // 게시글 조회 (단건)
    @GetMapping("/all/{id}")
    public ResponseEntity<ResponseDto<PostResponseDto>> getPostById(@PathVariable Long id){
        ResponseDto<PostResponseDto> post = postService.getPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    // 게시글 수정
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseDto<PostResponseDto>> updatePost(
            @PathVariable Long id, @RequestBody PostRequestDto dto
    ){
        ResponseDto<PostResponseDto> post = postService.updatePost(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
}
