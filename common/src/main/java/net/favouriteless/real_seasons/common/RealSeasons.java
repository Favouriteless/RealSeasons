package net.favouriteless.real_seasons.common;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealSeasons {

    public static final String MOD_ID = "real_seasons";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}