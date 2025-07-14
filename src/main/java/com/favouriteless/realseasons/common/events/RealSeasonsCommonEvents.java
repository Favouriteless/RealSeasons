package com.favouriteless.realseasons.common.events;

import com.favouriteless.realseasons.RealSeasons;
import com.favouriteless.realseasons.common.capabilities.SeasonCycleCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import javax.annotation.Nonnull;

@EventBusSubscriber(modid=RealSeasons.MOD_ID, bus=Bus.FORGE)
public class RealSeasonsCommonEvents {

	@SubscribeEvent
	public static void onAttachCapabilitiesLevel(@Nonnull final AttachCapabilitiesEvent<Level> event) {
		Level level = event.getObject();

		if(!level.isClientSide)
			event.addCapability(new ResourceLocation(RealSeasons.MOD_ID, "season_cycle"), new SeasonCycleCapabilityProvider());
	}

}
