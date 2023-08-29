package com.favouriteless.realseasons.common.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.Component;

import java.time.LocalTime;

public class RealTimeArgument implements ArgumentType<LocalTime>  {

	private static final SimpleCommandExceptionType ERROR_INVALID_TIME = new SimpleCommandExceptionType(Component.translatable("argument.realseasons.time.invalid"));

	public static RealTimeArgument arg() {
		return new RealTimeArgument();
	}

	@Override
	public LocalTime parse(StringReader reader) throws CommandSyntaxException {
		String timeString = reader.readString();

		if(!timeString.matches("^[0-9]{4}"))
			throw ERROR_INVALID_TIME.create();

		int hour = Integer.parseInt(timeString.substring(0, 2));
		int minute = Integer.parseInt(timeString.substring(2, 4));

		if(hour < 0 || hour > 24)
			throw ERROR_INVALID_TIME.create();
		if(minute < 0 || minute > 59)
			throw ERROR_INVALID_TIME.create();

		return LocalTime.of(hour, minute);
	}

}
