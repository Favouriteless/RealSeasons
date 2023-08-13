package com.favouriteless.realseasons.common.events;

import com.favouriteless.realseasons.RealSeasons;
import com.favouriteless.realseasons.RealSeasonsConfig;
import com.favouriteless.realseasons.common.capabilities.SeasonCycleCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import sereneseasons.api.SSGameRules;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season.SubSeason;
import sereneseasons.api.season.SeasonHelper;
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
			if(event.level.dimension() == Level.OVERWORLD)
				event.level.getGameRules().getRule(SSGameRules.RULE_DOSEASONCYCLE).set(false, null); // Force doSeasonCycle to false.

			long currentSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

			event.level.getCapability(RealSeasons.SEASON_CYCLE_CAPABILITY).ifPresent(cap -> {
				long startTime = cap.getSeasonStartTime();

				if(startTime == -1) {
					cap.setSeasonStartTime(currentSeconds);
					cap.setStartingSeason(SubSeason.EARLY_SPRING);
				}

				startTime = cap.getSeasonStartTime();

				long timeDiff = currentSeconds - startTime;
				double secondsPerSubseason = RealSeasonsConfig.SECONDS_PER_SEASON.get() / 3.0D;
				double subseasonsSinceStart = timeDiff / secondsPerSubseason;

				ISeasonState seasonState = SeasonHelper.getSeasonState(event.level);
				SubSeason currentSubseason = seasonState.getSubSeason();
				SubSeason desiredSubseason = SubSeason.values()[((int)Math.floor(subseasonsSinceStart) + cap.getStartingSeasonOffset()) % SubSeason.values().length];

				if(currentSubseason != desiredSubseason) {
					SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(event.level);
					seasonData.seasonCycleTicks = SeasonTime.ZERO.getSubSeasonDuration() * desiredSubseason.ordinal();
					seasonData.setDirty();
					SeasonHandler.sendSeasonUpdate(event.level);
				}

			});
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesLevel(@Nonnull final AttachCapabilitiesEvent<Level> event) {
		Level level = event.getObject();

		if(!level.isClientSide && level.dimension() == Level.OVERWORLD)
			event.addCapability(new ResourceLocation(RealSeasons.MODID, "season_cycle"), new SeasonCycleCapabilityProvider());
	}

}
