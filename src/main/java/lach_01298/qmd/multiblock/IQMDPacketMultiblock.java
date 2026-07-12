package lach_01298.qmd.multiblock;

import com.nred.nuclearcraft.multiblock.IPacketMultiblock;
import com.nred.nuclearcraft.multiblock.Multiblock;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public interface IQMDPacketMultiblock<MULTIBLOCK extends Multiblock<MULTIBLOCK>, PACKET extends MultiblockUpdatePacket> extends IPacketMultiblock<MULTIBLOCK, PACKET> {
    default void sendMultiblockUpdatePacketToListeners() {
        if (getWorld().isClientSide()) {
            return;
        }
        PACKET packet = getMultiblockUpdatePacket();
        if (packet == null) {
            return;
        }
        for (Player player : getMultiblockUpdatePacketListeners()) {
            PacketDistributor.sendToPlayer((ServerPlayer) player, packet);
        }
    }

    default void sendMultiblockUpdatePacketToPlayer(Player player) {
        if (getWorld().isClientSide()) {
            return;
        }
        PACKET packet = getMultiblockUpdatePacket();
        if (packet == null) {
            return;
        }
        PacketDistributor.sendToPlayer((ServerPlayer) player, packet);
    }

    default void sendMultiblockUpdatePacketToAll() {
        if (getWorld().isClientSide()) {
            return;
        }
        PACKET packet = getMultiblockUpdatePacket();
        if (packet == null) {
            return;
        }
        PacketDistributor.sendToAllPlayers(packet);
    }
}