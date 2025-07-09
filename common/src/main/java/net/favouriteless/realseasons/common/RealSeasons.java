package net.favouriteless.realseasons.common;

import glitchcore.event.EventManager;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealSeasons {

    public static final String MOD_ID = "realseasons";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        EventManager.addListener(SetSeasonCycleCommand::register);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}