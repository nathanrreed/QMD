package lach_01298.qmd.network;

import lach_01298.qmd.machine.network.CreativeParticleSourceGuiPacket;
import lach_01298.qmd.machine.network.CreativeParticleSourceUpdatePacket;
import lach_01298.qmd.machine.network.IrradiatorUpdatePacket;
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
//        wrapper.registerMessage(QMDOpenSideConfigGuiPacket.Handler.class, QMDOpenSideConfigGuiPacket.class, nextID(), Side.SERVER); TODO
//        wrapper.registerMessage(QMDOpenTileGuiPacket.Handler.class, QMDOpenTileGuiPacket.class, nextID(), Side.SERVER);
        registrar.playToServer(CreativeParticleSourceGuiPacket.TYPE, CreativeParticleSourceGuiPacket.STREAM_CODEC, CreativeParticleSourceGuiPacket.Handler::handleOnServer);

//        wrapper.registerMessage(QMDClearTankPacket.Handler.class, QMDClearTankPacket.class, nextID(), Side.SERVER);

        // CLIENT
        registrar.playToClient(IrradiatorUpdatePacket.TYPE, IrradiatorUpdatePacket.STREAM_CODEC, IrradiatorUpdatePacket.Handler::handleOnClient);

//        registrar.playToClient(BeamProjectileUpdatePacket.TYPE, BeamProjectileUpdatePacket.STREAM_CODEC, BeamProjectileUpdatePacket.Handler::handleOnClient);
//        registrar.playToClient(LeptonBeamUpdatePacket.TYPE, LeptonBeamUpdatePacket.STREAM_CODEC, LeptonBeamUpdatePacket.Handler::handleOnClient);
//        registrar.playToClient(AntimatterProjectileUpdatePacket.TYPE, AntimatterProjectileUpdatePacket.STREAM_CODEC, AntimatterProjectileUpdatePacket.Handler::handleOnClient);

//        wrapper.registerMessage(LinearAcceleratorUpdatePacket.Handler.class, LinearAcceleratorUpdatePacket.class, nextID(), Side.CLIENT); TODO
//        wrapper.registerMessage(RingAcceleratorUpdatePacket.Handler.class, RingAcceleratorUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(BeamDiverterUpdatePacket.Handler.class, BeamDiverterUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(BeamSplitterUpdatePacket.Handler.class, BeamSplitterUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(DeceleratorUpdatePacket.Handler.class, DeceleratorUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(MassSpectrometerUpdatePacket.Handler.class, MassSpectrometerUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(TargetChamberUpdatePacket.Handler.class, TargetChamberUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(DecayChamberUpdatePacket.Handler.class, DecayChamberUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(BeamDumpUpdatePacket.Handler.class, BeamDumpUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(NeutralContainmentUpdatePacket.Handler.class, NeutralContainmentUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(ContainmentRenderPacket.Handler.class, ContainmentRenderPacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(CollisionChamberUpdatePacket.Handler.class, CollisionChamberUpdatePacket.class, nextID(), Side.CLIENT);

        registrar.playToClient(CreativeParticleSourceUpdatePacket.TYPE, CreativeParticleSourceUpdatePacket.STREAM_CODEC, CreativeParticleSourceUpdatePacket.Handler::handleOnClient);

//        wrapper.registerMessage(NucleosynthesisChamberUpdatePacket.Handler.class, NucleosynthesisChamberUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(AcceleratorSourceUpdatePacket.Handler.class, AcceleratorSourceUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(LiquefierUpdatePacket.Handler.class, LiquefierUpdatePacket.class, nextID(), Side.CLIENT);
//        wrapper.registerMessage(LiquefierRenderPacket.Handler.class, LiquefierRenderPacket.class, nextID(), Side.CLIENT);
    }
}
