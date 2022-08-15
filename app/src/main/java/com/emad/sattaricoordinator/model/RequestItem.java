package com.emad.sattaricoordinator.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RequestItem implements Serializable {

   @SerializedName("_id")
   String id;
   @SerializedName("startDate")
   String startDate;
   @SerializedName("endDate")
   String endDate;
   @SerializedName("status")
   String status;
   @SerializedName("employees")
   List<Employee> employees;

   String ownerName;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getOwnerName() {
      return ownerName;
   }

   public void setOwnerName(String ownerName) {
      this.ownerName = ownerName;
   }

   public String getStartDate() {
      return startDate;
   }

   public void setStartDate(String startDate) {
      this.startDate = startDate;
   }

   public String getEndDate() {
      return endDate;
   }

   public void setEndDate(String endDate) {
      this.endDate = endDate;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public List<Employee> getEmployees() {
      return employees;
   }

   public void setEmployees(List<Employee> employees) {
      this.employees = employees;
   }
}
