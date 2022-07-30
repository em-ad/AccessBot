package com.emad.sattaricoordinator.repository.remote;


import com.emad.sattaricoordinator.model.TelegramMessageResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TelegramApi {

    @GET("sendMessage")
    Call<TelegramMessageResponseModel> sendMessageToChat(
            @Query("chat_id") String channelId,
            @Query("text") String message
    );

}
