package net.favouriteless.real_seasons.common;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {

    public static final ServerConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    public final IntValue SECONDS_PER_SEASON;

    private ServerConfig(ModConfigSpec.Builder builder) {
        SECONDS_PER_SEASON = builder.comment("The length of a FULL season (e.g. spring) in seconds").defineInRange("season_length", 86400, 90, Integer.MAX_VALUE);
    }

    static {
        Pair<ServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

}