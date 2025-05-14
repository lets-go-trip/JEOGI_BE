package com.ssafy.tripchat.global.security.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
  @NotBlank
  private String id;

  @NotBlank
  private String password;

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;
}
