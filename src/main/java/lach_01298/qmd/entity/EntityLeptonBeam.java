//package lach_01298.qmd.entity;
//
//import lach_01298.qmd.network.LeptonBeamUpdatePacket;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.neoforged.neoforge.network.PacketDistributor;
//
//import java.awt.*;
//
//import static lach_01298.qmd.entity.QMDEntities.LEPTON_BEAM;
//
//public class EntityLeptonBeam extends EntityBeamProjectile {
//    private Color color;
//
//    public EntityLeptonBeam(EntityType<EntityLeptonBeam> entityType, Level level) {
//        super(entityType, level);
//    }
//
//    public EntityLeptonBeam(Level level, Player player, double length, InteractionHand hand, Integer color) {
//        super(LEPTON_BEAM.get(), level, player, length, hand, 4);
//        this.color = new Color(color);
//    }
//
//    public void setColour(int color) {
//        this.color = new Color(color);
//    }
//
//    public Color getColor() {
//        return this.color;
//    }
//
//    @Override
//    protected void sendUpdatePacket() {
//        if (!this.level().isClientSide()) {
//            if (this.getOwner() != null) {
//                PacketDistributor.sendToPlayersNear((ServerLevel) level(), null, this.getX(), this.getY(), this.getZ(), 128, new LeptonBeamUpdatePacket(this));
//            }
//        }
//    }
//}