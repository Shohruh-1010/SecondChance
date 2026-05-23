package com.secondchance;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondChanceMod implements ModInitializer {
	public static final String MOD_ID = "secondchance";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("SecondChance mod loaded!");
	}
}
