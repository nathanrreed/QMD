//package lach_01298.qmd.network;
//
//import io.netty.buffer.ByteBuf;
//import lach_01298.qmd.entity.EntityLeptonBeam;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.player.Player;
//import net.neoforged.neoforge.network.handling.IPayloadContext;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class LeptonBeamUpdatePacket extends BeamProjectileUpdatePacket {
//    public static final Type<LeptonBeamUpdatePacket> TYPE = new Type<>(ncLoc("lepton_beam_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, LeptonBeamUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            LeptonBeamUpdatePacket::toBytes, LeptonBeamUpdatePacket::fromBytes
//    );
//
//    public int color;
//
//
//    public LeptonBeamUpdatePacket(EntityLeptonBeam projectile) {
//        super(projectile);
//        this.color = projectile.getColor().getRGB();
//    }
//
//    public LeptonBeamUpdatePacket(BeamProjectileUpdatePacket beamProjectileUpdatePacket, int color) {
//        super(beamProjectileUpdatePacket.entityId, beamProjectileUpdatePacket.ownerEntityId, beamProjectileUpdatePacket.length, beamProjectileUpdatePacket.mainHand);
//        this.color = color;
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static LeptonBeamUpdatePacket fromBytes(ByteBuf buf) {
//        BeamProjectileUpdatePacket beamProjectileUpdatePacket = BeamProjectileUpdatePacket.fromBytes(buf);
//        int color = buf.readInt();
//        return new LeptonBeamUpdatePacket(beamProjectileUpdatePacket, color);
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//        buf.writeInt(color);
//    }
//
//    public static class Handler {
//        public static void handleOnClient(LeptonBeamUpdatePacket payload, IPayloadContext context) {
//            context.enqueueWork(() -> {
//                if (context.player().level().getEntity(payload.entityId) instanceof EntityLeptonBeam beam) {
//                    if (context.player().level().getEntity(payload.ownerEntityId) instanceof Player beamOwner) {
//                        beam.setOwner(beamOwner);
//                        beam.setLength(payload.length);
//                        beam.setColour(payload.color);
//
//                        if (payload.mainHand) {
//                            beam.setHand(InteractionHand.MAIN_HAND);
//                        } else {
//                            beam.setHand(InteractionHand.OFF_HAND);
//                        }
//                    }
//                }
//            });
//        }
//    }
//}