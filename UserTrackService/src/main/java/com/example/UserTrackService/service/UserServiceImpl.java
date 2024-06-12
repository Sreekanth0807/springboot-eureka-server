package com.example.UserTrackService.service;



import com.example.UserTrackService.domain.User;
import com.example.UserTrackService.exception.UserAlreadyExistsException;
import com.example.UserTrackService.exception.UserNotFoundException;
import com.example.UserTrackService.proxy.UserProxy;
import com.example.UserTrackService.repository.UserTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserServiceImpl implements IUserService {

    private final UserProxy userProxy;
    private final UserTrackRepository userTrackRepository;

    @Autowired
    public UserServiceImpl(
            UserProxy userProxy, UserTrackRepository userTrackRepository) {
        this.userProxy = userProxy;
        this.userTrackRepository = userTrackRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if (userTrackRepository.existsById(user.getUserId())) {
            throw new UserAlreadyExistsException("User already exists with id: " + user.getUserId());
        }
        return userTrackRepository.save(user);
    }

    @Override
    public User getUserByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
        Optional<User> user = userTrackRepository.findByUserIdAndPassword(userId, password);
        if (user == null) {
            throw new UserNotFoundException("Invalid credentials for user id: " + userId);
        }
        return user.get();
    }
    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if(userTrackRepository.findById(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }
        User saveUser = userTrackRepository.save(user);
        if(!(saveUser.getEmail().isEmpty())){
            ResponseEntity r = userProxy.saveUser(user);
            System.out.println(r.getBody());
        }return saveUser;
    }
}

//@Service
//public class UserServiceImpl implements IUserService {
//
//    // Autowire the UserRepository using constructor autowiring
//    @Autowired
//    private UserProxy userProxy;
//    @Autowired
//    private UserRepository userRepository;
//
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public UserServiceImpl(UserProxy userProxy) {
//        this.userProxy = userProxy;
//    }
//
//    @Override
//    public User saveUser(User user) throws UserAlreadyExistsException {
//        if (userRepository.existsById(user.getUserId())) {
//            throw new UserAlreadyExistsException("User already exists with id: " + user.getUserId());
//        }
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User getUserByUserIdAndPassword(String userId, String password) throws InvalidCredentialsException {
//        User user = userRepository.findByUserIdAndPassword(userId, password);
//        if (user == null) {
//            throw new InvalidCredentialsException("Invalid credentials for user id: " + userId);
//        }
//        return user;
//    }
//
//}
