package com.example.smartgarage.controllers.mvc;

import com.example.smartgarage.exceptions.EntityDuplicateException;
import com.example.smartgarage.exceptions.NotFoundRoleException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.exceptions.PasswordConfirmationException;
import com.example.smartgarage.models.dtos.UserDto;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.dtos.UserFilterDto;
import com.example.smartgarage.models.filter_options.UserFilterOptions;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.repositories.CarModelRepository;
import com.example.smartgarage.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/users")
public class UserMVCController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CarModelRepository carModelRepository;

    public UserMVCController(UserService userService, ModelMapper modelMapper,
                             CarModelRepository carModelRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.carModelRepository = carModelRepository;
    }

    @ModelAttribute("userDto")
    public UserDto createUserModel() {
        return new UserDto();
    }

    @ModelAttribute("generateUserDto")
    public GenerateUserDto createGenerateUserModel() {
        return new GenerateUserDto();
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login-error")
    public ModelAndView failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                    String username) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("bad_credentials", true);
        modelAndView.addObject("username", username);

        modelAndView.setViewName("/login");

        return modelAndView;
    }

    @GetMapping("/update/{generatedUsername}")
    public String getUpdate(@PathVariable String generatedUsername, Model model) {
        model.addAttribute("generatedUsername", generatedUsername);
        return "user-update";
    }

    @PostMapping("/update/{generatedUsername}")
    public String handleUpdate(@PathVariable String generatedUsername, @Valid UserDto userDto, BindingResult bindingResult,
                                 Model model) {

        model.addAttribute("generatedUsername", generatedUsername);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            model.addAttribute("org.springframework.validation.BindingResult.userDto", bindingResult);

            return "user-update";
        }

        List<String> exists = userService.checkIfExist(userDto.getUsername(), userDto.getPhoneNumber(),
                userDto.getEmail());
        if (!exists.isEmpty()) {
            model.addAttribute("userDto", userDto);
            for (String value : exists) {
                switch (value) {
                    case "username":
                        model.addAttribute("usernameExists", true);
                        break;
                    case "phoneNumber":
                        model.addAttribute("phoneNumberExists", true);
                        break;
                    case "email":
                        model.addAttribute("emailExists", true);
                        break;
                }
            }

            return "user-update";
        }

        UserServiceModel userServiceModel = modelMapper.map(userDto, UserServiceModel.class);
        try {
            userService.updateUser(userServiceModel, generatedUsername);
        }catch (NotValidPasswordException e){
            model.addAttribute("userDto", userDto);
            model.addAttribute("invalidPassword", true);
            model.addAttribute("exceptionPassMessage", e.getMessage());
            return "user-update";
        } catch (NotFoundRoleException e){
            model.addAttribute("notFoundRoleExceptionMess", e.getMessage());
            return "user-update";
        } catch (PasswordConfirmationException e){
            model.addAttribute("userDto", userDto);
            model.addAttribute("notConfirmedPassword", true);
            model.addAttribute("confirmationExcMessage", e.getMessage());
            return "user-update";
        }

        return "redirect:/customer";
    }

    @GetMapping("/generateUser")
    public String getGenerate(){
        return "generate-user";
    }

    @PostMapping("/generateUser")
    public String handleGenerateUser(@Valid GenerateUserDto generateUserDto, BindingResult bindingResult,
                                     Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("generateUserDto", generateUserDto);
            model.addAttribute("org.springframework.validation.BindingResult.generateUserDto", bindingResult);

            return "generate-user";
        }

        generateUserDto.setUsername(generateRandomString(10));
        generateUserDto.setPassword(generateRandomString(8));

        try {
            userService.generateUser(generateUserDto);
        } catch (EntityDuplicateException e){
            model.addAttribute("emailExists", true);
            model.addAttribute("emailExistsError", e.getMessage());
            return "generate-user";
        }

        sendEmail(modelMapper.map(generateUserDto, UserServiceModel.class));

        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String getAllUsers(@ModelAttribute("filterOptions")UserFilterDto userFilterDto, Model model, Principal principal){

        UserFilterOptions userFilterOptions = new UserFilterOptions(
                userFilterDto.getUsername(),
                userFilterDto.getEmail(),
                userFilterDto.getPhoneNumber(),
                userFilterDto.getVehicleVin(),
                userFilterDto.getVehicleModel(),
                userFilterDto.getVehicleBrand(),
                userFilterDto.getVisitFirstDate(),
                userFilterDto.getVisitLastDate(),
                userFilterDto.getVisitDate(),
                userFilterDto.getSortBy(),
                userFilterDto.getSortOrder()
        );


        System.out.println(principal.getName());
        model.addAttribute("allUsers", userService.get(userFilterOptions));
        model.addAttribute("filterOptions", userFilterDto);
        return "users-all";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username){

        UserServiceModel userServiceModel = modelMapper.map(userService.getByUsername(username), UserServiceModel.class);
        userService.delete(userServiceModel);
        return "redirect:/users/all";

    }

    static void sendEmail(UserServiceModel userServiceModel) {
        String recipientUsername = userServiceModel.getUsername();
        String recipientPassword = userServiceModel.getPassword();
        String username = "iliantrenchevvvvvv@gmail.com";
        String password = "dgjgepoyqjryqeoz";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("iliantrenchevvvvvv@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userServiceModel.getEmail()));
            message.setSubject("Testing Email Sending");
            message.setText("Dear recipient,"
                    + "\n\n Here is your account info for logging in:"
                    + "\n\n Username: " + recipientUsername
                    + "\n\n Password: " + recipientPassword);

            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    static String generateRandomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
