package net.favouriteless.real_seasons.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.favouriteless.real_seasons.client.EnchantedClient;
import net.favouriteless.real_seasons.platform.services.FabricNetworkHelper;
import net.favouriteless.real_seasons.platform.services.FabricNetworkHelper.ClientPayloadRegisterable;

public class RealSeasonsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EnchantedClient.init();
        FabricNetworkHelper.clientHandlers.forEach(ClientPayloadRegisterable::register);
    }

}
