package com.project.angleace.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginSignupResponse {
    private String email;
    private String token;
}
