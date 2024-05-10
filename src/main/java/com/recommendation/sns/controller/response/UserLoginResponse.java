package com.recommendation.sns.controller.response;

import com.recommendation.sns.model.User;
import com.recommendation.sns.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

   private String token;
}
