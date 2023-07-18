package com.fashion_blog.dtos.request;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserRequestDTO {
    @Id
    private Long id;
    private String userName;
    private String email;
    private String password;
}
