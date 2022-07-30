package com.emad.sattaricoordinator.repository.remote;

import static com.emad.sattaricoordinator.utils.Constants.TELEGRAM_BASE_URL;

import com.emad.sattaricoordinator.utils.Constants;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final TelegramApi telegramApi;

    private RetrofitClient() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(createInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TELEGRAM_BASE_URL)
                .addConverterFactory(createGsonFactory())
                .client(httpClient)
                .build();

        telegramApi = retrofit.create(TelegramApi.class);
    }

    private Converter.Factory createGsonFactory() {
        return GsonConverterFactory.create(new GsonBuilder().setLenient().create());
    }

    private Interceptor createInterceptor() {
        return chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.header(Constants.API_CONTENT_TYPE, Constants.API_CONTENT_TYPE_VALUE);
            return chain.proceed(requestBuilder.build());
        };
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public TelegramApi getTelegramApi() {
        return telegramApi;
    }
}
