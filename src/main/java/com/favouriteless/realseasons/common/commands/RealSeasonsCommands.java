package com.favouriteless.realseasons.common.commands;

import com.favouriteless.realseasons.RealSeasons;
import net.minecraft.commands.Commands;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.server.command.EnumArgument;
import sereneseasons.api.season.Season.SubSeason;

@EventBusSubscriber(modid=RealSeasons.MODID, bus=Bus.FORGE)
public class RealSeasonsCommands {

	@SubscribeEvent
	public static void onCommandsRegistered(RegisterCommandsEvent event) {
		event.getDispatcher().register(
				Commands.literal("resetSeasonCycle")
						.then(Commands.argument("start_season", EnumArgument.enumArgument(SubSeason.class))
								.executes((ctx) -> {
									Level world = ctx.getSource().getLevel();
									return CommandResetSeasonCycle.resetCycle(ctx.getSource(), world, ctx.getArgument("start_season", SubSeason.class));
								})
						)
		);
	}

}
