package com.application.friendhub.api.RestController;

import com.application.friendhub.api.dto.AccountDto;
import com.application.friendhub.api.exception.EmailAlreadyExistsException;
import com.application.friendhub.api.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AccountControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;



    @Test
    public void ShouldTestRegistrationProcess() throws Exception {



         mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON)
                         .content("{\"email\": \"kamil@gmail.com\", \"password\": \"kamil123\"}"))
                 .andExpect(status().isCreated())
                 .andExpect(content().string("Account has been created"));
    }
    @Test
    public void shouldTestIfRegistrationProcessThrowsEx() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail("kamil@gmail.com");
        accountDto.setPassword("kamil123");

        doThrow(new EmailAlreadyExistsException("Email already exists")).when(accountService).createAccount(any(AccountDto.class));

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"kamil@gmail.com\", \"password\": \"kamil123\"}"))
                .andExpect(status().isConflict())
                .andExpect(result -> assertEquals("Email already exists", result.getResolvedException().getMessage()));
    }

    }