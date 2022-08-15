package com.emad.sattaricoordinator.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {

   @SerializedName("name")
   private String name;
   @SerializedName("nationalCode")
   private String nid;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getNid() {
      return nid;
   }

   public void setNid(String nid) {
      this.nid = nid;
   }
}
