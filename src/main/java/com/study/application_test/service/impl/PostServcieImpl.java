package com.study.application_test.service.impl;

import com.study.application_test.common.ResponseMessage;
import com.study.application_test.dto.request.PostRequestDto;
import com.study.application_test.dto.response.PostResponseDto;
import com.study.application_test.dto.response.ResponseDto;
import com.study.application_test.entity.Post;
import com.study.application_test.entity.User;
import com.study.application_test.repository.PostRepository;
import com.study.application_test.repository.UserRepository;
import com.study.application_test.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServcieImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 생성
    @Override
    public ResponseDto<PostResponseDto> createPost(PostRequestDto dto) {
        PostResponseDto responseDto = null;

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXIST_POST + dto.getUserId()));

        Post newPost = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();

        Post savedPost = postRepository.save(newPost);

        responseDto = PostResponseDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .userId(savedPost.getUser().getId())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

    // 전체 조회
    @Override
    public ResponseDto<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> responseDtos = null;

        List<Post> posts = postRepository.findAll();


        responseDtos = posts.stream()
                .map(post -> PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .userId(post.getUser().getId())
                        .build())
                .collect(Collectors.toList());


        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDtos);
    }

    // 단건 조회
    @Override
    public ResponseDto<PostResponseDto> getPostById(Long id) {
        PostResponseDto responseDto = null;

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXIST_POST + id));

        responseDto = PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }

//    // 수정
    @Override
    public ResponseDto<PostResponseDto> updatePost(Long id, PostRequestDto dto) {
        PostResponseDto responseDto = null;

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXIST_POST + id));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        Post updatedPost = postRepository.save(post);

        responseDto = PostResponseDto.builder()
                .id(updatedPost.getId())
                .title(updatedPost.getTitle())
                .content(updatedPost.getContent())
                .userId(updatedPost.getUser().getId())
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
    }
}
