package com.max.quizspring.auth;

import lombok.Data;

@Data
public class RegisterAgentRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
}
