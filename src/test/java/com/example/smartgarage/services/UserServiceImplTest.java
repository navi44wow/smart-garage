package com.example.smartgarage.services;


import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.exceptions.NotValidPasswordException;
import com.example.smartgarage.models.entities.User;
import com.example.smartgarage.models.view_models.UserViewModel;
import com.example.smartgarage.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.example.smartgarage.Helpers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    ModelMapper modelMapper;

    @Test
    void getAll_ShouldReturn_List_Of_Users(){
        List<User> mockList = new ArrayList<>();
        mockList.add(createMockUser());

        Mockito.when(userRepository.findAllUsers()).thenReturn(mockList);
        List<UserViewModel> result = userService.getAll();

        assertEquals(mockList.size(), result.size());

        Mockito.verify(userRepository, Mockito.times(1)).findAllUsers();
    }


    @Test
    void getByUsername_Should_Return_User(){
        User mockUser = createMockUser();

        Mockito.when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        UserViewModel result = userService.getByUsername(mockUser.getUsername());
        assertEquals(modelMapper.map(mockUser, UserViewModel.class), result);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(mockUser.getUsername());

    }

    @Test
    void getByUsername_Should_Throw_When_User_Does_Not_Exist(){
        User mockUser = createMockUser();

        assertThrows(EntityNotFoundException.class, () -> userService.getByUsername(mockUser.getUsername()));

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(mockUser.getUsername());
    }

    @Test
    void getById_Should_Return_User(){
        User mockUser = createMockUser();

        Mockito.when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        User result = userService.getById(mockUser.getId());
        assertEquals(mockUser, result);

        Mockito.verify(userRepository, Mockito.times(1)).findById(mockUser.getId());

    }

    @Test
    void getById_Should_Throw_When_User_Does_Not_Exist(){
        User mockUser = createMockUser();

        assertThrows(EntityNotFoundException.class, () -> userService.getById(mockUser.getId()));

        Mockito.verify(userRepository, Mockito.times(1)).findById(mockUser.getId());

    }

    @Test
    void getByEmail_Should_Return_User(){
        User mockUser = createMockUser();

        Mockito.when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        UserViewModel result = userService.getByEmail(mockUser.getEmail());
        assertEquals(modelMapper.map(mockUser, UserViewModel.class), result);

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(mockUser.getEmail());

    }

    @Test
    void getByEmail_Should_Throw_When_User_Does_Not_Exist(){
        User mockUser = createMockUser();

        assertThrows(EntityNotFoundException.class, () -> userService.getByEmail(mockUser.getEmail()));

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(mockUser.getEmail());

    }

    @Test
    void getByPhoneNumber_Should_Return_User(){
        User mockUser = createMockUser();

        Mockito.when(userRepository.findByPhoneNumber(mockUser.getPhoneNumber())).thenReturn(Optional.of(mockUser));
        UserViewModel result = userService.getByPhoneNumber(mockUser.getPhoneNumber());
        assertEquals(modelMapper.map(mockUser, UserViewModel.class), result);

        Mockito.verify(userRepository, Mockito.times(1))
                .findByPhoneNumber(mockUser.getPhoneNumber());

    }

    @Test
    void getByPhoneNumber_Should_Throw_When_User_Does_Not_Exist(){
        User mockUser = createMockUser();

        assertThrows(EntityNotFoundException.class, () -> userService.getByPhoneNumber(mockUser.getPhoneNumber()));

        Mockito.verify(userRepository, Mockito.times(1)).findByPhoneNumber(mockUser.getPhoneNumber());

    }

    @Test
    void checkPassword_Should_Throw(){
        String password = "Invalid";

        assertThrows(NotValidPasswordException.class, () -> userService.checkPassword(password));
    }
}