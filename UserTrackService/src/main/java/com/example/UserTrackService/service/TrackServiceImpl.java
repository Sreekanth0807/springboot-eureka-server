package com.example.UserTrackService.service;


import com.example.UserTrackService.domain.Track;
import com.example.UserTrackService.domain.User;
import com.example.UserTrackService.exception.TrackAlreadyExistsException;
import com.example.UserTrackService.exception.TrackNotFoundException;
import com.example.UserTrackService.exception.UserAlreadyExistsException;
import com.example.UserTrackService.exception.UserNotFoundException;
import com.example.UserTrackService.repository.UserTrackRepository;
import com.example.UserTrackService.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements ITrackService {

    // Autowire the UserTrackRepository using constructor autowiring
    @Autowired
    private final UserTrackRepository userTrackRepository;

    public TrackServiceImpl(UserTrackRepository userTrackRepository) {
        this.userTrackRepository = userTrackRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (userTrackRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("user already exists");
        }
        return userTrackRepository.save(user);
    }

    @Override
    public User saveUserTrackToWishList(Track track, String userId)
            throws TrackAlreadyExistsException, UserNotFoundException {
        Optional<User> userOptional = userTrackRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("Track already exists");
        }

        User user = userOptional.get();
        List<Track> tracks = user.getTrackList();
        if (tracks.stream().anyMatch(t -> t.getTrackId().equals(track.getTrackId()))) {
            throw new TrackAlreadyExistsException();
        }

        tracks.add(track);
        user.setTrackList(tracks);
        return userTrackRepository.save(user);
    }

    @Override
    public List<Track> getAllUserTracksFromWishList(String userId) throws UserNotFoundException {
        Optional<User> userOptional = userTrackRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("user not found");
        }

        return userOptional.get().getTrackList();
    }

    @Override
    public User deleteTrack(String userId, String trackId) throws TrackNotFoundException, UserNotFoundException {
        Optional<User> userOptional = userTrackRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new TrackNotFoundException();
        }

        User user = userOptional.get();
        List<Track> tracks = user.getTrackList();
        Track trackToRemove = tracks.stream().filter(t -> t.getTrackId().equals(trackId)).findFirst()
                .orElseThrow(TrackNotFoundException::new);

        tracks.remove(trackToRemove);
        user.setTrackList(tracks);
        return userTrackRepository.save(user);
    }

    @Override
    public User updateUserTrackWishListWithGivenTrack(String userId, Track track)
            throws UserNotFoundException, TrackNotFoundException, TrackAlreadyExistsException {
        Optional<User> userOptional = userTrackRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("user not found");
        }

        User user = userOptional.get();
        List<Track> tracks = user.getTrackList();
        Track existingTrack = tracks.stream().filter(t -> t.getTrackId().equals(track.getTrackId())).findFirst()
                .orElseThrow(TrackNotFoundException::new);

        if (!existingTrack.equals(track)) {
            if (tracks.stream().anyMatch(t -> t.getTrackId().equals(track.getTrackId()) && !t.equals(existingTrack))) {
                throw new TrackAlreadyExistsException();
            }
        }

        tracks.remove(existingTrack);
        tracks.add(track);
        user.setTrackList(tracks);
        return userTrackRepository.save(user);
    }
}