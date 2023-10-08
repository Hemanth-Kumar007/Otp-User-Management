package com.example.OtpUserManagement.Repositories;

import com.example.OtpUserManagement.Entities.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Integer> {
    Optional<OtpEntity> findByPhoneNumberAndOtpAndExpirationTimeAfter(String phoneNumber, String otp, LocalDateTime expirationTime);
}
