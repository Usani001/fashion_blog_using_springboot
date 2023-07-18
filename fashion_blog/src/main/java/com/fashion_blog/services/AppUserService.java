package com.fashion_blog.services;

import com.fashion_blog.dtos.request.AppUserRequestDTO;
import com.fashion_blog.dtos.response.AppUserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface AppUserService {
    public AppUserResponseDTO addNewUser( AppUserRequestDTO request);

    public AppUserResponseDTO updateAppUser(Long userId, AppUserRequestDTO request);

    public AppUserResponseDTO getAppUser(Long userId);

    public AppUserResponseDTO loginUser(AppUserRequestDTO request, HttpServletRequest httpRequest);

    public String deleteAppUser(Long userId);
}
