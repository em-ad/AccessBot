package com.emad.sattaricoordinator.repository.remote;

public interface ApiCallback {
   void apiFailed(Object o);
   void apiSucceeded(Object o);
}