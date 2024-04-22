package com.application.friendhub.controller;

import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.registrationProcess.FirstStepDto;
import com.application.friendhub.fronted.SelectOptionService;
import com.application.friendhub.registrationProcess.FirstStepDtoService;
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

@Controller
public class RegistrationController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final SelectOptionService selectOptionService;
    private final FirstStepDtoService firstStepDtoService;
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    public RegistrationController(SelectOptionService selectOptionService, FirstStepDtoService firstStepDtoService, UserDetailsRepository userDetailsRepository, UserRepository userRepository) {
        this.selectOptionService = selectOptionService;
        this.firstStepDtoService = firstStepDtoService;
        this.userDetailsRepository = userDetailsRepository;
        this.userRepository = userRepository;
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
    ) {

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
        UserEntity user = firstStepDtoService.convertFirstStepDtoToUserEntity(firstStepDto);
        UserEntity userEntity = userRepository.save(user);
        UserDetailsEntity userDetailsEntity = firstStepDtoService.convertFirstStepDtoToUserDetailsEntity(firstStepDto,userEntity);
        userDetailsRepository.save(userDetailsEntity);


        return "redirect:/register/friendHub/secondStep";
    }


    @GetMapping("/register/friendHub/secondStep")
    public String secondStep() {


        return "html/registrationAndLogin/SecondStep";
    }


    @GetMapping("/friendHub/login")
    public String login(Model model) {
        return "html/registrationAndLogin/login";
    }


}
