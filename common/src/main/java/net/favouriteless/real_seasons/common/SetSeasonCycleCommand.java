package net.favouriteless.real_seasons.common;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import glitchcore.event.server.RegisterCommandsEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import sereneseasons.api.season.Season.SubSeason;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SetSeasonCycleCommand {

    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("setSeasonCycle")
                        .requires(cs -> cs.hasPermission(3))
                        .then(Commands.argument("start_season", StringArgumentType.word())
                                .then(Commands.argument("now", BoolArgumentType.bool())
                                        .executes(c -> setCycle(c.getSource(), StringArgumentType.getString(c, "start_season"), BoolArgumentType.getBool(c, "now")))
                                )
                        )
        );
    }

    public static int setCycle(CommandSourceStack cs, String startSeason, boolean now) {
        SubSeason subSeason = SubSeason.valueOf(startSeason);
        if(subSeason == null) {
            cs.sendFailure(Component.literal(startSeason + " is not a valid subseason"));
            return 0;
        }

        long epoch = now ? LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) : LocalDate.now().atTime(0, 0).toEpochSecond(ZoneOffset.UTC);

        RealSeasonsSavedData data = RealSeasonsSavedData.get(cs.getLevel());
        data.seasonStartTime = epoch;
        data.startingSeason = subSeason;
        data.setDirty();

        return 1;
    }

}
