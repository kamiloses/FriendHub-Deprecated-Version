package com.application.friendhub.api.RestController;

import com.application.friendhub.api.exception.EmailAlreadyExistsException;
import com.application.friendhub.api.exception.InvalidCredentialsException;
import com.application.friendhub.api.dto.JwtResponseDTO;
import com.application.friendhub.api.jwt.JWTGenerator;
import com.application.friendhub.api.dto.LoginDto;
import com.application.friendhub.api.dto.AccountDto;
import com.application.friendhub.api.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;


    public AccountController(AccountService accountService, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody  AccountDto accountDto) {


           try {

               accountService.createAccount(accountDto);
               return new ResponseEntity<>("Account has been created",HttpStatus.CREATED);
           }catch (EmailAlreadyExistsException e) {
               throw new EmailAlreadyExistsException(e.getMessage());

           }


    }
//    @PutMapping("/register")
//public String updateAccount(@RequestBody AccountDto accountDto) {
//        accountDto.setPassword(accountDto.getPassword());
//
//    return "success";}


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDto loginDto){
        try {


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new ResponseEntity<>(new JwtResponseDTO(token), HttpStatus.OK);}
        catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password");

        }
    }




}