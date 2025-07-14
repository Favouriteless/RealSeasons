package com.favouriteless.realseasons.mixin;

import com.favouriteless.realseasons.RealSeasons;
import com.favouriteless.realseasons.RealSeasonsConfig;
import com.favouriteless.realseasons.api.capabilities.ISeasonCycleCapability;
import glitchcore.event.TickEvent;
import glitchcore.event.TickEvent.Phase;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import sereneseasons.api.SSGameRules;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.Season.SubSeason;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModConfig;
import sereneseasons.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mixin(SeasonHandler.class)
public class MixinSeasonHandler {

	@Shadow
	public static SeasonSavedData getSeasonSavedData(Level w) {
		return null;
	}

	/**
	 * @author Favouriteless
	 * @reason RealSeasons add-on runs on it's own season cycle, needs to ignore all default Serene Seasons Logic.
	 */
	@Overwrite(remap = false)
	public static void onLevelTick(TickEvent.Level event) {
		Level l = event.getLevel();

		if(event.getPhase() != Phase.START || l.isClientSide || !ModConfig.seasons.isDimensionWhitelisted(l.dimension()))
			return;
		if(!l.getGameRules().getBoolean(SSGameRules.RULE_DOSEASONCYCLE))
			return;

		ServerLevel level = (ServerLevel)l;


		long currentSeconds = OffsetDateTime.now().toEpochSecond();

		LazyOptional<ISeasonCycleCapability> optional = level.getCapability(RealSeasons.SEASON_CYCLE_CAPABILITY);
		if(!optional.isPresent())
			return;

		ISeasonCycleCapability cap = optional.orElse(null);
		long startTime = cap.getSeasonStartTime();

		if(startTime == -1) { // If not initialised
			long startSeconds;

			if(RealSeasonsConfig.START_MIDNIGHT.get())
				startSeconds = RealSeasonsConfig.START_UTC.get() ? LocalDate.now().atTime(0, 0).toEpochSecond(ZoneOffset.UTC) : LocalDate.now().atTime(0, 0).toEpochSecond(OffsetDateTime.now().getOffset());
			else
				startSeconds = currentSeconds;

			cap.setSeasonStartTime(startSeconds);
			cap.setStartingSeason(SubSeason.EARLY_SPRING);
		}

		ISeasonState state = SeasonHelper.getSeasonState(level);

		startTime = cap.getSeasonStartTime();
		long timeSinceStart = currentSeconds - startTime;

		int secondsInCycle = RealSeasonsConfig.SECONDS_PER_SEASON.get() * 4; // Real seconds in RealSeasons cycle.
		double secondsInSubseason = RealSeasonsConfig.SECONDS_PER_SEASON.get() / 3.0D; // Real seconds in RealSeasons cycle.
		int ticksInCycle = state.getCycleDuration(); // Ticks in SereneSeasons cycle.

		double secondsThroughCycle = ((timeSinceStart + secondsInSubseason * cap.getStartingSeasonOffset()) % secondsInCycle); // How many seconds the game is through the current cycle.
		double cyclePercent = secondsThroughCycle / secondsInCycle;

		int desiredCycleTick = (int)Math.round(ticksInCycle * cyclePercent);

		SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(level);
		seasonData.seasonCycleTicks = desiredCycleTick;
		seasonData.setDirty();

		if(level.getGameTime() % 100 == 0)
			SeasonHandler.sendSeasonUpdate(level);
	}

}
