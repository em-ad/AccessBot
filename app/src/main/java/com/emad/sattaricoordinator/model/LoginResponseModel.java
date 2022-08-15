package com.emad.sattaricoordinator.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

   @SerializedName("access_token")
   String token;
   @SerializedName("user")
   RegisterRequestModel user;

   public RegisterRequestModel getUser() {
      return user;
   }

   public void setUser(RegisterRequestModel user) {
      this.user = user;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }
}
