package com.fashion_blog.repository;

import com.fashion_blog.entity.AppUser;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Optional;
@Repository
@Transactional
public interface AppUserRepository extends JpaRepository<AppUser, Long> {



    Optional<AppUser> findByUserName(String userName);


    Optional<AppUser> findByEmail(String email);
}
