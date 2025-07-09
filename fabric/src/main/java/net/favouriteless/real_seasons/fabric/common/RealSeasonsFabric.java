package net.favouriteless.real_seasons.fabric.common;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.favouriteless.real_seasons.common.RealSeasons;
import net.favouriteless.real_seasons.common.ServerConfig;
import net.neoforged.fml.config.ModConfig.Type;

public class RealSeasonsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        RealSeasons.init();

        NeoForgeConfigRegistry.INSTANCE.register(RealSeasons.MOD_ID, Type.SERVER, ServerConfig.SPEC, "real_seasons-server.toml");
    }

}
