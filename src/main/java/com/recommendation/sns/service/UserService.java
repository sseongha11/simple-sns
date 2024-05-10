package com.recommendation.sns.service;

import com.recommendation.sns.exception.ErrorCode;
import com.recommendation.sns.exception.SnsApplicationException;
import com.recommendation.sns.model.User;
import com.recommendation.sns.model.entity.UserEntity;
import com.recommendation.sns.repository.UserEntityRepository;
import com.recommendation.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expired-time-ms}")
    private Long expiredTimeMs;

    public User loadUserByUserName(String userName) {
        return User.fromEntity(userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName))));
    }

    @Transactional
    public User join(String userName, String password) {
        // Check the user with userName in the case of login
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        // Sign up with User Information
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));
        return User.fromEntity(userEntity);
    }

    // TODO: implement
    public String login(String userName, String password) {
        // Check the userName
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));

        // Check the password
        if(!encoder.matches(password, userEntity.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // Create the JWT
        String token = JwtTokenUtils.generateToken(userName,secretKey, expiredTimeMs);

        return token;
    }
}
