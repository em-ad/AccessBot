package com.emad.sattaricoordinator.model;

import com.google.gson.annotations.SerializedName;

public class TelegramMessageResponseModel {

   @SerializedName("ok")
   boolean ok;
   @SerializedName("result")
   Object result;

   public boolean isOk() {
      return ok;
   }

   public void setOk(boolean ok) {
      this.ok = ok;
   }

   public Object getResult() {
      return result;
   }

   public void setResult(Object result) {
      this.result = result;
   }

}
