package com.example.OtpUserManagement;

import com.example.OtpUserManagement.Dto.AddUser;
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
        try {
            serviceObj.sendOtp(phoneNumber);
            return new ResponseEntity("OTP sent successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to send OTP", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        if (serviceObj.verifyOtp(phoneNumber, otp)) {
            return new ResponseEntity("OTP is valid", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Invalid OTP", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/addUserDetails")
    public ResponseEntity addUser(@RequestBody AddUser addUserBody){
        try{
            serviceObj.addUser(addUserBody);
            return new ResponseEntity("User is added", HttpStatus.OK);
        }
       catch (Exception e){
            return new ResponseEntity("User not added", HttpStatus.BAD_REQUEST);
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
