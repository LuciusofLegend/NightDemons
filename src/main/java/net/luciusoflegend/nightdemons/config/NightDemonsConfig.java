package net.luciusoflegend.nightdemons.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record NightDemonsConfig(String greeting) {
    public static final MapCodec<NightDemonsConfig> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("greeting").forGetter(NightDemonsConfig::greeting)
    ).apply(instance, NightDemonsConfig::new));
}
