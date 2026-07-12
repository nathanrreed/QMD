package lach_01298.qmd.network;

import lach_01298.qmd.machine.network.CreativeParticleSourceGuiPacket;
import lach_01298.qmd.machine.network.CreativeParticleSourceUpdatePacket;
import lach_01298.qmd.machine.network.IrradiatorUpdatePacket;
import lach_01298.qmd.multiblock.network.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class QMDPackets {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        // SERVER
        registrar.playToServer(CreativeParticleSourceGuiPacket.TYPE, CreativeParticleSourceGuiPacket.STREAM_CODEC, CreativeParticleSourceGuiPacket.Handler::handleOnServer);
        registrar.playToServer(QMDClearTankPacket.TYPE, QMDClearTankPacket.STREAM_CODEC, QMDClearTankPacket.Handler::handleOnServer);

        // CLIENT
        registrar.playToClient(IrradiatorUpdatePacket.TYPE, IrradiatorUpdatePacket.STREAM_CODEC, IrradiatorUpdatePacket.Handler::handleOnClient);

//        registrar.playToClient(BeamProjectileUpdatePacket.TYPE, BeamProjectileUpdatePacket.STREAM_CODEC, BeamProjectileUpdatePacket.Handler::handleOnClient);
//        registrar.playToClient(LeptonBeamUpdatePacket.TYPE, LeptonBeamUpdatePacket.STREAM_CODEC, LeptonBeamUpdatePacket.Handler::handleOnClient);
//        registrar.playToClient(AntimatterProjectileUpdatePacket.TYPE, AntimatterProjectileUpdatePacket.STREAM_CODEC, AntimatterProjectileUpdatePacket.Handler::handleOnClient);

        registrar.playToClient(LinearAcceleratorUpdatePacket.TYPE, LinearAcceleratorUpdatePacket.STREAM_CODEC, LinearAcceleratorUpdatePacket.Handler::handleOnClient);
        registrar.playToClient(RingAcceleratorUpdatePacket.TYPE, RingAcceleratorUpdatePacket.STREAM_CODEC, RingAcceleratorUpdatePacket.Handler::handleOnClient);
        registrar.playToClient(BeamDiverterUpdatePacket.TYPE, BeamDiverterUpdatePacket.STREAM_CODEC, BeamDiverterUpdatePacket.Handler::handleOnClient);
        registrar.playToClient(BeamSplitterUpdatePacket.TYPE, BeamSplitterUpdatePacket.STREAM_CODEC, BeamSplitterUpdatePacket.Handler::handleOnClient);
        registrar.playToClient(DeceleratorUpdatePacket.TYPE, DeceleratorUpdatePacket.STREAM_CODEC, DeceleratorUpdatePacket.Handler::handleOnClient);
        registrar.playToClient(MassSpectrometerUpdatePacket.TYPE, MassSpectrometerUpdatePacket.STREAM_CODEC, MassSpectrometerUpdatePacket.Handler::handleOnClient);

//        wrapper.registerMessage(TargetChamberUpdatePacket.Handler.class, TargetChamberUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(DecayChamberUpdatePacket.Handler.class, DecayChamberUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(BeamDumpUpdatePacket.Handler.class, BeamDumpUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(NeutralContainmentUpdatePacket.Handler.class, NeutralContainmentUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(ContainmentRenderPacket.Handler.class, ContainmentRenderPacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(CollisionChamberUpdatePacket.Handler.class, CollisionChamberUpdatePacket.class, nextID(), Side.CLIENT);

        registrar.playToClient(CreativeParticleSourceUpdatePacket.TYPE, CreativeParticleSourceUpdatePacket.STREAM_CODEC, CreativeParticleSourceUpdatePacket.Handler::handleOnClient);

//        wrapper.registerMessage(NucleosynthesisChamberUpdatePacket.Handler.class, NucleosynthesisChamberUpdatePacket.class, nextID(), Side.CLIENT);
        registrar.playToClient(AcceleratorSourceUpdatePacket.TYPE, AcceleratorSourceUpdatePacket.STREAM_CODEC, AcceleratorSourceUpdatePacket.Handler::handleOnClient);
//        wrapper.registerMessage(LiquefierUpdatePacket.Handler.class, LiquefierUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(LiquefierRenderPacket.Handler.class, LiquefierRenderPacket.class, nextID(), Side.CLIENT);
    }
}
