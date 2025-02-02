package net.luciusoflegend.nightdemons.game;

import net.luciusoflegend.nightdemons.config.NightDemonsConfig;
import net.luciusoflegend.nightdemons.game.rule.NightDemonsRuleType;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.map_templates.MapTemplate;
import xyz.nucleoid.plasmid.api.game.GameOpenContext;
import xyz.nucleoid.plasmid.api.game.GameOpenProcedure;
import xyz.nucleoid.plasmid.api.game.GameSpace;
import xyz.nucleoid.plasmid.api.game.event.GamePlayerEvents;
import xyz.nucleoid.plasmid.api.game.player.JoinOffer;
import xyz.nucleoid.plasmid.api.game.player.JoinOfferResult;
import xyz.nucleoid.plasmid.api.game.world.generator.TemplateChunkGenerator;

public final class NightDemonsGame {

    private final NightDemonsConfig config;
    private final GameSpace gameSpace;
    private final ServerWorld world;

    public NightDemonsGame(NightDemonsConfig config, GameSpace gameSpace, ServerWorld world) {
        this.config = config;
        this.gameSpace = gameSpace;
        this.world = world;
    }

    public static GameOpenProcedure open(GameOpenContext<NightDemonsConfig> context) {
        NightDemonsConfig config = context.config();

        MapTemplate template = MapTemplate.createEmpty();
        template.setBlockState(new BlockPos(0, 64, 0), Blocks.STONE.getDefaultState());

        TemplateChunkGenerator generator = new TemplateChunkGenerator(context.server(), template);

        RuntimeWorldConfig worldConfig = new RuntimeWorldConfig()
                .setGenerator(generator)
                .setTimeOfDay(6000);

        return context.openWithWorld(worldConfig, (activity, world) -> {
            NightDemonsGame game = new NightDemonsGame(config, activity.getGameSpace(), world);

            activity.deny(NightDemonsRuleType.TAKE_DAMAGE);
            activity.listen(GamePlayerEvents.OFFER, game::playerJoinOffer);
            activity.listen(GamePlayerEvents.ADD, game::addPlayer);
        });
    }

    private JoinOfferResult playerJoinOffer(JoinOffer offer)
    {
        return offer.accept();
    }

    private void addPlayer(ServerPlayerEntity player)
    {
        Text greetingMessage = Text.literal(config.greeting());
        this.gameSpace.getPlayers().sendMessage(greetingMessage);
        player.changeGameMode(GameMode.ADVENTURE);
    }
}