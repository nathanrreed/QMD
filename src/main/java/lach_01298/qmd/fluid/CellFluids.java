package lach_01298.qmd.fluid;

import com.google.common.collect.Sets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.Collections;
import java.util.Set;

public class CellFluids {

    static Set<ResourceLocation> cellFluids = Sets.newHashSet();
    static Set<Fluid> currentCellFluids;

    public static boolean addFluid(Fluid fluid) {
        if (fluid == null || fluid == Fluids.EMPTY) {
            return false;
        }
        return cellFluids.add(BuiltInRegistries.FLUID.getKey(fluid));
    }

    public static Set<Fluid> getFluids() {
        if (currentCellFluids == null) {
            Set<Fluid> tmp = Sets.newHashSet();
            for (ResourceLocation fluidName : cellFluids) {
                tmp.add(BuiltInRegistries.FLUID.get(fluidName));
            }
            currentCellFluids = Collections.unmodifiableSet(tmp);
        }
        return currentCellFluids;
    }

    public static boolean hasFluid(Fluid fluid) {
        return cellFluids.contains(BuiltInRegistries.FLUID.getKey(fluid));
    }
}
