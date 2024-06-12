package com.example.UserTrackService.service;



import com.example.UserTrackService.domain.User;
import com.example.UserTrackService.exception.UserAlreadyExistsException;
import com.example.UserTrackService.exception.UserNotFoundException;


public interface IUserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByUserIdAndPassword(String userId, String password) throws UserNotFoundException;

    User registerUser(User user) throws UserAlreadyExistsException;
}
