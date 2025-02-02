package net.luciusoflegend.nightdemons.game.rule;

import xyz.nucleoid.plasmid.api.game.rule.GameRuleType;
import xyz.nucleoid.stimuli.event.player.PlayerDamageEvent;

public final class NightDemonsRuleType {

    public static final GameRuleType TAKE_DAMAGE = GameRuleType.create().enforces(PlayerDamageEvent.EVENT, result -> (player, source, amount) -> result);
}
