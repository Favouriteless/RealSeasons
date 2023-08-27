package com.favouriteless.realseasons.common.events;

import com.favouriteless.realseasons.RealSeasons;
import com.favouriteless.realseasons.RealSeasonsConfig;
import com.favouriteless.realseasons.common.capabilities.SeasonCycleCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import sereneseasons.api.SSGameRules;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season.SubSeason;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.ServerConfig;
import sereneseasons.handler.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@EventBusSubscriber(modid=RealSeasons.MODID, bus=Bus.FORGE)
public class RealSeasonsCommonEvents {

	@SubscribeEvent
	public static void onLevelTick(LevelTickEvent event) {
		if(!event.level.isClientSide) {
			long currentSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

			event.level.getCapability(RealSeasons.SEASON_CYCLE_CAPABILITY).ifPresent(cap -> {
				long startTime = cap.getSeasonStartTime();

				if(startTime == -1) {
					cap.setSeasonStartTime(currentSeconds);
					cap.setStartingSeason(SubSeason.EARLY_SPRING);
				}

				ISeasonState seasonState = SeasonHelper.getSeasonState(event.level);

				startTime = cap.getSeasonStartTime();
				long timeSinceStart = currentSeconds - startTime;

				int secondsInCycle = RealSeasonsConfig.SECONDS_PER_SEASON.get() * 4; // Real seconds in RealSeasons cycle.
				double secondsInSubseason = RealSeasonsConfig.SECONDS_PER_SEASON.get() / 3.0D; // Real seconds in RealSeasons cycle.
				int ticksInCycle = seasonState.getCycleDuration(); // Ticks in SereneSeasons cycle.

				double secondsThroughCycle = ((timeSinceStart + secondsInSubseason * cap.getStartingSeasonOffset()) % secondsInCycle); // How many seconds the game is through the current cycle.
				double cyclePercent = secondsThroughCycle / secondsInCycle;

				int desiredCycleTick = (int)Math.round(ticksInCycle * cyclePercent);

				SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(event.level);
				seasonData.seasonCycleTicks = desiredCycleTick;
				seasonData.setDirty();

				if(event.level.getGameTime() % 20 == 0)
					SeasonHandler.sendSeasonUpdate(event.level);
			});
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesLevel(@Nonnull final AttachCapabilitiesEvent<Level> event) {
		Level level = event.getObject();

		if(!level.isClientSide)
			event.addCapability(new ResourceLocation(RealSeasons.MODID, "season_cycle"), new SeasonCycleCapabilityProvider());
	}

}
