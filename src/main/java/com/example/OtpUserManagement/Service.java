package com.example.OtpUserManagement;

import com.example.OtpUserManagement.Dto.AddUser;
import com.example.OtpUserManagement.Entities.OtpEntity;
import com.example.OtpUserManagement.Entities.UserEntity;
import com.example.OtpUserManagement.Repositories.OtpRepository;
import com.example.OtpUserManagement.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    public OtpEntity sendOtp(String phoneNumber){
        String otp = generateOtp();

        // Storing OTP in the database with an expiration time
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setPhoneNumber(phoneNumber);
        otpEntity.setOtp(otp);
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);

        return otpEntity;
    }

    private static final String OTP_CHARACTERS = "0123456789"; // Define the characters allowed in the OTP
    private static final int OTP_LENGTH = 6; // Define the length of the OTP

    private String generateOtp(){
        StringBuilder otp = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(OTP_CHARACTERS.length());
            char otpDigit = OTP_CHARACTERS.charAt(index);
            otp.append(otpDigit);
        }

        return otp.toString();

    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        Optional<OtpEntity> otpEntityOptional = otpRepository.findByPhoneNumberAndOtpAndExpirationTimeAfter(
                phoneNumber, otp, LocalDateTime.now()
        );
        return otpEntityOptional.isPresent();
    }

    public void addUser(AddUser addUserDetails){
        UserEntity userEntity = new UserEntity();

        userEntity.setUserName(addUserDetails.getUserName());
        userEntity.setPassword(addUserDetails.getPassword());
        userEntity.setPhoneNumber(addUserDetails.getPhoneNumber());
        userEntity.setEmailId(addUserDetails.getEmailId());

        userRepository.save(userEntity);
    }

    public UserEntity getUser(String userName){
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(userName);
        return userEntityOptional.get();
    }
}
