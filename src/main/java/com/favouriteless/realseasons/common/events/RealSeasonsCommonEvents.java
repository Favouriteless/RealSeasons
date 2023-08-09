package com.favouriteless.realseasons.common.events;

import com.favouriteless.realseasons.RealSeasons;
import com.favouriteless.realseasons.common.capabilities.SeasonCycleCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import javax.annotation.Nonnull;

@EventBusSubscriber(modid=RealSeasons.MODID, bus=Bus.FORGE)
public class RealSeasonsCommonEvents {

	@SubscribeEvent
	public static void onLevelTick(LevelTickEvent event) {
		if(!event.level.isClientSide) {
			if(event.level.dimension() == Level.OVERWORLD) {
				// Only run event on overworld ticks, server side.

				event.level.getCapability(RealSeasons.SEASON_CYCLE_CAPABILITY).ifPresent(cap -> {

				});
			}
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesLevel(@Nonnull final AttachCapabilitiesEvent<Level> event) {
		Level level = event.getObject();

		if(!level.isClientSide && level.dimension() == Level.OVERWORLD)
			event.addCapability(new ResourceLocation(RealSeasons.MODID, "season_cycle"), new SeasonCycleCapabilityProvider());
	}

}
