package com.application.friendhub.registrationProcess.controller;

import com.application.friendhub.EmailSender.EmailServiceImpl;
import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.registrationProcess.dto.FirstStepDto;
import com.application.friendhub.registrationProcess.service.FirstStepDtoService;
import com.application.friendhub.registrationProcess.dto.SecondStepDto;
import com.application.friendhub.registrationProcess.service.SelectOptionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class RegistrationController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final SelectOptionService selectOptionService;
    private final FirstStepDtoService firstStepDtoService;
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private String token;
    private UserEntity user;
    private UserDetailsEntity userDetailsEntity;
    public RegistrationController(SelectOptionService selectOptionService, FirstStepDtoService firstStepDtoService, UserDetailsRepository userDetailsRepository, UserRepository userRepository, EmailServiceImpl emailService) {
        this.selectOptionService = selectOptionService;
        this.firstStepDtoService = firstStepDtoService;
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @GetMapping("/register/firstStep")
    public String firstStep(Model model) {
        model.addAttribute("days", selectOptionService.getDays());
        model.addAttribute("months", selectOptionService.getMonths());
        model.addAttribute("years", selectOptionService.getYears());
        model.addAttribute("firstStepDto", new FirstStepDto());
        System.out.println(selectOptionService.getMonths());

        return "html/registrationAndLogin/firstStep";
    }


    @PostMapping("/register/firstStep/add")
    public String firstStep(@Valid @ModelAttribute("firstStepDto") FirstStepDto firstStepDto,
                            BindingResult bindingResult, Model model,

                            @RequestParam("dob-day") String day,
                            @RequestParam("dob-month") String month,
                            @RequestParam("dob-year") String year
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("days", selectOptionService.getDays());
            model.addAttribute("months", selectOptionService.getMonths());
            model.addAttribute("years", selectOptionService.getYears());
            return "html/registrationAndLogin/firstStep";
        }

        firstStepDtoService.SetMonthForFirstStepDto(day, month, year, firstStepDto);
        if (!selectOptionService.validateDayOfMonth(firstStepDto)) {
            model.addAttribute("days", selectOptionService.getDays());
            model.addAttribute("months", selectOptionService.getMonths());
            model.addAttribute("years", selectOptionService.getYears());
            //todo dodać komunikat związany z  validateDayOfMonth
            return "html/registrationAndLogin/firstStep";
        }

        user = firstStepDtoService.convertFirstStepDtoToUserEntity(firstStepDto);

        userDetailsEntity = firstStepDtoService.convertFirstStepDtoToUserDetailsEntity(firstStepDto, user);


        token = emailService.generateToken();
        LOG.error(token);
       /* emailService.sendEmail(user.getEmail(),token);*/

        return "redirect:/register/secondStep";
    }

    @GetMapping("/register/secondStep")
     public String secondStep(Model model) {
     model.addAttribute("secondStepDto",new SecondStepDto());

        return "html/registrationAndLogin/SecondStep";
    }
    @PostMapping("/register/secondStep/add")
    public String secondStep(@ModelAttribute("secondStepDto") SecondStepDto secondStepDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors() | !secondStepDto.getToken().equals(token.toUpperCase())) {

            return "html/registrationAndLogin/SecondStep";
        }

        userRepository.save(user);
        userDetailsRepository.save(userDetailsEntity);

        return  "redirect:/friendHub/login";
    }




    @GetMapping("/friendHub/login")
    public String login(Model model) {
        return "html/registrationAndLogin/login";
    }


}
