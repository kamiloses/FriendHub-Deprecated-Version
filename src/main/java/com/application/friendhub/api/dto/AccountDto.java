package com.application.friendhub.api.dto;

import com.application.friendhub.api.other.Role;
import com.application.friendhub.registrationProcess.other.UniqueEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
  //  @UniqueEmail
    public String email;
    public String password;
    public Role role;

}
