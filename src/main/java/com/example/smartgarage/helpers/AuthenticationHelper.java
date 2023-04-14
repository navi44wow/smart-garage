package com.example.smartgarage.helpers;

import com.example.smartgarage.exceptions.AuthorizationException;
import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.services.SmartGarageUserService;
import com.example.smartgarage.services.contracts.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;


@Component
public class AuthenticationHelper {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";
    private final UserService userService;
    private final SessionFactory sessionFactory;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private final SmartGarageUserService smartGarageUserService;



    public AuthenticationHelper(UserService userService, SessionFactory sessionFactory, PasswordEncoder passwordEncoder, ModelMapper modelMapper, SmartGarageUserService smartGarageUserService) {
        this.userService = userService;
        this.sessionFactory = sessionFactory;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.smartGarageUserService = smartGarageUserService;
    }


    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        try {
            String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String username = getUsername(userInfo);
            String password = getPassword(userInfo);
            UserViewModel user = userService.getByUsername(username);

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }

            return modelMapper.map(user, User.class);
        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
        return userInfo.substring(firstSpace + 1);
    }




    public User getLoggedInUser(HttpHeaders headers) {
        Session session = sessionFactory.openSession(); // Get the session, but don't create one if it doesn't exist
        if (session != null) {
            return (User) session.getTransaction(); // Retrieve the user from the session
        } else {
            throw new AuthorizationException("You are not logged in"); // No session, no logged in user
        }
    }

    public void checkAuthorization(@RequestHeader("Authorization") HttpHeaders headers) {
        try {
            User user = tryGetUser(headers);
            UserDetails userDetails = smartGarageUserService.loadUserByUsername(user.getUsername());
            if (userDetails.getAuthorities().stream()
                    .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_EMPLOYEE"))) {
                throw new AuthorizationException("You are unauthorized!");
            }
        } catch (AuthorizationException e){
            throw new AuthorizationException(e.getMessage());
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException("User", "username", tryGetUser(headers).getUsername());
        }
    }
}