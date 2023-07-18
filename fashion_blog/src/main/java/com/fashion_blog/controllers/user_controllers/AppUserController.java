package com.fashion_blog.controllers.user_controllers;

import com.fashion_blog.dtos.request.AppUserRequestDTO;
import com.fashion_blog.dtos.response.AppUserResponseDTO;
import com.fashion_blog.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@RequiredArgsConstructor
@RestController
@RequestMapping("/fashion-user")
public class AppUserController {

    private final AppUserService appUserService;



    @PostMapping("/register-user")
    public ResponseEntity<AppUserResponseDTO> addNewUser(@RequestBody AppUserRequestDTO request) {
        AppUserResponseDTO response = appUserService.addNewUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login-user")
    public ResponseEntity<AppUserResponseDTO> loginUser(@RequestBody AppUserRequestDTO request, HttpServletRequest httpRequest) {
        AppUserResponseDTO response = appUserService.loginUser(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<AppUserResponseDTO> getAppUser(@PathVariable Long userId) {
        AppUserResponseDTO response = appUserService.getAppUser(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<AppUserResponseDTO> updateAppUser(@PathVariable Long userId, @RequestBody AppUserRequestDTO request) {
        AppUserResponseDTO response = appUserService.updateAppUser(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteAppUser(@PathVariable Long userId) {
        String message = appUserService.deleteAppUser(userId);
        return ResponseEntity.ok(message);
    }
}
