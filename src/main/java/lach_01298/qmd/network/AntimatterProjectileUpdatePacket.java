//package lach_01298.qmd.network;
//
//import lach_01298.qmd.entity.EntityAntimatterProjectile;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.neoforged.neoforge.network.handling.IPayloadContext;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class AntimatterProjectileUpdatePacket extends QMDPacket {
//    public static final Type<AntimatterProjectileUpdatePacket> TYPE = new Type<>(ncLoc("antimatter_projectile_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, AntimatterProjectileUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            AntimatterProjectileUpdatePacket::toBytes, AntimatterProjectileUpdatePacket::fromBytes
//    );
//
//    public int entityId;
//    public int color;
//
//    public AntimatterProjectileUpdatePacket(EntityAntimatterProjectile projectile) {
//        this.entityId = projectile.getId();
//        this.color = projectile.getColor().getRGB();
//    }
//
//    public AntimatterProjectileUpdatePacket(int entityId, int color) {
//        this.entityId = entityId;
//        this.color = color;
//    }
//
//    public static AntimatterProjectileUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
//        int entityId = buf.readInt();
//        int color = buf.readInt();
//        return new AntimatterProjectileUpdatePacket(entityId, color);
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        buf.writeInt(entityId);
//        buf.writeInt(color);
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler {
//        public static void handleOnClient(AntimatterProjectileUpdatePacket payload, IPayloadContext context) {
//            context.enqueueWork(() -> {
//                if (context.player().level().getEntity(payload.entityId) instanceof EntityAntimatterProjectile projectile) {
//                    projectile.setColor(payload.color);
//                }
//            });
//        }
//    }
//}
