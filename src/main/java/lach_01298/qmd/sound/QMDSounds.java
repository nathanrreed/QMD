package lach_01298.qmd.sound;

import lach_01298.qmd.QMD;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class QMDSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, QMD.MOD_ID);

    public static Supplier<SoundEvent> lepton_cannon;
    public static Supplier<SoundEvent> gluon_gun;
    public static Supplier<SoundEvent> gluon_gun_start;
    public static Supplier<SoundEvent> gluon_gun_stop;

    public static void init() {
        lepton_cannon = SOUND_EVENTS.register("neutral.qmd.lepton_cannon", SoundEvent::createVariableRangeEvent);
        gluon_gun = SOUND_EVENTS.register("neutral.qmd.gluon_gun", SoundEvent::createVariableRangeEvent);
        gluon_gun_start = SOUND_EVENTS.register("neutral.qmd.gluon_gun_start", SoundEvent::createVariableRangeEvent);
        gluon_gun_stop = SOUND_EVENTS.register("neutral.qmd.gluon_gun_stop", SoundEvent::createVariableRangeEvent);
    }

    public static void register(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
    }
}