package com.emad.sattaricoordinator.model;

import java.io.Serializable;
import java.util.List;

public class CreateRequestModel implements Serializable {

   private String startDate;
   private String endDate;
   private List<Employee> employees;

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

   public List<Employee> getEmployees() {
      return employees;
   }

   public void setEmployees(List<Employee> employees) {
      this.employees = employees;
   }
}
