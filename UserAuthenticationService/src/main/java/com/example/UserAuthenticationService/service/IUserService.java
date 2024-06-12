package com.example.UserAuthenticationService.service;


import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.exception.UserAlreadyExistsException;

public interface IUserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByUserIdAndPassword(String userId, String password) throws InvalidCredentialsException;
}
