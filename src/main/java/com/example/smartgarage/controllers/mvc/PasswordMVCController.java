package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.example.smartgarage.controllers.mvc.UserMVCController.generateRandomString;
import static com.example.smartgarage.controllers.mvc.UserMVCController.sendEmail;

@Controller
@RequestMapping("/password")
public class PasswordMVCController {

    private final UserService userService;

    private final ModelMapper modelMapper;
    public PasswordMVCController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @ModelAttribute("generateUserDto")
    public GenerateUserDto createGenerateUserModel() {
        return new GenerateUserDto();
    }


    @GetMapping("/forgot")
    public String getPasswordReset(){
        return "forgot_password";
    }

    @PostMapping("/forgot")
    public String sendEmailForForgottenPassword(@Valid GenerateUserDto generateUserDto, BindingResult bindingResult,
                                                Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("org.springframework.validation.BindingResult.generateUserDto", bindingResult);

            return "forgot_password";
        }

        try {
            generateUserDto.setPassword(generateRandomString(8));
            UserViewModel userViewModel = userService.getByEmail(generateUserDto.getEmail());
            generateUserDto.setUsername(userViewModel.getUsername());
            UserServiceModel userServiceModel = modelMapper.map(generateUserDto, UserServiceModel.class);
            userService.prepareForPasswordReset(userServiceModel);
            sendEmail(userServiceModel);
        } catch (EntityNotFoundException e) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("userNotFound", true);
            model.addAttribute("exceptionNotFound", e.getMessage());
            return "forgot_password";
        }


        return "redirect:/users/login";
    }
}
