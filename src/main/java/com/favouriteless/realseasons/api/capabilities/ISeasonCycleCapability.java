package com.favouriteless.realseasons.api.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISeasonCycleCapability extends INBTSerializable<CompoundTag> {

	/**
	 * Set the start time for the season cycle. This should be a LocalDateTime integer.
	 */
	void setSeasonStartTime(long time);

	/**
	 * Get the start time for the season cycle.
	 */
	long getSeasonStartTime();

}
