package net.favouriteless.realseasons.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.favouriteless.realseasons.client.EnchantedClient;
import net.favouriteless.realseasons.platform.services.FabricNetworkHelper;
import net.favouriteless.realseasons.platform.services.FabricNetworkHelper.ClientPayloadRegisterable;

public class RealSeasonsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EnchantedClient.init();
        FabricNetworkHelper.clientHandlers.forEach(ClientPayloadRegisterable::register);
    }

}
