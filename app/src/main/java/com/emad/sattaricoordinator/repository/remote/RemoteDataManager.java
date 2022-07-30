package com.emad.sattaricoordinator.repository.remote;

public class RemoteDataManager {

    private static TelegramRepository telRepoInstance;

    public static TelegramRepository getTelegramRepository() {
        if (telRepoInstance == null)
            telRepoInstance = new TelegramRepository();
        return telRepoInstance;
    }

}
