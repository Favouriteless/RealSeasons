package net.favouriteless.realseasons.platform;

import net.favouriteless.realseasons.common.RealSeasons;
import net.favouriteless.realseasons.platform.services.NetworkHelper;

import java.util.ServiceLoader;

public class CommonServices {

    public static final NetworkHelper NETWORK = load(NetworkHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        RealSeasons.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

}