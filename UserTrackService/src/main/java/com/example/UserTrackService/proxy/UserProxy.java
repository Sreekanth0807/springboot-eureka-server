package com.example.UserTrackService.proxy;

import com.example.UserTrackService.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name="user-authentication-service",url="localhost:8085")
public interface UserProxy {
    @PostMapping("/api/v1/user")
    public ResponseEntity<?> saveUser(@RequestBody User user);
}
