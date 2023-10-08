package com.example.OtpUserManagement;

import com.example.OtpUserManagement.Dto.AddUser;
import com.example.OtpUserManagement.Entities.OtpEntity;
import com.example.OtpUserManagement.Entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private Service serviceObj;

    @PostMapping("/sendOtp")
    public ResponseEntity sendOtp(@RequestParam String phoneNumber){
            OtpEntity otpEntity = serviceObj.sendOtp(phoneNumber);
            return new ResponseEntity("Otp is "+otpEntity.getOtp()+"\n"+"Expiration Time is "+otpEntity.getExpirationTime()
                    , HttpStatus.OK);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        if (serviceObj.verifyOtp(phoneNumber, otp)) {
            return new ResponseEntity("OTP is valid" , HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Invalid OTP", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/addUserDetails")
    public ResponseEntity<String> addUser(@RequestBody AddUser addUserBody){
        try{
            serviceObj.addUser(addUserBody);
            return ResponseEntity.ok("User is added");
        }
       catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not added");
        }
    }

    @GetMapping("/getUserDetails")
    public ResponseEntity getUser(@RequestParam String userName){

        UserEntity getUserDetails = serviceObj.getUser(userName);
        if(getUserDetails != null){
            return new ResponseEntity(getUserDetails, HttpStatus.OK);
        }
        else{
            return new ResponseEntity("UserName is not valid", HttpStatus.BAD_REQUEST);
        }
    }

}
