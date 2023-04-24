package com.example.smartgarage.services;

import com.example.smartgarage.exceptions.*;
import com.example.smartgarage.models.dtos.GenerateUserDto;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.entities.UserRoleEntity;
import com.example.smartgarage.models.enums.UserRole;
import com.example.smartgarage.models.filter_options.UserFilterOptions;
import com.example.smartgarage.models.service_models.UserServiceModel;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.repositories.UserRepository;
import com.example.smartgarage.repositories.UserRoleRepository;
import com.example.smartgarage.services.contracts.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final UserRoleRepository userRoleRepository;

    private final SmartGarageUserService smartGarageUserService;

    private final Gson gson;

    private final Resource usersFile;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                           UserRoleRepository userRoleRepository, SmartGarageUserService smartGarageUserService,
                           Gson gson, @Value("classpath:init/users.json") Resource usersFile) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.smartGarageUserService = smartGarageUserService;
        this.gson = gson;
        this.usersFile = usersFile;
    }

    @Override
    public List<UserViewModel> getAll() {
        List<User> users = userRepository.findAllUsers();
        List<UserViewModel> userViewModels = new ArrayList<>();
        users.forEach(user -> userViewModels.add(modelMapper.map(user, UserViewModel.class)));

        return userViewModels;
    }

    @Override
    public UserViewModel getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with name ", username, " was not found!"));

        return modelMapper.map(user, UserViewModel.class);
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id ", id.toString(), " was not found!"));
    }

    @Override
    public void createUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        checkPassword(userServiceModel.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.CUSTOMER).orElseThrow(
                () -> new NotFoundRoleException("CUSTOMER role not found. Please seed the roles.")
        );

        user.addRole(userRole);

        if (!userServiceModel.getPassword().equals(userServiceModel.getConfirmPassword())) {
            throw new PasswordConfirmationException("Passwords are not identical!");
        }

        alreadyExistsUser(user.getUsername(), user.getPhoneNumber(), user.getEmail());
        user = userRepository.save(user);

        UserDetails userDetails = smartGarageUserService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                user.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void checkPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        if (!password.matches(passwordPattern)) {
            throw new NotValidPasswordException("Password must be at least 8 characters long, contain at least one digit, one capital letter, and one special symbol");
        }
    }

    @Override
    public void generateUser(GenerateUserDto generateUserDto) {
        if (userRepository.findByEmail(generateUserDto.getEmail()).isPresent()){
            throw new EntityDuplicateException("User", "email", generateUserDto.getEmail());
        }
        User user = modelMapper.map(generateUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.NOT_REGISTERED_CUSTOMER).orElseThrow(
                () -> new NotFoundRoleException("NOT_REGISTERED_CUSTOMER role not found. Please seed the roles.")
        );

        user.addRole(userRole);
        user = userRepository.save(user);

    }

    @Override
    public void prepareForPasswordReset(UserServiceModel generateUser) {
        User user = userRepository.findByEmail(generateUser.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("User with email ", generateUser.getEmail(), " was not found!"));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.FORGOT_PASSWORD_CUSTOMER).orElseThrow(
                () -> new NotFoundRoleException("CUSTOMER role not found. Please seed the roles.")
        );

        user.addRole(userRole);
        user.setPassword(passwordEncoder.encode(generateUser.getPassword()));

        userRepository.save(user);

    }

    @Override
    public void updatePassword(UserServiceModel userServiceModel) {
        checkPassword(userServiceModel.getPassword());
        if (!userServiceModel.getPassword().equals(userServiceModel.getConfirmPassword())) {
            throw new PasswordConfirmationException("Password is not confirmed properly!");
        }
        User user = userRepository.findByUsername(userServiceModel.getUsername()).orElseThrow(() ->
                new EntityNotFoundException("User with username ", userServiceModel.getUsername(), " was not found!"));

        UserRoleEntity customerUserRole = userRoleRepository.findByRole(UserRole.CUSTOMER).orElseThrow(
                () -> new NotFoundRoleException("CUSTOMER role not found. Please seed the roles."));

        UserRoleEntity employeeUserRole = userRoleRepository.findByRole(UserRole.EMPLOYEE).orElseThrow(
                () -> new NotFoundRoleException("CUSTOMER role not found. Please seed the roles.")
        );
        if (user.getRoles().contains(employeeUserRole)){
            user.setRoles(new ArrayList<>(Collections.singletonList(employeeUserRole)));
        } else {
            user.setRoles(new ArrayList<>(Collections.singletonList(customerUserRole)));
        }

        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        userRepository.save(user);


    }

    @Override
    public List<UserViewModel> get(UserFilterOptions userFilterOptions) {
        Set<User> users = new HashSet<>();

        if (!userFilterOptions.getUsername().equals(Optional.of(""))){
            users.addAll(userRepository.findAllByUsername(userFilterOptions.getUsername()));
        }

        if (!userFilterOptions.getEmail().equals(Optional.of(""))){
            users.addAll(userRepository.findAllByEmail(userFilterOptions.getEmail()));
        }

        if (!userFilterOptions.getPhoneNumber().equals(Optional.of(""))){
            users.addAll(userRepository.findAllByPhoneNumber(userFilterOptions.getPhoneNumber()));
        }

        if (!userFilterOptions.getVehicleVin().equals(Optional.of(""))){
            users.addAll(userRepository.findByVehiclesVIN(userFilterOptions.getVehicleVin()));
        }



        if (users.isEmpty()){
            users.addAll(userRepository.findAllUsers());
        }

        if (!userFilterOptions.getSortBy().equals(Optional.of("None"))) {
            if (userFilterOptions.getSortBy().equals(Optional.of("Username"))) {
                if (userFilterOptions.getSortOrder().equals(Optional.of("Asc"))) {
                    users = users.stream()
                            .sorted(Comparator.comparing(User::getUsername))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                } else {
                    users = users.stream()
                            .sorted(Comparator.comparing(User::getUsername).reversed())
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                }
            } else if (userFilterOptions.getSortBy().equals(Optional.of("Visit Date"))) {
                //TODO
            }
        }

        return users.stream()
                .map(user -> modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {
            try {
                UserRoleEntity employeeRole = userRoleRepository.findByRole(UserRole.EMPLOYEE).orElseThrow();
                User[] usersEntities =
                        gson.fromJson(Files.readString(Path.of(usersFile.getURI())), User[].class);

                Arrays.stream(usersEntities)
                        .forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));

                Arrays.stream(usersEntities)
                        .forEach(u -> u.setRoles(List.of(employeeRole)));

                userRepository.saveAll(Arrays.asList(usersEntities));
            } catch (Exception e) {
                throw new IllegalStateException("Cannot seed users");
            }
        }
    }

    @Override
    public UserViewModel getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User with email ", email, " was not found!"));

        return modelMapper.map(user, UserViewModel.class);
    }

    @Override
    public UserViewModel getByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new EntityNotFoundException("User with phone number ", phoneNumber, " was not found!"));

        return modelMapper.map(user, UserViewModel.class);
    }



    @Override
    public UserViewModel updateUser(UserServiceModel updated, String username) {
        checkPassword(updated.getPassword());
        if (!updated.getPassword().equals(updated.getConfirmPassword())) {
            throw new PasswordConfirmationException("Password is not confirmed properly!");
        }

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRole.CUSTOMER).orElseThrow(
                () -> new NotFoundRoleException("CUSTOMER role not found. Please seed the roles.")
        );

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with username ", updated.getUsername(), " was not found!"));
        user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
        user.setUsername(updated.getUsername());
        user.setPassword(passwordEncoder.encode(updated.getPassword()));
        user.setPhoneNumber(updated.getPhoneNumber());
        userRepository.save(user);

        UserDetails userDetails = smartGarageUserService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                user.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return modelMapper.map(updated, UserViewModel.class);
    }

    @Override
    public UserViewModel delete(UserServiceModel existingUser) {
        User user = userRepository.findByUsername(existingUser.getUsername()).orElseThrow(() ->
                new EntityNotFoundException("User with username ", existingUser.getUsername(), " was not found!"));
        userRepository.delete(user);
        return modelMapper.map(existingUser, UserViewModel.class);
    }

    private void alreadyExistsUser(String username, String phoneNumber, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new EntityDuplicateException("User", "username", username);
        } else if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new EntityDuplicateException("User", "phone number", phoneNumber);
        } else if (userRepository.findByEmail(email).isPresent()) {
            throw new EntityDuplicateException("User", "email", email);
        }

    }

    @Override
    public List<String> checkIfExist(String username, String phoneNumber, String email) {
        List<String> exists = new ArrayList<>();

        if (userRepository.findByUsername(username).isPresent()) {
            exists.add("username");
        }
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            exists.add("phoneNumber");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            exists.add("email");
        }

        return exists;
    }
}
