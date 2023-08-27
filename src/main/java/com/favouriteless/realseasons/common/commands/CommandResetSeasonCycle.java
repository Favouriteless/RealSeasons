package com.favouriteless.realseasons.common.commands;

import com.favouriteless.realseasons.RealSeasons;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season.SubSeason;
import sereneseasons.handler.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

public class CommandResetSeasonCycle {


	public static int resetCycle(CommandSourceStack cs, Level level, SubSeason season) throws CommandRuntimeException {
		if (season != null && level.getServer() != null) {

			for(ServerLevel serverLevel : level.getServer().getAllLevels()) {
				serverLevel.getCapability(RealSeasons.SEASON_CYCLE_CAPABILITY).ifPresent(cap -> {
					cap.setSeasonStartTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
					cap.setStartingSeason(season);
				});
			}
			
			cs.sendSuccess(Component.translatable("commands.realseasons.resetcycle.success").withStyle(ChatFormatting.GREEN), true);

		} else {
			cs.sendSuccess(Component.translatable("commands.realseasons.resetcycle.fail").withStyle(ChatFormatting.RED), true);
		}
		return 1;
	}

}
