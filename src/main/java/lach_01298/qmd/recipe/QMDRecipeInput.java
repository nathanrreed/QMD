package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record QMDRecipeInput(List<SizedChanceItemIngredient> itemIngredients, List<SizedChanceFluidIngredient> fluidIngredients, List<ParticleStack> particleStacks) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return itemIngredients.get(index).getStack();
    }

    public FluidStack getFluid(int index) {
        return fluidIngredients.get(index).getStack();
    }

    public ParticleStack getParticle(int index) {
        return particleStacks.get(index);
    }

    @Override
    public int size() {
        return itemIngredients.size();
    }

    public int fluid_size() {
        return fluidIngredients.size();
    }

    public int particle_size() {
        return particleStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.size(); i++) {
            if (!this.getItem(i).isEmpty()) {
                return false;
            }
        }

        for (int i = 0; i < this.fluid_size(); i++) {
            if (!this.getFluid(i).isEmpty()) {
                return false;
            }
        }

        for (int i = 0; i < this.particle_size(); i++) {
            if (this.getParticle(i) != null) {
                return false;
            }
        }

        return true;
    }
}