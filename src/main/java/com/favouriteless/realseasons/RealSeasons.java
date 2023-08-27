package com.favouriteless.realseasons;

import com.favouriteless.realseasons.api.capabilities.ISeasonCycleCapability;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(RealSeasons.MODID)
public class RealSeasons {

    public static final String MODID = "realseasons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static Capability<ISeasonCycleCapability> SEASON_CYCLE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public RealSeasons() {
        ModLoadingContext.get().registerConfig(Type.SERVER, RealSeasonsConfig.SPEC);
    }

}
