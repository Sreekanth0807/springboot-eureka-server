package com.example.UserTrackService.controller;


import com.example.UserTrackService.domain.Track;
import com.example.UserTrackService.domain.User;
import com.example.UserTrackService.exception.TrackAlreadyExistsException;
import com.example.UserTrackService.exception.TrackNotFoundException;
import com.example.UserTrackService.exception.UserAlreadyExistsException;
import com.example.UserTrackService.exception.UserNotFoundException;
import com.example.UserTrackService.service.TrackServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2")
public class TrackController {

    @Autowired
    private TrackServiceImpl trackService;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = trackService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("user/track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            User updatedUser = trackService.saveUserTrackToWishList(track, userId);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        } catch (TrackAlreadyExistsException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("user/tracks")
    public ResponseEntity<?> getAllTracks(HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            List<Track> tracks = trackService.getAllUserTracksFromWishList(userId);
            return new ResponseEntity<>(tracks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("user/track/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable String trackId, HttpServletRequest request)
            throws TrackNotFoundException {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            User updatedUser = trackService.deleteTrack(userId, trackId);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (TrackNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("user/track")
    public ResponseEntity<?> updateTrack(@RequestBody Track track, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            User updatedUser = trackService.updateUserTrackWishListWithGivenTrack(userId, track);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException | TrackNotFoundException | TrackAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}


