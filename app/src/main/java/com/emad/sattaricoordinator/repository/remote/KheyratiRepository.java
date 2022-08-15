package com.emad.sattaricoordinator.repository.remote;

import com.emad.sattaricoordinator.model.ChangeStatusModel;
import com.emad.sattaricoordinator.model.CreateRequestModel;
import com.emad.sattaricoordinator.model.LoginResponseModel;
import com.emad.sattaricoordinator.model.LoginRequestModel;
import com.emad.sattaricoordinator.model.RequestResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KheyratiRepository {

    public void login(String phone, String nid, ApiCallback apiCallback) {
        RetrofitClient.getInstance().getKheyratiApi()
                .login(new LoginRequestModel(nid, phone))
                .enqueue(new Callback<LoginResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            apiCallback.apiSucceeded(response.body());
                        } else
                            apiCallback.apiFailed(new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                        apiCallback.apiFailed(t);
                    }
                });
    }

    public void getRequests(String token, ApiCallback apiCallback) {
        RetrofitClient.getInstance().getKheyratiApi()
                .getRequests("Bearer " + token)
                .enqueue(new Callback<List<RequestResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<RequestResponseModel>> call, Response<List<RequestResponseModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            apiCallback.apiSucceeded(response.body());
                        } else
                            apiCallback.apiFailed(new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<List<RequestResponseModel>> call, Throwable t) {
                        apiCallback.apiFailed(t);
                    }
                });
    }

    public void createRequest(String token, CreateRequestModel requestModel, ApiCallback apiCallback){
        RetrofitClient.getInstance().getKheyratiApi()
                .createRequest(token, requestModel)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            apiCallback.apiSucceeded(response.body());
                        } else
                            apiCallback.apiFailed(new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        apiCallback.apiFailed(t);
                    }
                });
    }

    public void changeRequestStatus(String token, ChangeStatusModel requestModel, ApiCallback apiCallback){
        RetrofitClient.getInstance().getKheyratiApi()
                .changeRequestStatus(token, requestModel)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            apiCallback.apiSucceeded(response.body());
                        } else
                            apiCallback.apiFailed(new Throwable(response.message()));
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        apiCallback.apiFailed(t);
                    }
                });
    }
}
