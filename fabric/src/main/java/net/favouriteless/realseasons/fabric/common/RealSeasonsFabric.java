package net.favouriteless.realseasons.fabric.common;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.favouriteless.realseasons.common.RealSeasons;
import net.favouriteless.realseasons.common.ServerConfig;
import net.neoforged.fml.config.ModConfig.Type;

public class RealSeasonsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        RealSeasons.init();

        NeoForgeConfigRegistry.INSTANCE.register(RealSeasons.MOD_ID, Type.SERVER, ServerConfig.SPEC, "realseasons-server.toml");
    }

}
