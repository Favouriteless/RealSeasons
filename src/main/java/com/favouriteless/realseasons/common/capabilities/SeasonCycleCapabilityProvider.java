package com.favouriteless.realseasons.common.capabilities;

import com.favouriteless.realseasons.RealSeasons;
import com.favouriteless.realseasons.api.capabilities.ISeasonCycleCapability;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SeasonCycleCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

	private final ISeasonCycleCapability backend = new SeasonCycleCapability();
	private final LazyOptional<ISeasonCycleCapability> optionalData = LazyOptional.of(() -> backend);

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return RealSeasons.SEASON_CYCLE_CAPABILITY.orEmpty(cap, optionalData);
	}

	@Override
	public CompoundTag serializeNBT() {
		return backend.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		backend.deserializeNBT(nbt);
	}

}
