package com.emad.sattaricoordinator.repository.remote;

import com.emad.sattaricoordinator.model.ChangeStatusModel;
import com.emad.sattaricoordinator.model.CreateRequestModel;
import com.emad.sattaricoordinator.model.LoginResponseModel;
import com.emad.sattaricoordinator.model.LoginRequestModel;
import com.emad.sattaricoordinator.model.RegisterRequestModel;
import com.emad.sattaricoordinator.model.RequestItem;
import com.emad.sattaricoordinator.model.RequestResponseModel;
import com.emad.sattaricoordinator.model.TelegramMessageResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface KheyratiApi {

    @POST("user/register")
    Call<TelegramMessageResponseModel> register(
            @Header("Authorization") String authHeader,
            @Body RegisterRequestModel requestModel
    );

    @POST("user/login")
    Call<LoginResponseModel> login(@Body LoginRequestModel requestModel);

    @GET("/license/myRequests")
    Call<List<RequestItem>> getMyRequests(@Header("Authorization") String authHeader);

    @GET("/license/requests")
    Call<List<RequestResponseModel>> getRequests(@Header("Authorization") String authHeader);

    @POST("/license/create")
    Call<Void> createRequest(@Header("Authorization") String authHeader,
                             @Body CreateRequestModel createRequestModel);

    @POST("/license/changeRequestStatus")
    Call<Void> changeRequestStatus(@Header("Authorization") String authHeader,
                             @Body ChangeStatusModel model);
}
