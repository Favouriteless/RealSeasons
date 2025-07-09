package net.favouriteless.realseasons.neoforge.client;

import net.favouriteless.realseasons.client.EnchantedClient;
import net.favouriteless.realseasons.common.RealSeasons;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = RealSeasons.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = RealSeasons.MOD_ID, value = Dist.CLIENT)
public class EnchantedClientNeo {

    public EnchantedClientNeo(IEventBus bus, ModContainer container) {
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        EnchantedClient.init();
    }

}
