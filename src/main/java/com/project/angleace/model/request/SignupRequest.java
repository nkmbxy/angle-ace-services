package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SignupRequest {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
}
