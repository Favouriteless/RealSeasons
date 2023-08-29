package com.favouriteless.realseasons;

import com.favouriteless.realseasons.api.capabilities.ISeasonCycleCapability;
import com.favouriteless.realseasons.common.commands.RealTimeArgument;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(RealSeasons.MOD_ID)
public class RealSeasons {

    public static final String MOD_ID = "realseasons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static Capability<ISeasonCycleCapability> SEASON_CYCLE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, MOD_ID);
    public static final RegistryObject<SingletonArgumentInfo<RealTimeArgument>> ARGUMENT_REALTIME = ARGUMENT_TYPES.register("real_time", () ->
            ArgumentTypeInfos.registerByClass(RealTimeArgument.class, SingletonArgumentInfo.contextFree(RealTimeArgument::arg)));

    public RealSeasons() {
        ARGUMENT_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(Type.SERVER, RealSeasonsConfig.SPEC);
    }

}
