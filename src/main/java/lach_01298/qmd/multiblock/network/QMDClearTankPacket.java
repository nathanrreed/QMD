package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.QMD;
import lach_01298.qmd.multiblock.IMultiBlockTank;
import lach_01298.qmd.network.QMDPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class QMDClearTankPacket extends QMDPacket {
    public static final Type<QMDClearTankPacket> TYPE = new Type<>(ncLoc("qmd_clear_tank_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, QMDClearTankPacket> STREAM_CODEC = StreamCodec.ofMember(
            QMDClearTankPacket::toBytes, QMDClearTankPacket::fromBytes
    );
    private BlockPos pos;
    private int tankID;

    public QMDClearTankPacket(BlockPos pos, int tankID) {
        this.pos = pos;
        this.tankID = tankID;
    }

    public static QMDClearTankPacket fromBytes(RegistryFriendlyByteBuf buf) {
        BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        int tankID = buf.readInt();
        return new QMDClearTankPacket(pos, tankID);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(tankID);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public static void handleOnServer(QMDClearTankPacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                ServerPlayer player = (ServerPlayer) context.player();
                Level level = player.level();
                if (!level.isLoaded(payload.pos) || !level.mayInteract(player, payload.pos)) {
                    return;
                }
                onPacket(payload, level.getBlockEntity(payload.pos));
            });
        }

        protected static void onPacket(QMDClearTankPacket message, BlockEntity tile) {
            if (tile instanceof IMultiblockPart<?> part) {
                IMultiblockController<?> multiblock = part.getMultiblockController().orElse(null);
                if (multiblock instanceof IMultiBlockTank) {
                    IMultiBlockTank mbTanks = (IMultiBlockTank) multiblock;
                    List<Tank> tanks = mbTanks.getTanks();
                    if (tanks.size() > message.tankID) {
                        tanks.get(message.tankID).setFluid(FluidStack.EMPTY);
                    } else {
                        QMD.LOGGER.error("cannot clear multiblock tank {} as multiblock only has {} tanks", message.tankID, tanks.size());
                    }
                }
            }
        }
    }
}
