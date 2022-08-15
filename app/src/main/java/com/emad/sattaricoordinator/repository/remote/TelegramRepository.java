package com.emad.sattaricoordinator.repository.remote;

import com.emad.sattaricoordinator.model.TelegramMessageResponseModel;
import com.emad.sattaricoordinator.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelegramRepository {

    public void sendMessage(String text, ApiCallback apiCallback) {
        RetrofitClient.getInstance().getTelegramApi()
                .sendMessageToChat(Constants.EMAD_CHAT_ID, text)
                .enqueue(new Callback<TelegramMessageResponseModel>() {
                    @Override
                    public void onResponse(Call<TelegramMessageResponseModel> call, Response<TelegramMessageResponseModel> response) {
                        if(response.isSuccessful()){
                            apiCallback.apiSucceeded(null);
                        } else
                            apiCallback.apiFailed(null);
                    }

                    @Override
                    public void onFailure(Call<TelegramMessageResponseModel> call, Throwable t) {
                        apiCallback.apiFailed(null);
                    }
                });
    }
}
