package lach_01298.qmd.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record IrradiatorFuel(double speedMultiplier) {
    public static final Codec<IrradiatorFuel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("speed_mult").forGetter(IrradiatorFuel::speedMultiplier)
    ).apply(instance, IrradiatorFuel::new));
}