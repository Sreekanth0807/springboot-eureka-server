package com.example.UserTrackService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Use the@ResponseStatus annotation to set the exception message and status
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Track not found")
public class TrackNotFoundException extends Exception {
}
