package com.fashion_blog.dtos.response;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class AppUserResponseDTO {
    private Long id;
    private String userName;
    private String email;
}
