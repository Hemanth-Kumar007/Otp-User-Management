package com.example.OtpUserManagement.Dto;

import lombok.Data;

@Data
public class AddUser {

    private String userName;
    private String password;
    private String emailId;
    private String phoneNumber;
}
