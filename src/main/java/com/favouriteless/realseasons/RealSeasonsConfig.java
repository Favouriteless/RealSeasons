package com.favouriteless.realseasons;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.time.LocalDateTime;

@EventBusSubscriber(modid=RealSeasons.MODID, bus=Bus.MOD)
public class RealSeasonsConfig {

    public static final ForgeConfigSpec SPEC;

    private static final ConfigValue<Integer> SECONDS_PER_SEASON;


    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        SECONDS_PER_SEASON = builder.comment("#Duration of a season (in seconds) #default 86400 (1 day)").define("season_duration", 86400);

        SPEC = builder.build();
    }

}
