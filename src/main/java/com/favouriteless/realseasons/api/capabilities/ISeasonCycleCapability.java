package com.favouriteless.realseasons.api.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import sereneseasons.api.season.Season.SubSeason;

public interface ISeasonCycleCapability extends INBTSerializable<CompoundTag> {

	/**
	 * Set the start time for the season cycle. This should be a long representing seconds since epoch time, UTC.
	 */
	void setSeasonStartTime(long time);

	/**
	 * Get the start time for the season cycle.
	 */
	long getSeasonStartTime();

	void setStartingSeason(SubSeason season);

	int getStartingSeasonOffset();

	SubSeason getStartingSeason();

}
