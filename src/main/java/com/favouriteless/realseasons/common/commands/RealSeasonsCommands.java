package com.favouriteless.realseasons.common.commands;

import com.favouriteless.realseasons.RealSeasons;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.server.command.EnumArgument;
import sereneseasons.api.season.Season.SubSeason;

import java.time.*;
import java.time.format.DateTimeFormatter;

@EventBusSubscriber(modid=RealSeasons.MOD_ID, bus=Bus.FORGE)
public class RealSeasonsCommands {

	@SubscribeEvent
	public static void onCommandsRegistered(RegisterCommandsEvent event) {
		event.getDispatcher().register(
				Commands.literal("resetSeasonCycle")
						.then(Commands.argument("start_season", EnumArgument.enumArgument(SubSeason.class))
								.executes(ctx -> resetCycle(ctx.getSource(), ctx.getSource().getLevel(), ctx.getArgument("start_season", SubSeason.class), true, LocalDateTime.now()))
						.then(Commands.literal("utc")
								.then(Commands.argument("time", RealTimeArgument.arg())
									.executes(ctx -> resetCycle(ctx.getSource(), ctx.getSource().getLevel(), getSeasonArg(ctx), true, LocalDate.now().atTime(ctx.getArgument("time", LocalTime.class)))))
						)
						.then(Commands.literal("local")
								.then(Commands.argument("time", RealTimeArgument.arg())
									.executes(ctx -> resetCycle(ctx.getSource(), ctx.getSource().getLevel(), getSeasonArg(ctx), false, LocalDate.now().atTime(ctx.getArgument("time", LocalTime.class)))))
						))
		);
		event.getDispatcher().register(
				Commands.literal("getSeasonCycle")
						.executes(ctx -> getCycleInfo(ctx.getSource(), ctx.getSource().getLevel(), true))
					.then(Commands.literal("local")
								.executes(ctx -> getCycleInfo(ctx.getSource(), ctx.getSource().getLevel(), false)))
						.then(Commands.literal("utc")
								.executes(ctx -> getCycleInfo(ctx.getSource(), ctx.getSource().getLevel(), true)))
		);
	}

	public static int resetCycle(CommandSourceStack cs, Level level, SubSeason season, boolean isUtc, LocalDateTime time) throws CommandRuntimeException {
		if (season != null && level.getServer() != null) {

			if(time.isAfter(LocalDateTime.now()))
				cs.sendSuccess(Component.translatable("commands.realseasons.resetcycle.fail_future").withStyle(ChatFormatting.RED), true);
			else {
				for(ServerLevel serverLevel : level.getServer().getAllLevels()) {
					serverLevel.getCapability(RealSeasons.SEASON_CYCLE_CAPABILITY).ifPresent(cap -> {
						long epochSeconds = isUtc ? time.toEpochSecond(ZoneOffset.UTC) : time.toEpochSecond(OffsetDateTime.now().getOffset());

						cap.setSeasonStartTime(epochSeconds);
						cap.setStartingSeason(season);
					});
				}

				String timeString = time.atOffset(isUtc ? ZoneOffset.UTC : OffsetDateTime.now().getOffset()).format(DateTimeFormatter.ofPattern("HH:mm")) + (isUtc ? " UTC" : " " + ZoneOffset.systemDefault().getId());
				cs.sendSuccess(Component.translatable("commands.realseasons.resetcycle.success", season.toString(), timeString).withStyle(ChatFormatting.GREEN), true);
			}
		}
		else
			cs.sendSuccess(Component.translatable("commands.realseasons.resetcycle.fail").withStyle(ChatFormatting.RED), true);

		return 1;
	}

	public static int getCycleInfo(CommandSourceStack cs, Level level, boolean isUtc) throws CommandRuntimeException {
		if(level != null) {
			level.getCapability(RealSeasons.SEASON_CYCLE_CAPABILITY).ifPresent(cap -> {
				ZoneOffset offset = isUtc ? ZoneOffset.UTC : OffsetDateTime.now().getOffset();
				String timeString = LocalDateTime.ofEpochSecond(cap.getSeasonStartTime(), 0, offset)
						.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + (isUtc ? " UTC" : " " + ZoneOffset.systemDefault().getId());
				cs.sendSuccess(Component.translatable("commands.realseasons.getinfo.success", cap.getStartingSeason(), timeString).withStyle(ChatFormatting.GREEN), true);
			});
		}
		return 1;
	}

	private static SubSeason getSeasonArg(CommandContext<CommandSourceStack> ctx) {
		return ctx.getArgument("start_season", SubSeason.class);
	}

}
