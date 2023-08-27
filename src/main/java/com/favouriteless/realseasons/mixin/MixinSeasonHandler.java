package com.favouriteless.realseasons.mixin;

import net.minecraftforge.event.TickEvent.LevelTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import sereneseasons.handler.season.SeasonHandler;

@Mixin(SeasonHandler.class)
public class MixinSeasonHandler {

	/**
	 * @author Favouriteless
	 * @reason RealSeasons add-on runs on it's own season cycle, needs to ignore all default Serene Seasons Logic.
	 */
	@Overwrite(remap = false)
	public void onWorldTick(LevelTickEvent event) {

	}

}
