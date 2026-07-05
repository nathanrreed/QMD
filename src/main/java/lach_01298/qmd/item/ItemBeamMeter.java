package lach_01298.qmd.item;

import com.nred.nuclearcraft.item.NCItem;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Equations;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.text.DecimalFormat;
import java.util.List;


public class ItemBeamMeter extends NCItem {
    public ItemBeamMeter() {
        super(new Properties().stacksTo(1), true, List.of("item.qmd.beam_meter.desc"));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            BlockEntity tile = level.getBlockEntity(context.getClickedPos());
            if (tile != null) {
                Player player = context.getPlayer();
                if (player.isCrouching()) {
//                    if (tile instanceof TileParticleChamberBeamPort) { TODO add
//                        TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) tile;
//                        if (port.getIOType() == IOType.OUTPUT) {
//                            int inputNumberOffset = 0;
//                            if (port.getMultiblock().getLogic() instanceof CollisionChamberLogic) {
//                                inputNumberOffset = 1;
//                            }
//                            TextComponentString message = new TextComponentString(
//                                    Lang.localize("qmd.block.particle_chamber_port_setting", ChatFormatting.LIGHT_PURPLE + " " + (port.getIONumber() - inputNumberOffset)));
//                            player.sendMessage(message);
//                            return EnumActionResult.SUCCESS;
//                        }
//                    }
//                    if (tile instanceof TileAcceleratorBeamPort) {
//                        TileAcceleratorBeamPort port = (TileAcceleratorBeamPort) tile;
//                        ChatFormatting colour;
//                        switch (port.getSetting()) {
//                            case INPUT:
//                                colour = ChatFormatting.DARK_AQUA;
//                                break;
//                            case OUTPUT:
//                                colour = ChatFormatting.RED;
//                                break;
//                            default:
//                                colour = ChatFormatting.GRAY;
//                                break;
//                        }
//                        TextComponentString message = new TextComponentString(Lang.localize("qmd.block.accelerator_port_setting", colour + Lang.localize("qmd.block.port_mode." + port.getSetting().name())));
//                        player.sendMessage(message);
//                        return EnumActionResult.SUCCESS;
//                    }
                } else {
                    for (Direction face : Direction.values()) {
                        IParticleStackHandler particleStorage = level.getCapability(CapabilityParticleStackHandler.BLOCK, tile.getBlockPos(), face);
                        if (particleStorage != null) {
                            ParticleStack particles = particleStorage.getParticle();

                            if (particles != null) {
//                                if (tile instanceof TileBeamline) { TODO
//                                    TileBeamline beam = (TileBeamline) tile;
//                                    if (beam.getMultiblock() != null) {
//                                        particles.addFocus(-Equations.focusLoss(beam.getMultiblock().length(), particles));
//                                    }
//                                }

                                DecimalFormat df = new DecimalFormat("#.####");
                                DecimalFormat df2 = new DecimalFormat("#.#");

                                Component message = Component.translatable("gui.qmd.particlestack.line",
                                        Component.translatable("gui.qmd.particlestack.name", Component.translatable("qmd.particle." + particles.getParticle().getName() + ".name").withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.AQUA),
                                        Component.translatable("gui.qmd.particlestack.amount", ChatFormatting.WHITE + Units.getSIFormat(particles.getAmount(), "pu")).withStyle(ChatFormatting.YELLOW),
                                        Component.translatable("gui.qmd.particlestack.mean_energy", ChatFormatting.WHITE + Units.getSIFormat(particles.getMeanEnergy(), 3, "eV")).withStyle(ChatFormatting.GREEN),
                                        Component.translatable("gui.qmd.particlestack.focus", ChatFormatting.WHITE + df.format(particles.getFocus())).withStyle(ChatFormatting.DARK_AQUA),

                                        Component.translatable("gui.qmd.particlestack.focus_loss", ChatFormatting.WHITE + df.format(Equations.focusLoss(1, particles))).withStyle(ChatFormatting.DARK_AQUA),
                                        Component.translatable("gui.qmd.particlestack.travel_distance", ChatFormatting.WHITE + df2.format(Equations.travelDistance(particles))).withStyle(ChatFormatting.GREEN)
                                );
                                player.sendSystemMessage(message);
                            } else {
                                player.sendSystemMessage(Component.translatable("gui.qmd.particlestack.empty"));
                            }
                            return InteractionResult.SUCCESS;
                        }
                    }
                }

            }
        }

        return InteractionResult.PASS;
    }
}