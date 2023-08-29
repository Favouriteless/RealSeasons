package com.favouriteless.realseasons;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid=RealSeasons.MOD_ID, bus=Bus.MOD)
public class RealSeasonsConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ConfigValue<Integer> SECONDS_PER_SEASON;
    public static final ConfigValue<Boolean> START_MIDNIGHT;
    public static final ConfigValue<Boolean> START_UTC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        SECONDS_PER_SEASON = builder.comment("Duration of a season (in seconds) #default 86400 (1 day)").define("season_duration", 86400);
        START_MIDNIGHT = builder.comment("Always start the season cycle of a world at 00:00 (midnight, in local time) #default true").define("start_midnight", true);
        START_UTC = builder.comment("(*Only active if start_midnight is true) when starting midnight, should the timezone be UTC (true) or local (false) #default false").define("start_utc", false);
        SPEC = builder.build();
    }

}
