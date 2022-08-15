package com.emad.sattaricoordinator.repository.remote;

public class RemoteDataManager {

    private static TelegramRepository telRepoInstance;

    public static TelegramRepository getTelegramRepository() {
        if (telRepoInstance == null)
            telRepoInstance = new TelegramRepository();
        return telRepoInstance;
    }

    private static KheyratiRepository kheyratiRepository;

    public static KheyratiRepository getKheyratiRepository() {
        if (kheyratiRepository == null)
            kheyratiRepository = new KheyratiRepository();
        return kheyratiRepository;
    }

}
