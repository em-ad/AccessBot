package com.emad.sattaricoordinator.model;

import java.io.Serializable;
import java.util.List;

public class ChangeStatusModel implements Serializable {

   String requestId;
   String status;

   public String getRequestId() {
      return requestId;
   }

   public void setRequestId(String requestId) {
      this.requestId = requestId;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }
}
