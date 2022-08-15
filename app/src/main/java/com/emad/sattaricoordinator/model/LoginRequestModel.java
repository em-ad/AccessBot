package com.emad.sattaricoordinator.model;

public class LoginRequestModel {

   private String nationalCode;
   private String phoneNumber;

   public LoginRequestModel(String nationalCode, String phoneNumber) {
      this.nationalCode = nationalCode;
      this.phoneNumber = phoneNumber;
   }

   public String getNationalCode() {
      return nationalCode;
   }

   public void setNationalCode(String nationalCode) {
      this.nationalCode = nationalCode;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }
}
