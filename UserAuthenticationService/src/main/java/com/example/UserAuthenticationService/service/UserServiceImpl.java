package com.example.UserAuthenticationService.service;


import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.repository.UserRepository;
import com.example.UserAuthenticationService.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    // Autowire the UserRepository using constructor autowiring

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.existsById(user.getUserId())) {
            throw new UserAlreadyExistsException("User already exists with id: " + user.getUserId());
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByUserIdAndPassword(String userId, String password) throws InvalidCredentialsException {
        User user = userRepository.findByUserIdAndPassword(userId, password);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid credentials for user id: " + userId);
        }
        return user;
    }

}
