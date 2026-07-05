//package lach_01298.qmd.network;
//
//import io.netty.buffer.ByteBuf;
//import lach_01298.qmd.entity.EntityBeamProjectile;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.player.Player;
//import net.neoforged.neoforge.network.handling.IPayloadContext;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class BeamProjectileUpdatePacket extends QMDPacket {
//    public static final Type<BeamProjectileUpdatePacket> TYPE = new Type<>(ncLoc("beam_projectile_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, BeamProjectileUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            BeamProjectileUpdatePacket::toBytes, BeamProjectileUpdatePacket::fromBytes
//    );
//
//    public int entityId;
//    public int ownerEntityId;
//    public double length;
//    public boolean mainHand;
//
//    public BeamProjectileUpdatePacket(EntityBeamProjectile projectile) {
//        this.entityId = projectile.getId();
//
//        this.ownerEntityId = projectile.getOwner().getId();
//        this.length = projectile.getLength();
//        mainHand = projectile.getHand() == InteractionHand.MAIN_HAND;
//    }
//
//    public BeamProjectileUpdatePacket(int entityId, int ownerEntityId, double length, boolean mainHand) {
//        this.entityId = entityId;
//        this.ownerEntityId = ownerEntityId;
//        this.length = length;
//        this.mainHand = mainHand;
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static BeamProjectileUpdatePacket fromBytes(ByteBuf buf) {
//        int entityId = buf.readInt();
//        int ownerEntityId = buf.readInt();
//        double length = buf.readDouble();
//        boolean mainHand = buf.readBoolean();
//        return new BeamProjectileUpdatePacket(entityId, ownerEntityId, length, mainHand);
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        buf.writeInt(entityId);
//        buf.writeInt(ownerEntityId);
//        buf.writeDouble(length);
//        buf.writeBoolean(mainHand);
//    }
//
//    public static class Handler {
//        public static void handleOnClient(BeamProjectileUpdatePacket payload, IPayloadContext context) {
//            context.enqueueWork(() -> {
//                if (context.player().level().getEntity(payload.entityId) instanceof EntityBeamProjectile beam) {
//                    if (context.player().level().getEntity(payload.ownerEntityId) instanceof Player playerOwner) {
//                        beam.setOwner(playerOwner);
//                        beam.setLength(payload.length);
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