package net.luciusoflegend.nightdemons;

import net.fabricmc.api.ModInitializer;

import net.luciusoflegend.nightdemons.config.NightDemonsConfig;
import net.luciusoflegend.nightdemons.game.NightDemonsGame;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.nucleoid.plasmid.api.game.GameType;

public class NightDemons implements ModInitializer {
	public static final String MOD_ID = "nightdemons";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Ready to play some NightDemons");
		GameType.register(Identifier.of("nightdemons:nightdemonsgame"), NightDemonsConfig.CODEC, NightDemonsGame::open);
	}
}