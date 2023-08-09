package com.favouriteless.realseasons.common.capabilities;

import com.favouriteless.realseasons.api.capabilities.ISeasonCycleCapability;
import net.minecraft.nbt.CompoundTag;

public class SeasonCycleCapability implements ISeasonCycleCapability {

	private long seasonStartTime = -1;

	@Override
	public void setSeasonStartTime(long time) {
		seasonStartTime = time;
	}

	@Override
	public long getSeasonStartTime() {
		return seasonStartTime;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putLong("startTime", seasonStartTime);

		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		seasonStartTime = nbt.getLong("startTime");
	}

}
