package com.application.friendhub.api.service;

import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.api.dto.AccountDto;
import com.application.friendhub.api.exception.EmailAlreadyExistsException;
import com.application.friendhub.api.other.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createAccount(AccountDto accountDto) {

        if (userRepository.existsByEmail(accountDto.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(accountDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        userEntity.setRole(Role.ROLE_ADMIN);
        userRepository.save(userEntity);


    return userEntity;}


/*    @PutMapping("/change-password/{userId}")
    public String changePassword(@PathVariable Long userId, @RequestBody ChangePasswordRequest request) {

        createAccountService.changePassword(userId, request.getNewPassword());

        return "Password changed successfully";
    }*/



}
