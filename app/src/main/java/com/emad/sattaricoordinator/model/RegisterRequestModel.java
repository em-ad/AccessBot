package com.emad.sattaricoordinator.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterRequestModel implements Serializable {

   @SerializedName("userId")
   private String userId;
   @SerializedName("name")
   private String name;
   @SerializedName("nationalCode")
   private String nationalCode;
   @SerializedName("phoneNumber")
   private String phoneNumber;
   @SerializedName("isAdmin")
   private boolean isAdmin;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
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

   public boolean isAdmin() {
      return isAdmin;
   }

   public void setAdmin(boolean admin) {
      isAdmin = admin;
   }
}
