package com.fashion_blog.services.implementation;


import com.fashion_blog.dtos.request.AppUserRequestDTO;
import com.fashion_blog.dtos.response.AppUserResponseDTO;
import com.fashion_blog.entity.AppUser;
import com.fashion_blog.entity.Post;
import com.fashion_blog.repository.AppUserRepository;
import com.fashion_blog.services.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Optional;



@Slf4j
@RequiredArgsConstructor
@Service
@Setter
@Getter
public class AppUserImplementation implements AppUserService {

    private final AppUserRepository appUserRepository;


    //Create
    @Override
    public AppUserResponseDTO addNewUser(AppUserRequestDTO request) {
        Optional<AppUser> userOptional = appUserRepository.findByUserName(request.getUserName());
        if (userOptional.isPresent()){
            throw new IllegalStateException("Username has been taken already, try a new one");
        }
        Optional<AppUser> userOptional1 = appUserRepository.findByEmail(request.getEmail());
        if (userOptional1.isPresent()){
            throw new IllegalStateException("The email you are providing has been used by another account");
        }

        AppUser appUser = AppUser.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();

        appUserRepository.save(appUser);

        AppUserResponseDTO response = AppUserResponseDTO.builder()
                .id(appUser.getId())
                .userName(request.getUserName())
                .email(request.getEmail())
                .build();
        return response;
    }

    @Override
    public AppUserResponseDTO loginUser(AppUserRequestDTO request, HttpServletRequest httpRequest) {
        Optional<AppUser> userOptional = appUserRepository.findByUserName(request.getUserName());
        if (userOptional.isEmpty()){
            throw new NoSuchElementException("User does not exist!!! Please if you are new sign up first");
        }
        AppUser appUser = userOptional.get();
        if (!appUser.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid password!!! Please provide a valid password");
        }
        HttpSession session = httpRequest.getSession();
        session.setAttribute("appUser", appUser);


        AppUserResponseDTO response = AppUserResponseDTO.builder()
                .userName(request.getUserName())
                .email(request.getEmail()!= null ? request.getEmail() : appUser.getEmail())
                .build();



        return response;
    }

    //Read
    @Override
    public AppUserResponseDTO getAppUser(Long userId) {
        Optional<AppUser> userOptional = appUserRepository.findById(userId);
        if (userOptional.isPresent()) {
            AppUserRequestDTO request = new AppUserRequestDTO();
            AppUser appUser = userOptional.get();
            return AppUserResponseDTO.builder()
                    .id(request.getId()!= null ? request.getId() : appUser.getId())
                    .userName(request.getUserName()!= null ? request.getUserName() : appUser.getUserName())
                    .build();
        }else {
            throw new NoSuchElementException("User with user id: "+userId+" does not exist!!!");
        }

    }


    //Update
    @Override
    public AppUserResponseDTO updateAppUser(Long userId, AppUserRequestDTO request){
        Optional<AppUser> userOptional = appUserRepository.findById(userId);

        if(userOptional.isPresent()) {
            AppUser appUser = userOptional.get();
                    appUser.setUserName(request.getUserName());
                    appUser.setEmail(request.getEmail());
                    appUser.setPassword(request.getPassword());
            appUserRepository.save(appUser);

            log.info("User details has been updated successfully");
            return AppUserResponseDTO.builder()
                    .userName(request.getUserName())
                    .email(request.getEmail())
                    .build();
        }else {
            throw new NoSuchElementException("User with user id: "+userId+" does not exist");
        }
    }


    //Delete
    public String deleteAppUser(Long userId) {
        appUserRepository.deleteById(userId);
        log.info("The app user with " + userId +" id has been deleted successfully");
        return "Application user with Id: "+userId+" has been deleted successfully";

    }
}
