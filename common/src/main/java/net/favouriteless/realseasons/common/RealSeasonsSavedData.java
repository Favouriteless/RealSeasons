package net.favouriteless.realseasons.common;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import sereneseasons.api.season.Season.SubSeason;

public class RealSeasonsSavedData extends SavedData {

    private static final String NAME = RealSeasons.MOD_ID + "_SeasonData";
    public long seasonStartTime = -1;
    public SubSeason startingSeason = null;

    public RealSeasonsSavedData() {
        super();
    }

    public static RealSeasonsSavedData get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(new Factory<>(RealSeasonsSavedData::new, RealSeasonsSavedData::load, null), NAME);
    }

    public static RealSeasonsSavedData load(CompoundTag tag, Provider provider) {
        RealSeasonsSavedData data = new RealSeasonsSavedData();
        data.seasonStartTime = tag.getLong("seasonStartTime");
        data.startingSeason = SubSeason.values()[tag.getInt("startingSeason")];
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, Provider provider) {
        tag.putLong("seasonStartTime", seasonStartTime);
        tag.putInt("startingSeason", startingSeason.ordinal());
        return tag;
    }

}
