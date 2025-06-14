package com.ssafy.tripchat.global.security.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank
  private String id;

  @NotBlank
  private String password;
}
