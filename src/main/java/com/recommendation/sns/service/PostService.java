package com.recommendation.sns.service;

import com.recommendation.sns.exception.ErrorCode;
import com.recommendation.sns.exception.SnsApplicationException;
import com.recommendation.sns.model.entity.PostEntity;
import com.recommendation.sns.model.entity.UserEntity;
import com.recommendation.sns.repository.PostEntityRepository;
import com.recommendation.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));

        PostEntity saved = postEntityRepository.save(PostEntity.of(title, body, userEntity));

    }
}
