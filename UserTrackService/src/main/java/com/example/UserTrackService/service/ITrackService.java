package com.example.UserTrackService.service;


import com.example.UserTrackService.domain.Track;
import com.example.UserTrackService.domain.User;
import com.example.UserTrackService.exception.TrackAlreadyExistsException;
import com.example.UserTrackService.exception.TrackNotFoundException;
import com.example.UserTrackService.exception.UserAlreadyExistsException;
import com.example.UserTrackService.exception.UserNotFoundException;

import java.util.List;

public interface ITrackService {

    User registerUser(User user) throws UserAlreadyExistsException;
    User saveUserTrackToWishList(Track track, String userId) throws TrackAlreadyExistsException, UserNotFoundException;
    List<Track> getAllUserTracksFromWishList(String userId) throws Exception;
    User deleteTrack(String userId,String trackId) throws TrackNotFoundException, UserNotFoundException;
    User updateUserTrackWishListWithGivenTrack(String userId,Track track) throws UserNotFoundException, TrackNotFoundException, TrackAlreadyExistsException;
}
