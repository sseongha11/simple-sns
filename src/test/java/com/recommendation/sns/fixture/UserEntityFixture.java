package com.recommendation.sns.fixture;

import com.recommendation.sns.model.entity.UserEntity;

public class UserEntityFixture {
    public static UserEntity get (String userName, String password) {
        UserEntity result = new UserEntity();
        result.setId(1);
        result.setUserName(userName);
        result.setPassword(password);
        return result;
    }
}
