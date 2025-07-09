package net.favouriteless.real_seasons.mixin.common;

import glitchcore.event.TickEvent;
import net.favouriteless.real_seasons.common.RealSeasonsSavedData;
import net.favouriteless.real_seasons.common.ServerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
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

import java.time.*;

@Mixin(SeasonHandler.class)
public class SeasonHandlerMixin {

    @Shadow
    public static SeasonSavedData getSeasonSavedData(Level w) {
        return null;
    }

    /**
     * @author Favouriteless
     * @reason RealSeasons completely replaces SereneSeasons tick logic.
     */
    @Overwrite(remap = false)
    public static void onLevelTick(TickEvent.Level event) {
        Level l = event.getLevel();

        if(event.getPhase() != TickEvent.Phase.START || l.isClientSide() || !ModConfig.seasons.isDimensionWhitelisted(l.dimension()))
            return;
        if(!l.getGameRules().getBoolean(SSGameRules.RULE_DOSEASONCYCLE))
            return;

        ServerLevel level = (ServerLevel)l;

        long currentSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        RealSeasonsSavedData data = RealSeasonsSavedData.get(level);

        if(data.seasonStartTime == -1) { // If -1 this means RealSeasons hasn't been initialised yet.
            data.seasonStartTime = currentSeconds;
            data.startingSeason = SubSeason.EARLY_SPRING;
            data.setDirty();
        }

        SeasonSavedData seasonData = getSeasonSavedData(level);
        if(seasonData == null)
            return;

        ISeasonState state = SeasonHelper.getSeasonState(level);

        long timeElapsed = currentSeconds - data.seasonStartTime;
        int subseasonsElapsed = (int)(timeElapsed / Math.round(ServerConfig.INSTANCE.SECONDS_PER_SEASON.get() / 3.0D));

        int subseasonDuration = state.getSubSeasonDuration();
        int offset = data.startingSeason.ordinal() * subseasonDuration;

        seasonData.seasonCycleTicks = subseasonDuration * subseasonsElapsed + offset;
        seasonData.setDirty();

        if(level.getGameTime() % 600 == 0)
            SeasonHandler.sendSeasonUpdate(level); // Update every 30 seconds as we don't have state change info here.
    }

}