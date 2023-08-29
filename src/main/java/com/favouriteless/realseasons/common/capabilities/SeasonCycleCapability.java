package com.favouriteless.realseasons.common.capabilities;

import com.favouriteless.realseasons.api.capabilities.ISeasonCycleCapability;
import net.minecraft.nbt.CompoundTag;
import sereneseasons.api.season.Season.SubSeason;

public class SeasonCycleCapability implements ISeasonCycleCapability {

	private long seasonStartTime = -1;
	private String startingSeason = "";

	@Override
	public void setSeasonStartTime(long time) {
		seasonStartTime = time;
	}

	@Override
	public long getSeasonStartTime() {
		return seasonStartTime;
	}

	@Override
	public void setStartingSeason(SubSeason season) {
		startingSeason = season.name();
	}

	@Override
	public int getStartingSeasonOffset() {
		return SubSeason.valueOf(startingSeason).ordinal();
	}

	@Override
	public SubSeason getStartingSeason() {
		return SubSeason.valueOf(startingSeason);
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putLong("startTime", seasonStartTime);
		nbt.putString("startSeason", startingSeason);

		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		seasonStartTime = nbt.getLong("startTime");
		startingSeason = nbt.getString("startSeason");

		if(startingSeason.equals(""))
			startingSeason = SubSeason.EARLY_SPRING.name();
	}



}
