package com.application.friendhub.registrationProcess.controller;

import com.application.friendhub.EmailSender.EmailServiceImpl;
import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.registrationProcess.dto.FirstStepDto;
import com.application.friendhub.registrationProcess.dto.RemindMyPasswordDto;
import com.application.friendhub.registrationProcess.service.FirstStepDtoService;
import com.application.friendhub.registrationProcess.dto.SecondStepDto;
import com.application.friendhub.registrationProcess.service.SecondStepService;
import com.application.friendhub.registrationProcess.service.SelectDateOfBirthOptionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Controller
@Slf4j
public class RegistrationController {


    private final SelectDateOfBirthOptionService selectDateOfBirthOptionService;
    private final FirstStepDtoService firstStepDtoService;
    private final SecondStepService secondStepService;
    private final EmailServiceImpl emailService;
    private String token;
    private UserEntity userEntity;
    private UserDetailsEntity userDetailsEntity;

    public RegistrationController(SelectDateOfBirthOptionService selectDateOfBirthOptionService, FirstStepDtoService firstStepDtoService, SecondStepService secondStepService, EmailServiceImpl emailService) {
        this.selectDateOfBirthOptionService = selectDateOfBirthOptionService;
        this.firstStepDtoService = firstStepDtoService;
        this.secondStepService = secondStepService;
        this.emailService = emailService;

    }

    @GetMapping("/friendHub/register/firstStep")
    public String firstStep(Model model) {
        model.addAttribute("days", selectDateOfBirthOptionService.getDays());
        model.addAttribute("months", selectDateOfBirthOptionService.getMonths());
        model.addAttribute("years", selectDateOfBirthOptionService.getYears());
        model.addAttribute("firstStepDto", new FirstStepDto());


        return "html/registrationAndLogin/firstStep";
    }


    @PostMapping("/friendHub/register/firstStep/add")
    public String firstStep(@Valid @ModelAttribute("firstStepDto") FirstStepDto firstStepDto,
                            BindingResult bindingResult, Model model,
                            @RequestParam("dob-day") String day,
                            @RequestParam("dob-month") String month,
                            @RequestParam("dob-year") String year) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("days", selectDateOfBirthOptionService.getDays());
            model.addAttribute("months", selectDateOfBirthOptionService.getMonths());
            model.addAttribute("years", selectDateOfBirthOptionService.getYears());
            return "html/registrationAndLogin/firstStep";
        }

        firstStepDtoService.SetMonthForFirstStepDto(day, month, year, firstStepDto);
        if (!selectDateOfBirthOptionService.validateDayOfMonth(firstStepDto)) {
            model.addAttribute("days", selectDateOfBirthOptionService.getDays());
            model.addAttribute("months", selectDateOfBirthOptionService.getMonths());
            model.addAttribute("years", selectDateOfBirthOptionService.getYears());
            //todo dodać komunikat związany z  validateDayOfMonth
            return "html/registrationAndLogin/firstStep";
        }

        userEntity = firstStepDtoService.convertFirstStepDtoToUserEntity(firstStepDto);

        userDetailsEntity = firstStepDtoService.convertFirstStepDtoToUserDetailsEntity(firstStepDto, userEntity);


        token = emailService.generateToken();

      CompletableFuture.runAsync(()-> {
          try {
              emailService.sendEmail(userEntity.getEmail(),token);
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      });


        return "redirect:/friendHub/register/secondStep";
    }

    @GetMapping("/friendHub/register/secondStep")
    public String secondStep(Model model) {
        model.addAttribute("secondStepDto", new SecondStepDto());

        return "html/registrationAndLogin/SecondStep";
    }

    @PostMapping("/friendHub/register/secondStep/add")
    public String secondStep(@ModelAttribute("secondStepDto") SecondStepDto secondStepDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors() | !secondStepDto.getToken().equals(token.toUpperCase())) {

            return "html/registrationAndLogin/SecondStep";
        }

        secondStepService.createUserAccount(userEntity,userDetailsEntity);
        return "redirect:/friendHub/login";
    }


    @PostMapping("/friendHub/register/secondStep/resendToken")
    public String resendToken() {
        token = emailService.generateToken();

        return "redirect:/friendHub/register/secondStep";
    }

    @GetMapping("/friendHub/login")
    public String login(Model model) {
        return "html/registrationAndLogin/login";
    }


    @GetMapping("/friendHub/remindMyPassword")
    public String remindMyPassword() {


        return "html/registrationAndLogin/remindMyPassword";
    }


@PostMapping("/friendHub/remindMyPassword/add")
public String remindMyPassword(@ModelAttribute RemindMyPasswordDto remindMyPasswordDto) {
/*emailService.sendYourAccountData(remindMyPasswordDto.getEmail());*/



return "redirect:/friendHub/login";}
}