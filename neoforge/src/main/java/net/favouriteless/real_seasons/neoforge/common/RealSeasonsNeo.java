package net.favouriteless.real_seasons.neoforge.common;

import net.favouriteless.real_seasons.common.RealSeasons;
import net.favouriteless.real_seasons.common.ServerConfig;
import net.favouriteless.real_seasons.platform.services.NeoNetworkHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;

@Mod(RealSeasons.MOD_ID)
public class RealSeasonsNeo {
    
    public RealSeasonsNeo(IEventBus bus, ModContainer container) {
        RealSeasons.init();
        container.registerConfig(Type.SERVER, ServerConfig.SPEC, "real_seasons-server.toml");

        bus.addListener(NeoNetworkHelper::registerPayloads);
    }

}