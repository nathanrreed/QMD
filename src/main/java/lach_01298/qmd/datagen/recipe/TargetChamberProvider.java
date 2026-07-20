package lach_01298.qmd.datagen.recipe;

import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.util.FluidStackHelper;
import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.enums.MaterialTypes.*;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeBuilder;
import lach_01298.qmd.recipe.types.TargetChamberRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nred.nuclearcraft.helpers.RecipeHelpers.*;
import static com.nred.nuclearcraft.registration.FluidRegistration.GAS_MAP;
import static com.nred.nuclearcraft.registration.ItemRegistration.*;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.fluidTag;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;
import static lach_01298.qmd.item.QMDItems.*;

public class TargetChamberProvider {
    public TargetChamberProvider(RecipeOutput recipeOutput) {
        Map<SizedChanceItemIngredient, SizedChanceItemIngredient> SpallationMaterials = new HashMap<>();

        SpallationMaterials.put(isotope("californium/252", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CALIFORNIUM), 1));
        SpallationMaterials.put(isotope("californium/251", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CALIFORNIUM), 1));
        SpallationMaterials.put(isotope("californium/250", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CALIFORNIUM), 1));
        SpallationMaterials.put(isotope("californium/249", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CALIFORNIUM), 1));
        SpallationMaterials.put(isotope("berkelium/248", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.BERKELIUM), 1));
        SpallationMaterials.put(isotope("berkelium/247", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.BERKELIUM), 1));
        SpallationMaterials.put(isotope("curium/247", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CURIUM), 1));
        SpallationMaterials.put(isotope("curium/246", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CURIUM), 1));
        SpallationMaterials.put(isotope("curium/245", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CURIUM), 1));
        SpallationMaterials.put(isotope("curium/243", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.CURIUM), 1));
        SpallationMaterials.put(isotope("americium/243", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.AMERICIUM), 1));
        SpallationMaterials.put(isotope("americium/242", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.AMERICIUM), 1));
        SpallationMaterials.put(isotope("americium/241", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.AMERICIUM), 1));
        SpallationMaterials.put(isotope("plutonium/242", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.PLUTONIUM), 1));
        SpallationMaterials.put(isotope("plutonium/241", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.PLUTONIUM), 1));
        SpallationMaterials.put(isotope("plutonium/239", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.PLUTONIUM), 1));
        SpallationMaterials.put(isotope("plutonium/238", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.PLUTONIUM), 1));
        SpallationMaterials.put(isotope("neptunium/237", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.NEPTUNIUM), 1));
        SpallationMaterials.put(isotope("neptunium/236", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.NEPTUNIUM), 1));
        SpallationMaterials.put(isotope("uranium/238", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.URANIUM), 1));
        SpallationMaterials.put(isotope("uranium/235", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.URANIUM), 1));
        SpallationMaterials.put(isotope("uranium/234", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.URANIUM), 1));
        SpallationMaterials.put(isotope("uranium/233", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.URANIUM), 1));
        SpallationMaterials.put(dust("protactinium_233", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.PROTACTINIUM), 1));
        SpallationMaterials.put(dust("protactinium_231", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.PROTACTINIUM), 1));
        SpallationMaterials.put(ingot("thorium", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.THORIUM), 1));
        SpallationMaterials.put(dust("radium", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.RADIUM), 1));
        SpallationMaterials.put(dust("polonium", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.POLONIUM), 1));
        SpallationMaterials.put(dust("bismuth", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.BISMUTH), 1));
        SpallationMaterials.put(ingot("lead", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.LEAD), 1));
        SpallationMaterials.put(ingot("mercury", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.MERCURY), 1));
        SpallationMaterials.put(ingot("gold", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.GOLD), 1));
        SpallationMaterials.put(ingot("platinum", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.PLATINUM), 1));
        SpallationMaterials.put(ingot("iridium", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.IRIDIUM), 1));
        SpallationMaterials.put(isotope("iridium/192", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.IRIDIUM), 1));
        SpallationMaterials.put(ingot("osmium", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.OSMIUM), 1));
        SpallationMaterials.put(ingot("tungsten", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.TUNGSTEN), 1));
        SpallationMaterials.put(ingot("hafnium", 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.HAFNIUM), 1));

        //proton reactions
        addQMDBalancedRecipe(recipeOutput, isotope("boron/11", 1), new ParticleStack(Particles.proton, 1, 400), null,
                new ParticleStack(Particles.alpha, 3), null, null, 900, 0.2, 8680);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.proton, 1, 1000), SizedChanceItemIngredient.of(LITHIUM_MAP.get("6"), 1),
                new ParticleStack(Particles.alpha), null, null, 7500, 0.625, 2130);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.proton, 1, 1500), SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 1),
                null, new ParticleStack(Particles.photon), null, 2000, 0.02, 11600);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("tritium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 2400), null, null,
                new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron), null, 3600, 0.5, -764);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/10", 1), new ParticleStack(Particles.proton, 1, 4000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.alpha), null, null, 6000, 0.8, 1150);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("fluorine"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 4000), null, NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.alpha), null, null, 11000, 0.5, 8110);

        addQMDBalancedRecipe(recipeOutput, ingot("copper", 1), new ParticleStack(Particles.proton, 1, 4500), SizedChanceItemIngredient.of(ingots.get(IngotType.ZINC), 1),
                null, new ParticleStack(Particles.photon), null, 5900, 0.04, 7710);

        addQMDBalancedRecipe(recipeOutput, ingot("cobalt", 1), new ParticleStack(Particles.proton, 1, 4600), SizedChanceItemIngredient.of(ingots.get(IngotType.NICKEL), 1),
                null, new ParticleStack(Particles.photon), null, 5600, 0.2, 9530);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("deuterium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 6000), null, null,
                new ParticleStack(Particles.helion), new ParticleStack(Particles.photon), null, 10000, 0.02, 5490);

        addQMDBalancedRecipe(recipeOutput, ingot("osmium", 1), new ParticleStack(Particles.proton, 1, 8300), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.IRIDIUM_192), 1),
                null, new ParticleStack(Particles.neutron), null, 10300, 0.16, -1830);

        addQMDBalancedRecipe(recipeOutput, ingot("manganese", 1), new ParticleStack(Particles.proton, 1, 10000), SizedChanceItemIngredient.of(Items.IRON_INGOT, 1),
                null, new ParticleStack(Particles.photon), null, 19500, 0.02, 10200);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("deuterium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 11000), null, null,
                new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), null, 20500, 0.16, -2230);

        addQMDBalancedRecipe(recipeOutput, ingot("thorium", 1), new ParticleStack(Particles.proton, 1, 11500), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.PROTACTINIUM_231), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 16000, 0.625, -6830);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 1), new ParticleStack(Particles.proton, 1, 12000), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("237"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 16500, 0.32, -6420);

        addQMDBalancedRecipe(recipeOutput, ingot("gold", 1), null, new ParticleStack(Particles.proton, 1, 12500), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("mercury"), FluidStackHelper.INGOT_VOLUME),
                null, new ParticleStack(Particles.neutron, 2), null, 14000, 0.5, -8170);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("242"), 1), new ParticleStack(Particles.proton, 1, 12500), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("241"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 16500, 0.4, -7070);

        addQMDBalancedRecipe(recipeOutput, dust("bismuth", 1), new ParticleStack(Particles.proton, 1, 14000), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1),
                null, new ParticleStack(Particles.photon), null, 19000, 0.02, 4980);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/11", 1), new ParticleStack(Particles.proton, 1, 15500), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1),
                null, new ParticleStack(Particles.photon), null, 26000, 0.02, 16000);

        addQMDBalancedRecipe(recipeOutput, ingot("calcium", 1), new ParticleStack(Particles.proton, 1, 16500), SizedChanceItemIngredient.of(ingots.get(IngotType.POTASSIUM), 1),
                new ParticleStack(Particles.proton, 2), null, null, 25000, 1, -8330);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 17000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1), null,
                new ParticleStack(Particles.alpha, 2), null, null, 26000, 0.032, -10500);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 19000), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1), null,
                new ParticleStack(Particles.alpha), null, new ParticleStack(Particles.proton), 25500, 0.25, -7160);

        addQMDBalancedRecipe(recipeOutput, gem("silicon", 1), new ParticleStack(Particles.proton, 1, 19000), SizedChanceItemIngredient.of(INGOT_MAP.get("aluminum"), 1),
                new ParticleStack(Particles.proton, 2), null, null, 28000, 1, -11600);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 1), new ParticleStack(Particles.proton, 1, 19000), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                null, new ParticleStack(Particles.neutron, 3), null, 23000, 1, -13000);

        addQMDBalancedRecipe(recipeOutput, ingot("sodium", 1), new ParticleStack(Particles.proton, 1, 20000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 28000, 0.5, -12400);

        addQMDBalancedRecipe(recipeOutput, dust("bismuth", 1), new ParticleStack(Particles.proton, 1, 20000), SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1),
                new ParticleStack(Particles.alpha), null, null, 24000, 0.04, 10400);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), new ParticleStack(Particles.proton, 1, 20500), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                null, new ParticleStack(Particles.photon), null, 30000, 0.02, 4830);

        addQMDBalancedRecipe(recipeOutput, ingot("gold", 1), new ParticleStack(Particles.proton, 1, 21000), SizedChanceItemIngredient.of(ingots.get(IngotType.PLATNIUM), 1),
                new ParticleStack(Particles.alpha), null, null, 25000, 0.064, 8490);

        addQMDBalancedRecipe(recipeOutput, isotope("magnesium/26", 1), new ParticleStack(Particles.proton, 1, 23000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 29000, 0.1, -14200);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.proton, 1, 27000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 35000, 0.08, -26300);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/11", 1), new ParticleStack(Particles.proton, 1, 30000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.triton), new ParticleStack(Particles.neutron, 2), null, 33000, 0.05, -31400);

        addQMDBalancedRecipe(recipeOutput, ingot("calcium", 1), null, new ParticleStack(Particles.proton, 1, 30000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("argon"), FluidStackHelper.BUCKET_VOLUME),
                new ParticleStack(Particles.proton, 3), null, null, 43000, 0.20, -14700);

        addQMDBalancedRecipe(recipeOutput, gem("silicon", 1), new ParticleStack(Particles.proton, 1, 32000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.MAGNESIUM_26), 1),
                new ParticleStack(Particles.proton, 3), null, null, 50000, 0.2, -19900);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 33000), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1), null,
                new ParticleStack(Particles.proton), null, new ParticleStack(Particles.deuteron), 46000, 0.125, -10300);

        addQMDBalancedRecipe(recipeOutput, isotope("magnesium/24", 1), new ParticleStack(Particles.proton, 1, 38000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), null, 56000, 0.32, -24100);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.proton, 1, 40000), SizedChanceItemIngredient.of(BORON_MAP.get("11"), 1),
                new ParticleStack(Particles.proton, 2), null, null, 50000, 0.064, -16000);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 40000), null, NCFluid.sizedIngredient(GAS_MAP.get("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.proton), null, new ParticleStack(Particles.deuteron), 65000, 0.05, -20700);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.proton, 1, 40000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 50000, 0.08, -22500);

        addQMDBalancedRecipe(recipeOutput, ingot("copper", 1), new ParticleStack(Particles.proton, 1, 45000), SizedChanceItemIngredient.of(ingots.get(IngotType.NICKEL), 1),
                new ParticleStack(Particles.alpha), null, null, 56000, 0.625, 3760);

        addQMDBalancedRecipe(recipeOutput, ingot("calcium", 1), null, new ParticleStack(Particles.proton, 1, 51000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("chlorine"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.helion), new ParticleStack(Particles.electron_neutrino), new ParticleStack(Particles.proton), 67000, 0.16, -18500);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(BORON_MAP.get("10"), 1),
                new ParticleStack(Particles.proton), null, new ParticleStack(Particles.deuteron), 85000, 0.08, -25200);

        addQMDBalancedRecipe(recipeOutput, dust("radium", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 0.2, 0);

        addQMDBalancedRecipe(recipeOutput, ingot("thorium", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("uranium/233", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("uranium/234", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("uranium/235", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("uranium/238", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("neptunium/237", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("plutonium/239", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("plutonium/241", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("plutonium/242", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("americium/241", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("americium/243", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, isotope("copernicium/291", 1), new ParticleStack(Particles.proton, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 8), null, 600000, 1.0, 0);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.proton, 1, 65000), SizedChanceItemIngredient.of(BORON_MAP.get("10"), 1), null,
                new ParticleStack(Particles.alpha), null, new ParticleStack(Particles.helion), 150000, 0.02, -26900);

        addQMDBalancedRecipe(recipeOutput, ingot("gold", 1), new ParticleStack(Particles.proton, 1, 100000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.IRIDIUM_192), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 200000, 0.02, -6800);

        addQMDBalancedRecipe(recipeOutput, dust("bismuth", 1), new ParticleStack(Particles.proton, 1, 100000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.LIGHT), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 0.4, 0);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.proton, 1, 150000), SizedChanceItemIngredient.of(INGOT_MAP.get("beryllium"), 1),
                new ParticleStack(Particles.proton), null, new ParticleStack(Particles.triton), 1000000, 0.02, -26800);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.proton, 1, 155000), SizedChanceItemIngredient.of(ingots.get(IngotType.SODIUM), 1),
                new ParticleStack(Particles.proton, 3), new ParticleStack(Particles.neutron, 2), null, 170000, 0.02, -38400);

        addQMDBalancedRecipe(recipeOutput, ingot("platinum", 1), new ParticleStack(Particles.proton, 1, 200000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.LIGHT), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 0.02, 0);

        addQMDBalancedRecipe(recipeOutput, ingot("gold", 1), new ParticleStack(Particles.proton, 1, 200000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.LIGHT), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 0.16, 0);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("mercury"), FluidStackHelper.INGOT_VOLUME), new ParticleStack(Particles.proton, 1, 200000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.LIGHT), 1), null,
                null, new ParticleStack(Particles.neutron), null, 600000, 0.02, 0);
        addQMDBalancedRecipe(recipeOutput, ingot("mercury", 1), new ParticleStack(Particles.proton, 1, 200000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.LIGHT), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 0.02, 0);

        addQMDBalancedRecipe(recipeOutput, ingot("lead", 1), new ParticleStack(Particles.proton, 1, 200000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.LIGHT), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 0.25, 0);

        addQMDBalancedRecipe(recipeOutput, ingot("tungsten", 1), new ParticleStack(Particles.proton, 1, 400000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.LIGHT), 1),
                null, new ParticleStack(Particles.neutron), null, 600000, 0.08, 0);

        // Pion production
        for (Map.Entry<SizedChanceItemIngredient, SizedChanceItemIngredient> material : SpallationMaterials.entrySet()) {
            addQMDBalancedRecipe(recipeOutput, material.getKey(), new ParticleStack(Particles.proton, 1, 600000), material.getValue(),
                    new ParticleStack(Particles.pion_plus), null, new ParticleStack(Particles.pion_minus), 5000000, 0.2, -279000);
        }
        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("mercury"), FluidStackHelper.INGOT_VOLUME), new ParticleStack(Particles.proton, 1, 600000), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.MERCURY), 1), null,
                new ParticleStack(Particles.pion_plus), null, new ParticleStack(Particles.pion_minus), 5000000, 0.2, -279000);

        // antiproton production
        for (Map.Entry<SizedChanceItemIngredient, SizedChanceItemIngredient> material : SpallationMaterials.entrySet()) {
            addQMDBalancedRecipe(recipeOutput, material.getKey(), new ParticleStack(Particles.proton, 1, 5630000), material.getValue(),
                    new ParticleStack(Particles.proton), null, new ParticleStack(Particles.antiproton), 20000000, 0.2, -1880000);
        }
        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("mercury"), FluidStackHelper.INGOT_VOLUME), new ParticleStack(Particles.proton, 1, 5630000), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.MERCURY), 1), null,
                new ParticleStack(Particles.proton), null, new ParticleStack(Particles.antiproton), 20000000, 0.2, -1880000);

        // neutron reactions

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("helium_3"), FluidStackHelper.BUCKET_VOLUME), new ParticleStack(Particles.neutron, 1, 0), null, null,
                new ParticleStack(Particles.proton), null, new ParticleStack(Particles.triton), 18000, 0.1, 764);

        addQMDBalancedRecipe(recipeOutput, isotope("beryllium/7", 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(LITHIUM_MAP.get("7"), 1),
                new ParticleStack(Particles.proton), null, null, 10000, 1, 1640);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/10", 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(BORON_MAP.get("11"), 1),
                null, new ParticleStack(Particles.photon), null, 1000, 0.02, 11500);

        addQMDBalancedRecipe(recipeOutput, isotope("sodium/22", 1), null, new ParticleStack(Particles.neutron, 1, 0), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("neon"), FluidStackHelper.BUCKET_VOLUME),
                new ParticleStack(Particles.proton), null, null, 11000, 0.25, 3630);

        addQMDBalancedRecipe(recipeOutput, ingot("cobalt", 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.COBALT_60), 1),
                null, new ParticleStack(Particles.photon), null, 10000, 0.5, 7490);

        addQMDBalancedRecipe(ingotExists(recipeOutput, "iridium"), isotope("iridium/192", 1), new ParticleStack(Particles.neutron, 1, 0), ingot("iridium", 1),
                null, new ParticleStack(Particles.photon), null, 5000, 1, 7770);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("233"), 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.Uranium_234), 1),
                null, new ParticleStack(Particles.photon), null, 5000, 1.0, 6840);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(isotopes.get(IsotopeType.Uranium_234), 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1),
                null, new ParticleStack(Particles.photon), null, 5000, 1.0, 5300);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("237"), 1),
                null, new ParticleStack(Particles.photon), null, 14000, 1, 6580);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("238"), 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("239"), 1),
                null, new ParticleStack(Particles.photon), null, 5000, 1, 5650);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("241"), 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("242"), 1),
                null, new ParticleStack(Particles.photon), null, 5000, 1, 6310);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("241"), 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1),
                null, new ParticleStack(Particles.photon), null, 5000, 1, 5540);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1), new ParticleStack(Particles.neutron, 1, 0), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("243"), 1),
                null, new ParticleStack(Particles.photon), null, 5000, 1, 6370);

        addQMDBalancedRecipe(recipeOutput, dust("sulfur", 1), new ParticleStack(Particles.neutron, 1, 2800), SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 1),
                new ParticleStack(Particles.alpha), null, null, 4000, 0.2, 1530);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.neutron, 1, 3000), null,
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.deuteron), 21000, 0.2, -1470);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("chlorine"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.neutron, 1, 3000), SizedChanceItemIngredient.of(GEM_DUST_MAP.get("sulfur"), 1), null,
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.electron_antineutrino), new ParticleStack(Particles.electron), 13500, 0.2, 2650);

        addQMDBalancedRecipe(recipeOutput, ingot("calcium", 1), null, new ParticleStack(Particles.neutron, 1, 3000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("chlorine"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.electron_neutrino), null, 15500, 0.1, 2050);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.neutron, 1, 4000), null,
                new ParticleStack(Particles.alpha, 2), new ParticleStack(Particles.neutron, 2), null, 14000, 0.5, -1570);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/7", 1), new ParticleStack(Particles.neutron, 1, 5000), null,
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.triton), 17000, 0.25, -2470);

        addQMDBalancedRecipe(recipeOutput, ingot("zinc", 1), new ParticleStack(Particles.neutron, 1, 5500), SizedChanceItemIngredient.of(ingots.get(IngotType.NICKEL), 1),
                new ParticleStack(Particles.alpha), null, null, 13000, 0.25, 3860);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.neutron, 1, 6000), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1), null,
                new ParticleStack(Particles.triton), null, null, 16000, 0.02, -4010);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("242"), 1), new ParticleStack(Particles.neutron, 1, 8500), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("241"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 13000, 1, -6310);

        addQMDBalancedRecipe(recipeOutput, ingot("iridium", 1), new ParticleStack(Particles.neutron, 1, 9000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.IRIDIUM_192), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 19500, 1, -7770);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("239"), 1), new ParticleStack(Particles.neutron, 1, 9000), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("238"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 14000, 0.32, -5650);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("243"), 1), new ParticleStack(Particles.neutron, 1, 9000), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 14500, 1, -5540);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("tritium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.neutron, 1, 10000), null, null,
                new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron, 2), null, 18000, 0.04, -6260);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("237"), 1), new ParticleStack(Particles.neutron, 1, 10000), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 14000, 1, -6580);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("deuterium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.neutron, 1, 11000), null, null,
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron, 2), null, 54000, 0.16, -2230);

        addQMDBalancedRecipe(recipeOutput, ingot("iron", 1), new ParticleStack(Particles.neutron, 1, 11000), SizedChanceItemIngredient.of(ingots.get(IngotType.CHROMIUM), 1),
                new ParticleStack(Particles.alpha), null, null, 22000, 0.1, 324);

        addQMDBalancedRecipe(recipeOutput, ingot("copper", 1), new ParticleStack(Particles.neutron, 1, 11000), SizedChanceItemIngredient.of(ingots.get(IngotType.NICKEL), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 15000, 1.0, -6120);

        addQMDBalancedRecipe(recipeOutput, ingot("chromium", 1), new ParticleStack(Particles.neutron, 1, 12000), SizedChanceItemIngredient.of(ingots.get(IngotType.TITANIUM), 1),
                new ParticleStack(Particles.alpha), null, null, 20000, 0.16, -1210);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/11", 1), new ParticleStack(Particles.neutron, 1, 12500), SizedChanceItemIngredient.of(INGOT_MAP.get("beryllium"), 1),
                new ParticleStack(Particles.triton), null, null, 20000, 0.04, -9560);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.neutron, 1, 13000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.MAGNESIUM_26), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 30000, 0.8, -8270);

        addQMDBalancedRecipe(recipeOutput, isotope("magnesium/24", 1), null, new ParticleStack(Particles.neutron, 1, 14000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("neon"), FluidStackHelper.BUCKET_VOLUME),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 32000, 0.1, -9320);

        addQMDBalancedRecipe(recipeOutput, ingot("calcium", 1), new ParticleStack(Particles.neutron, 1, 14000), SizedChanceItemIngredient.of(ingots.get(IngotType.POTASSIUM), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 24000, 1, -8330);

        addQMDBalancedRecipe(recipeOutput, ingot("zinc", 1), new ParticleStack(Particles.neutron, 1, 14000), SizedChanceItemIngredient.of(Items.COPPER_INGOT, 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 24500, 1, -7710);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.neutron, 1, 15000), SizedChanceItemIngredient.of(LITHIUM_MAP.get("7"), 1),
                new ParticleStack(Particles.triton), null, null, 26000, 0.064, -10400);

        addQMDBalancedRecipe(recipeOutput, ingot("potassium", 1), null, new ParticleStack(Particles.neutron, 1, 15000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("chlorine"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 32000, 0.1, -7220);

        addQMDBalancedRecipe(recipeOutput, ingot("zirconium", 1), new ParticleStack(Particles.neutron, 1, 15000), SizedChanceItemIngredient.of(ingots.get(IngotType.STRONTIUM), 1),
                new ParticleStack(Particles.alpha), null, null, 20000, 0.04, 1760);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/11", 1), new ParticleStack(Particles.neutron, 1, 17000), SizedChanceItemIngredient.of(BORON_MAP.get("10"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 29000, 0.05, -11500);

        addQMDBalancedRecipe(recipeOutput, ingot("sodium", 1), new ParticleStack(Particles.neutron, 1, 17000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 28000, 0.25, -12400);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), new ParticleStack(Particles.neutron, 1, 17000), SizedChanceItemIngredient.of(URANIUM_MAP.get("233"), 1),
                null, new ParticleStack(Particles.neutron, 3), null, 23500, 0.625, -12100);

        addQMDBalancedRecipe(recipeOutput, gem("silicon", 1), new ParticleStack(Particles.neutron, 1, 18000), SizedChanceItemIngredient.of(INGOT_MAP.get("aluminum"), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 27000, 0.8, -11600);

        addQMDBalancedRecipe(recipeOutput, ingot("barium", 1), new ParticleStack(Particles.neutron, 1, 18200), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("caesium_137"), 1),
                new ParticleStack(Particles.proton), null, null, 34000, 0.016, -393);

        addQMDBalancedRecipe(recipeOutput, ingot("nickel", 1), new ParticleStack(Particles.neutron, 1, 19000), SizedChanceItemIngredient.of(Items.IRON_INGOT, 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 30000, 0.8, -6400);

        addQMDBalancedRecipe(ingotExists(recipeOutput, "osmium"), ingot("platinum", 1), new ParticleStack(Particles.neutron, 1, 19000), ingot("osmium", 1),
                new ParticleStack(Particles.alpha), null, null, 27000, 0.025, 8730);

        addQMDBalancedRecipe(recipeOutput, dust("terbium", 1), new ParticleStack(Particles.neutron, 1, 20800), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("europium_155"), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 34000, 0.5, -140);

        addQMDBalancedRecipe(recipeOutput, ingot("zirconium", 1), new ParticleStack(Particles.neutron, 1, 21000), SizedChanceItemIngredient.of(ingots.get(IngotType.YTTRIUM), 1),
                new ParticleStack(Particles.deuteron), null, null, 36000, 0.625, -6130);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.neutron, 1, 22000), SizedChanceItemIngredient.of(INGOT_MAP.get("beryllium"), 1), null,
                new ParticleStack(Particles.alpha, 2), null, null, 35000, 0.02, -12900);

        addQMDBalancedRecipe(recipeOutput, ingot("niobium", 1), new ParticleStack(Particles.neutron, 1, 22000), SizedChanceItemIngredient.of(ingots.get(IngotType.YTTRIUM), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 34000, 0.125, -1930);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 1), new ParticleStack(Particles.neutron, 1, 26000), SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 34000, 0.55, -17800);

        addQMDBalancedRecipe(recipeOutput, ingot("manganese", 1), new ParticleStack(Particles.neutron, 1, 29000), SizedChanceItemIngredient.of(ingots.get(IngotType.CHROMIUM), 1),
                new ParticleStack(Particles.triton), null, null, 46000, 0.625, -9300);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.neutron, 1, 29500), SizedChanceItemIngredient.of(BORON_MAP.get("11"), 1),
                new ParticleStack(Particles.deuteron), null, null, 60000, 0.2, -13700);

        addQMDBalancedRecipe(recipeOutput, ingot("cobalt", 1), new ParticleStack(Particles.neutron, 1, 30000), SizedChanceItemIngredient.of(Items.IRON_INGOT, 1),
                new ParticleStack(Particles.triton), null, null, 50000, 0.4, -8930);

        addQMDBalancedRecipe(recipeOutput, ingot("yttrium", 1), new ParticleStack(Particles.neutron, 1, 30000), SizedChanceItemIngredient.of(ingots.get(IngotType.STRONTIUM), 1),
                new ParticleStack(Particles.deuteron), null, null, 49000, 0.32, -4840);

        addQMDBalancedRecipe(recipeOutput, ingot("sodium", 1), null, new ParticleStack(Particles.neutron, 1, 30000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("neon"), FluidStackHelper.BUCKET_VOLUME),
                new ParticleStack(Particles.triton), null, null, 60000, 0.1, -10700);

        addQMDBalancedRecipe(ingotExists(recipeOutput, "iridium"), ingot("gold", 1), new ParticleStack(Particles.neutron, 1, 30000), ingot("iridium", 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 45000, 0.05, 967);

        addQMDBalancedRecipe(recipeOutput, isotope("magnesium/24", 1), new ParticleStack(Particles.neutron, 1, 35000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron), null, 60000, 0.25, -21900);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("helium"), FluidStackHelper.BUCKET_VOLUME), new ParticleStack(Particles.neutron, 1, 40000), null, null,
                new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron, 2), null, 60000, 0.02, -20600);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.neutron, 1, 44000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron, 3), null, 80000, 0.05, -43100);

        addQMDBalancedRecipe(recipeOutput, isotope("copernicium/291", 1), new ParticleStack(Particles.neutron, 1, 60000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron, 8), null, 1000000, 1, 0);

        addQMDBalancedRecipe(recipeOutput, dust("bismuth", 1), new ParticleStack(Particles.neutron, 1, 70000), SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1),
                new ParticleStack(Particles.triton), null, null, 150000, 0.2, -2680);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.neutron, 1, 105000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron, 3), null, 150000, 0.032, -46800);

        // photon reactions

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("deuterium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.photon, 1, 2500), null, null,
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 14000, 0.02, -2230);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.photon, 1, 5500), null,
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 17000, 0.02, -3700);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("helium_3"), FluidStackHelper.BUCKET_VOLUME), new ParticleStack(Particles.photon, 1, 9000), null, null,
                new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), null, 36000, 0.02, -7720);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("237"), 1), new ParticleStack(Particles.photon, 1, 9750), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                null, new ParticleStack(Particles.neutron), null, 14500, 1.0, -6580);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("239"), 1), new ParticleStack(Particles.photon, 1, 10000), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("238"), 1),
                null, new ParticleStack(Particles.neutron), null, 13500, 0.4, -5650);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("243"), 1), new ParticleStack(Particles.photon, 1, 10000), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1),
                null, new ParticleStack(Particles.neutron), null, 12500, 1, -6360);

        addQMDBalancedRecipe(recipeOutput, ingot("tungsten", 1), new ParticleStack(Particles.photon, 1, 11000), SizedChanceItemIngredient.of(ingots.get(IngotType.HAFNIUM), 1),
                new ParticleStack(Particles.alpha), null, null, 16500, 0.25, 1660);

        addQMDBalancedRecipe(recipeOutput, ingot("zirconium", 1), new ParticleStack(Particles.photon, 1, 11500), SizedChanceItemIngredient.of(ingots.get(IngotType.YTTRIUM), 1),
                new ParticleStack(Particles.proton), null, null, 19000, 0.04, -8350);

        addQMDBalancedRecipe(recipeOutput, ingot("iridium", 1), new ParticleStack(Particles.photon, 1, 12000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.IRIDIUM_192), 1),
                null, new ParticleStack(Particles.neutron), null, 16000, 0.5, -7770);

        addQMDBalancedRecipe(recipeOutput, dust("bismuth", 1), new ParticleStack(Particles.photon, 1, 12000), SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 15000, 0.5, -11200);

        addQMDBalancedRecipe(recipeOutput, ingot("niobium", 1), new ParticleStack(Particles.photon, 1, 13000), SizedChanceItemIngredient.of(ingots.get(IngotType.YTTRIUM), 1),
                new ParticleStack(Particles.alpha), null, null, 21000, 0.02, -1930);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), new ParticleStack(Particles.photon, 1, 13500), SizedChanceItemIngredient.of(URANIUM_MAP.get("233"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 18000, 0.32, -12100);

        addQMDBalancedRecipe(recipeOutput, ingot("iron", 1), new ParticleStack(Particles.photon, 1, 14500), SizedChanceItemIngredient.of(INGOT_MAP.get("manganese"), 1),
                new ParticleStack(Particles.proton), null, null, 21000, 0.125, -10200);

        addQMDBalancedRecipe(recipeOutput, ingot("yttrium", 1), new ParticleStack(Particles.photon, 1, 16000), SizedChanceItemIngredient.of(ingots.get(IngotType.STRONTIUM), 1),
                new ParticleStack(Particles.proton), null, null, 23000, 0.016, -7070);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.photon, 1, 17500), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.MAGNESIUM_26), 1),
                new ParticleStack(Particles.proton), null, null, 23000, 0.032, -8270);

        addQMDBalancedRecipe(recipeOutput, gem("silicon", 1), new ParticleStack(Particles.photon, 1, 18000), SizedChanceItemIngredient.of(INGOT_MAP.get("aluminum"), 1),
                new ParticleStack(Particles.proton), null, null, 23000, 0.08, -11600);

        addQMDBalancedRecipe(recipeOutput, ingot("calcium", 1), new ParticleStack(Particles.photon, 1, 18000), SizedChanceItemIngredient.of(ingots.get(IngotType.POTASSIUM), 1),
                new ParticleStack(Particles.proton), null, null, 22000, 0.05, -8330);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.photon, 1, 18500), null,
                new ParticleStack(Particles.helion), null, new ParticleStack(Particles.triton), 25500, 0.02, -15800);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/7", 1), new ParticleStack(Particles.photon, 1, 18500), SizedChanceItemIngredient.of(LITHIUM_MAP.get("6"), 1),
                null, new ParticleStack(Particles.neutron), null, 24000, 0.02, -7250);

        addQMDBalancedRecipe(recipeOutput, ingot("copper", 1), new ParticleStack(Particles.photon, 1, 19500), SizedChanceItemIngredient.of(ingots.get(IngotType.NICKEL), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 27000, 0.05, -16700);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.photon, 1, 20000), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1), null,
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 27000, 0.02, -12500);

        addQMDBalancedRecipe(recipeOutput, isotope("magnesium/26", 1), new ParticleStack(Particles.photon, 1, 21000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.MAGNESIUM_24), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 28000, 0.04, -18400);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/11", 1), new ParticleStack(Particles.photon, 1, 24500), null,
                new ParticleStack(Particles.alpha, 2), new ParticleStack(Particles.neutron), new ParticleStack(Particles.deuteron), 30500, 0.02, -17400);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.photon, 1, 26000), null,
                new ParticleStack(Particles.alpha, 2), new ParticleStack(Particles.neutron), null, 46000, 0.02, -1570);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.photon, 1, 29000), null, NCFluid.sizedIngredient(GAS_MAP.get("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 41000, 0.02, -23000);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.photon, 1, 31500), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 42500, 0.02, -26300);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.photon, 1, 42500), SizedChanceItemIngredient.of(LITHIUM_MAP.get("6"), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 55000, 0.02, -31900);

        // electron reactions

        addQMDBalancedRecipe(recipeOutput, ingot("iron", 1), new ParticleStack(Particles.electron, 1, 50000), SizedChanceItemIngredient.of(ingots.get(IngotType.CHROMIUM), 1),
                new ParticleStack(Particles.alpha), null, new ParticleStack(Particles.electron), 100000, 0.01, -7610);

        addQMDBalancedRecipe(recipeOutput, ingot("cobalt", 1), new ParticleStack(Particles.electron, 1, 50000), SizedChanceItemIngredient.of(INGOT_MAP.get("manganese"), 1),
                new ParticleStack(Particles.alpha), null, new ParticleStack(Particles.electron), 100000, 0.01, -6940);

        addQMDBalancedRecipe(recipeOutput, ingot("zinc", 1), new ParticleStack(Particles.electron, 1, 50000), SizedChanceItemIngredient.of(ingots.get(IngotType.NICKEL), 1),
                new ParticleStack(Particles.alpha), null, new ParticleStack(Particles.electron), 100000, 0.01, -3960);

        addQMDBalancedRecipe(recipeOutput, ingot("zirconium", 1), new ParticleStack(Particles.electron, 1, 60000), SizedChanceItemIngredient.of(ingots.get(IngotType.YTTRIUM), 1),
                new ParticleStack(Particles.proton), null, new ParticleStack(Particles.electron), 130000, 0.01, -8350);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("233"), 1), new ParticleStack(Particles.electron, 1, 170000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron), 300000, 0.01, 0);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), new ParticleStack(Particles.electron, 1, 170000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron), 300000, 0.01, 0);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("239"), 1), new ParticleStack(Particles.electron, 1, 170000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron), 300000, 0.01, 0);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("237"), 1), new ParticleStack(Particles.electron, 1, 180000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron), 300000, 0.01, 0);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 1), new ParticleStack(Particles.electron, 1, 200000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron), 300000, 0.01, 0);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1), new ParticleStack(Particles.electron, 1, 200000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron), 300000, 0.01, 0);

        addQMDBalancedRecipe(recipeOutput, ingot("thorium", 1), new ParticleStack(Particles.electron, 1, 220000), SizedChanceItemIngredient.of(fissionWastes.get(FissionWasteType.HEAVY), 1),
                null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron), 300000, 0.01, 0);

        //deuteron reactions

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("tritium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.deuteron, 1, 50), null, null,
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 600, 0.5, 17100);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("deuterium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.deuteron, 1, 500), null, null,
                new ParticleStack(Particles.triton), new ParticleStack(Particles.neutron), null, 3000, 0.16, 2740);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.deuteron, 1, 500), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                null, new ParticleStack(Particles.neutron), null, 3000, 0.16, 3380);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.deuteron, 1, 1500), SizedChanceItemIngredient.of(LITHIUM_MAP.get("7"), 1),
                new ParticleStack(Particles.alpha), null, null, 5000, 0.625, 7150);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/11", 1), new ParticleStack(Particles.deuteron, 1, 1500), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1),
                null, new ParticleStack(Particles.neutron), null, 3000, 0.5, 13700);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/7", 1), new ParticleStack(Particles.deuteron, 1, 3500), null,
                new ParticleStack(Particles.alpha, 2), new ParticleStack(Particles.neutron), null, 9000, 1.0, 15100);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.deuteron, 1, 3500), null, NCFluid.sizedIngredient(GAS_MAP.get("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.alpha), null, null, 10000, 0.25, 3110);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.deuteron, 1, 5000), SizedChanceItemIngredient.of(BORON_MAP.get("10"), 1),
                new ParticleStack(Particles.alpha), null, null, 13000, 0.625, -1340);

        addQMDBalancedRecipe(recipeOutput, ingot("cobalt", 1), new ParticleStack(Particles.deuteron, 1, 5000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.COBALT_60), 1),
                new ParticleStack(Particles.proton), null, null, 10000, 0.4, 5270);

        addQMDBalancedRecipe(recipeOutput, isotope("magnesium/24", 1), new ParticleStack(Particles.deuteron, 1, 6000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.alpha), null, null, 11000, 0.4, 1960);

        addQMDBalancedRecipe(recipeOutput, dust("bismuth", 1), new ParticleStack(Particles.deuteron, 1, 10000), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1),
                null, new ParticleStack(Particles.neutron), null, 15000, 0.08, 2760);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("233"), 1), new ParticleStack(Particles.deuteron, 1, 10000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.Uranium_234), 1),
                new ParticleStack(Particles.proton), null, null, 16000, 0.08, 4620);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("241"), 1), new ParticleStack(Particles.deuteron, 1, 10500), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("241"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 19500, 0.5, -2990);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(isotopes.get(IsotopeType.Uranium_234), 1), new ParticleStack(Particles.deuteron, 1, 11000), SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1),
                new ParticleStack(Particles.proton), null, null, 17000, 0.32, 3070);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("238"), 1), new ParticleStack(Particles.deuteron, 1, 11000), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("239"), 1),
                new ParticleStack(Particles.proton), null, null, 17000, 0.2, 3420);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), new ParticleStack(Particles.deuteron, 1, 11500), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                null, new ParticleStack(Particles.neutron), null, 19500, 0.032, 2610);

        addQMDBalancedRecipe(recipeOutput, ingot("osmium", 1), new ParticleStack(Particles.deuteron, 1, 12000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.IRIDIUM_192), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 14500, 1.0, -4050);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("242"), 1), new ParticleStack(Particles.deuteron, 1, 12000), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 16000, 0.5, -3760);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("243"), 1), new ParticleStack(Particles.deuteron, 1, 12000), SizedChanceItemIngredient.of(CURIUM_MAP.get("243"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 15000, 0.2, -3010);

        addQMDBalancedRecipe(recipeOutput, ingot("gold", 1), null, new ParticleStack(Particles.deuteron, 1, 18000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("mercury"), FluidStackHelper.INGOT_VOLUME),
                null, new ParticleStack(Particles.neutron, 3), null, 24000, 0.5, -10400);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 1), new ParticleStack(Particles.deuteron, 1, 24000), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 30000, 1.0, -15200);

        addQMDBalancedRecipe(recipeOutput, ingot("sodium", 1), new ParticleStack(Particles.deuteron, 1, 30000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron), null, 55000, 0.4, -12400);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.deuteron, 1, 34000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron, 2), null, 53000, 0.064, -20600);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.deuteron, 1, 55000), null,
                new ParticleStack(Particles.alpha, 2), new ParticleStack(Particles.neutron), new ParticleStack(Particles.deuteron), 150000, 1.0, -1570);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.deuteron, 1, 56000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.alpha), null, new ParticleStack(Particles.triton), 200000, 0.08, -16300);

        addQMDBalancedRecipe(recipeOutput, ingot("yttrium", 1), new ParticleStack(Particles.deuteron, 1, 60000), SizedChanceItemIngredient.of(ingots.get(IngotType.STRONTIUM), 1),
                new ParticleStack(Particles.alpha), null, null, 200000, 0.064, 7890);

        // antideuteron production

        for (Map.Entry<SizedChanceItemIngredient, SizedChanceItemIngredient> material : SpallationMaterials.entrySet()) {
            addQMDBalancedRecipe(recipeOutput, material.getKey(), new ParticleStack(Particles.deuteron, 1, 11300000), material.getValue(),
                    new ParticleStack(Particles.deuteron), null, new ParticleStack(Particles.antideuteron), 20000000, 0.1, -3750000);
        }

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("mercury"), FluidStackHelper.INGOT_VOLUME), new ParticleStack(Particles.deuteron, 1, 11300000), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.MERCURY), 1), null,
                new ParticleStack(Particles.deuteron), null, new ParticleStack(Particles.antideuteron), 20000000, 0.1, -3750000);

        // triton reactions

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.triton, 1, 1000), SizedChanceItemIngredient.of(BORON_MAP.get("11"), 1),
                null, new ParticleStack(Particles.neutron), null, 7000, 1, 9560);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("tritium"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.triton, 1, 1200), null, null,
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron, 2), null, 3000, 0.1, 10800);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.triton, 1, 2500), SizedChanceItemIngredient.of(BORON_MAP.get("11"), 1),
                new ParticleStack(Particles.alpha), null, null, 7500, 0.625, 3860);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.triton, 1, 8000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 12000, 0.064, -2520);

        //Helion reactions

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.helion, 1, 11000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.deuteron), null, null, 30000, 0.5, 113);

        addQMDBalancedRecipe(recipeOutput, ingot("cobalt", 1), new ParticleStack(Particles.helion, 1, 14000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.COBALT_60), 1),
                new ParticleStack(Particles.proton, 2), null, null, 24000, 0.1, -226);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.helion, 1, 22000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.alpha, 2), null, null, 31000, 0.125, -5690);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.helion, 1, 23000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, 28000, 0.16, 14);

        addQMDBalancedRecipe(recipeOutput, ingot("lead", 1), new ParticleStack(Particles.helion, 1, 23000), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1),
                null, new ParticleStack(Particles.neutron), null, 30000, 0.02, 1060);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("237"), 1), new ParticleStack(Particles.helion, 1, 23500), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("238"), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 30000, 0.02, -1720);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.helion, 1, 30000), null,
                new ParticleStack(Particles.alpha, 2), null, new ParticleStack(Particles.proton), 150000, 0.625, 16900);

        addQMDBalancedRecipe(recipeOutput, isotope("boron/10", 1), new ParticleStack(Particles.helion, 1, 30000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 47000, 0.2, -6570);

        addQMDBalancedRecipe(recipeOutput, dust("bismuth", 1), new ParticleStack(Particles.helion, 1, 30000), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 43000, 0.2, -2730);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.helion, 1, 90000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.alpha, 2), null, null, 165000, 0.16, -1930);

        // alpha reactions

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.alpha, 1, 4000), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1),
                null, new ParticleStack(Particles.neutron), null, 6500, 1.0, 5700);

        addQMDBalancedRecipe(recipeOutput, isotope("magnesium/26", 1), new ParticleStack(Particles.alpha, 1, 4000), SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 1),
                null, new ParticleStack(Particles.neutron), null, 6000, 0.32, 35);

        addQMDBalancedRecipe(recipeOutput, ingot("sodium", 1), new ParticleStack(Particles.alpha, 1, 4500), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.MAGNESIUM_26), 1),
                new ParticleStack(Particles.proton), null, null, 6000, 0.16, 1820);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("fluorine"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.alpha, 1, 6000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1), null,
                null, new ParticleStack(Particles.neutron), null, 11000, 0.25, -1950);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("fluorine"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.alpha, 1, 11000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("neon"), FluidStackHelper.BUCKET_VOLUME),
                new ParticleStack(Particles.proton), null, null, 17500, 0.16, 1670);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/7", 1), new ParticleStack(Particles.alpha, 1, 6500), SizedChanceItemIngredient.of(BORON_MAP.get("10"), 1),
                null, new ParticleStack(Particles.neutron), null, 7600, 0.5, -2790);

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.alpha, 1, 12000), SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 1),
                new ParticleStack(Particles.positron), new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron_neutrino), 17500, 0.4, 568);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.alpha, 1, 14000), null, NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 26000, 0.1, -5330);

        addQMDBalancedRecipe(recipeOutput, ingot("copper", 1), new ParticleStack(Particles.alpha, 1, 16000), SizedChanceItemIngredient.of(ingots.get(IngotType.ZINC), 1),
                new ParticleStack(Particles.proton), null, null, 20000, 0.16, -1540);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("oxygen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.alpha, 1, 18000), null, NCFluid.sizedIngredient(GAS_MAP.get("fluorine"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.positron), new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron_neutrino), 25000, 0.032, -9920);

        addQMDBalancedRecipe(recipeOutput, ingot("osmium", 1), new ParticleStack(Particles.alpha, 1, 19000), SizedChanceItemIngredient.of(ingots.get(IngotType.PLATNIUM), 1),
                null, new ParticleStack(Particles.neutron), null, 27500, 0.025, -8730);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), new ParticleStack(Particles.alpha, 1, 21000), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("238"), 1),
                null, new ParticleStack(Particles.neutron), null, 32000, 0.02, -10900);

        addQMDBalancedRecipe(recipeOutput, dust("ytterbium", 1), new ParticleStack(Particles.alpha, 1, 21200), SizedChanceItemIngredient.of(ingots.get(IngotType.HAFNIUM), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 26600, 1.0, -14800);

        addQMDBalancedRecipe(recipeOutput, ingot("lead", 1), new ParticleStack(Particles.alpha, 1, 26000), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1),
                null, new ParticleStack(Particles.neutron, 2), null, 32000, 1.0, -19500);

        addQMDBalancedRecipe(recipeOutput, isotope("lithium/6", 1), new ParticleStack(Particles.alpha, 1, 27000), SizedChanceItemIngredient.of(BORON_MAP.get("10"), 1),
                null, new ParticleStack(Particles.photon), null, 45000, 0.02, 4460);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("233"), 1), new ParticleStack(Particles.alpha, 1, 27000), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                new ParticleStack(Particles.proton), null, null, 30000, 0.02, -11300);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 1), new ParticleStack(Particles.alpha, 1, 27000), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("239"), 1),
                null, new ParticleStack(Particles.neutron, 3), null, 30000, 0.625, -23100);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("239"), 1), new ParticleStack(Particles.alpha, 1, 28000), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1),
                new ParticleStack(Particles.proton), null, null, 30000, 0.02, -11700);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("nitrogen"), FluidStackHelper.BUCKET_VOLUME / 2), new ParticleStack(Particles.alpha, 1, 30000), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1), null,
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 56000, 0.25, -12500);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), new ParticleStack(Particles.alpha, 1, 30000), SizedChanceItemIngredient.of(NEPTUNIUM_MAP.get("236"), 1),
                new ParticleStack(Particles.triton), null, null, 35000, 0.016, -15500);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 1), new ParticleStack(Particles.alpha, 1, 34500), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("238"), 1),
                null, new ParticleStack(Particles.neutron, 4), null, 42000, 0.128, -28700);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.alpha, 1, 38000), SizedChanceItemIngredient.of(BORON_MAP.get("11"), 1),
                new ParticleStack(Particles.alpha), null, new ParticleStack(Particles.proton), 50000, 0.25, -16000);

        addQMDBalancedRecipe(recipeOutput, ingot("cobalt", 1), new ParticleStack(Particles.alpha, 1, 38000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.COBALT_60), 1),
                new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), null, 54000, 0.16, -20800);

        addQMDBalancedRecipe(recipeOutput, dust("graphite", 1), new ParticleStack(Particles.alpha, 1, 53000), SizedChanceItemIngredient.of(BORON_MAP.get("10"), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 69000, 0.16, -27400);

        addQMDBalancedRecipe(recipeOutput, ingot("beryllium", 1), new ParticleStack(Particles.alpha, 1, 100000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.BERYLLIUM_7), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron, 2), null, 145000, 0.064, -20500);

        // Boron Ion reactions

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(semiconductors.get(SemiconductorType.SILICON_WAFER), 1), new ParticleStack(Particles.boron_ion, 1, 600, 2), SizedChanceItemIngredient.of(semiconductors.get(SemiconductorType.SILICON_P_DOPED), 1),
                null, null, null, 1000, 1, 0);
        addQMDBalancedRecipe(recipeOutput, isotope("lithium/7", 1), new ParticleStack(Particles.boron_ion, 1, 6000), SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron, 2), null, 12000, 0.2, 5010);

        // Ca-48 reactions

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(BERKELIUM_MAP.get("248"), 1), new ParticleStack(Particles.calcium_48_ion, 1, 40000, 2), SizedChanceItemIngredient.of(copernicium.get(CoperniciumType._291), 1),
                new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron_neutrino, 3), 50000, 0.02, -24400);

        // Electron antineutrino reactions
        // Inverse beta + decay

        addQMDBalancedRecipe(ingotExists(recipeOutput, "osmium"), isotope("iridium/192", 1), new ParticleStack(Particles.electron_antineutrino, 1, 0), ingot("osmium", 1),
                new ParticleStack(Particles.positron), null, null, 30000, 0.01, 25);

        addQMDBalancedRecipe(recipeOutput, ingot("nickel", 1), new ParticleStack(Particles.electron_antineutrino, 1, 200), SizedChanceItemIngredient.of(Items.IRON_INGOT, 1),
                new ParticleStack(Particles.positron, 2), new ParticleStack(Particles.electron_neutrino), null, 10900, 0.01, -117);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1), new ParticleStack(Particles.electron_antineutrino, 1, 300), SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("242"), 1),
                new ParticleStack(Particles.positron), null, null, 30000, 0.01, -271);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(CURIUM_MAP.get("243"), 1), new ParticleStack(Particles.electron_antineutrino, 1, 1100), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("243"), 1),
                new ParticleStack(Particles.positron), null, null, 30000, 0.01, -1010);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(BERKELIUM_MAP.get("247"), 1), new ParticleStack(Particles.electron_antineutrino, 1, 1100), SizedChanceItemIngredient.of(CURIUM_MAP.get("247"), 1),
                new ParticleStack(Particles.positron), null, null, 30000, 0.01, -1070);

        // Electron neutrino reactions
        // Inverse beta - decay

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(CURIUM_MAP.get("247"), 1), new ParticleStack(Particles.electron_neutrino, 1, 0), SizedChanceItemIngredient.of(BERKELIUM_MAP.get("247"), 1),
                null, null, new ParticleStack(Particles.electron), 30000, 0.01, 44);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(AMERICIUM_MAP.get("243"), 1), new ParticleStack(Particles.electron_neutrino, 1, 100), SizedChanceItemIngredient.of(CURIUM_MAP.get("243"), 1),
                null, null, new ParticleStack(Particles.electron), 30000, 0.01, -8);

        addQMDBalancedRecipe(recipeOutput, SizedChanceItemIngredient.of(PLUTONIUM_MAP.get("242"), 1), new ParticleStack(Particles.electron_neutrino, 1, 800), SizedChanceItemIngredient.of(AMERICIUM_MAP.get("242"), 1),
                null, null, new ParticleStack(Particles.electron), 30000, 0.01, -751);

        addQMDBalancedRecipe(recipeOutput, ingot("osmium", 1), new ParticleStack(Particles.electron_neutrino, 1, 1100), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.IRIDIUM_192), 1),
                null, null, new ParticleStack(Particles.electron), 30000, 0.01, -1050);

        // Pion reactions

        addQMDBalancedRecipe(recipeOutput, ingot("aluminum", 1), new ParticleStack(Particles.pion_minus, 1, 150000), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.SODIUM_22), 1),
                new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 250000, 0.025, 87500);

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("argon"), FluidStackHelper.BUCKET_VOLUME), new ParticleStack(Particles.pion_plus, 1, 70000), null, NCFluid.sizedIngredient(QMD_FLUIDS.get("chlorine"), FluidStackHelper.BUCKET_VOLUME / 2),
                new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), null, 320000, 0.04, 114000);


        // Antiproton reactions
        // antiproton anhilation
        for (Map.Entry<SizedChanceItemIngredient, SizedChanceItemIngredient> material : SpallationMaterials.entrySet()) {
            addQMDBalancedRecipe(recipeOutput, material.getKey(), new ParticleStack(Particles.antiproton, 1), material.getValue(),
                    new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
        }

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("mercury"), FluidStackHelper.INGOT_VOLUME), new ParticleStack(Particles.antiproton, 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.MERCURY), 1), null,
                new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);


        // Antideuteron reactions
        // antideuteron anhilation
        for (Map.Entry<SizedChanceItemIngredient, SizedChanceItemIngredient> material : SpallationMaterials.entrySet()) {
            addQMDBalancedRecipe(recipeOutput, material.getKey(), new ParticleStack(Particles.antideuteron, 1), material.getValue(),
                    new ParticleStack(Particles.pion_plus, 4), new ParticleStack(Particles.pion_naught, 4), new ParticleStack(Particles.pion_minus, 4), 10000000, 1, 2090000);
        }

        addQMDBalancedRecipe(recipeOutput, null, SizedChanceFluidIngredient.of(fluidTag("mercury"), FluidStackHelper.INGOT_VOLUME), new ParticleStack(Particles.antideuteron, 1), SizedChanceItemIngredient.of(spallationWastes.get(SpallationWasteType.MERCURY), 1), null,
                new ParticleStack(Particles.pion_plus, 4), new ParticleStack(Particles.pion_naught, 4), new ParticleStack(Particles.pion_minus, 4), 10000000, 1, 2090000);
    }

    /**
     * old method without fluids
     */
    public void addQMDBalancedRecipe(RecipeOutput recipeOutput, SizedChanceItemIngredient itemInput, ParticleStack inputParticle, SizedChanceItemIngredient itemOutput, ParticleStack outputParticle1, ParticleStack outputParticle2, ParticleStack outputParticle3, int maxEnergy, double crossSection, int energyRelased) {
        List<ParticleStack> inParticle = List.of();
        String name = "";

        if (inputParticle != null) {
            int recipeAmount = (int) (QMDConstants.moleAmount / crossSection);
            inputParticle.setAmount(inputParticle.getAmount() * recipeAmount);
            inParticle = List.of(inputParticle);
            name += inputParticle.getParticleString() + "_" + inputParticle.getMeanEnergy() + "_";
        }

        List<SizedChanceItemIngredient> inItem = List.of();
        if (itemInput != null) {
            inItem = List.of(itemInput);
            name += itemInput.getStackRaw().getHoverName().getString().replace("Empty Tag: c:", "").replaceAll("(dusts/|ingots/|isotopes/)", "").replace("/", "_").replaceFirst(".*\\.", "") + "_";
        }

        List<SizedChanceItemIngredient> outItem = List.of();
        if (itemOutput != null) {
            outItem = List.of(itemOutput);
            name += BuiltInRegistries.ITEM.getKey(itemOutput.getStackRaw().getItem()).getPath() + "_";
        }

        ArrayList<ParticleStack> list = new ArrayList<>();
        if (outputParticle1 != null) {
            list.add(outputParticle1);
            name += outputParticle1.getParticleString() + "_" + outputParticle1.getMeanEnergy() + "_";
        }
        if (outputParticle2 != null) {
            list.add(outputParticle2);
            name += outputParticle2.getParticleString() + "_" + outputParticle2.getMeanEnergy() + "_";
        }
        if (outputParticle3 != null) {
            list.add(outputParticle3);
            name += outputParticle3.getParticleString() + "_" + outputParticle3.getMeanEnergy() + "_";
        }

        new QMDRecipeBuilder<>(new TargetChamberRecipe(inItem, List.of(), inParticle, outItem, List.of(), list, maxEnergy, crossSection, energyRelased)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, name.replaceFirst("_$", "")));
    }

    public void addQMDBalancedRecipe(RecipeOutput recipeOutput, SizedChanceItemIngredient itemInput, SizedChanceFluidIngredient fluidInput, ParticleStack inputParticle, SizedChanceItemIngredient itemOutput, SizedChanceFluidIngredient fluidOutput, ParticleStack outputParticle1, ParticleStack outputParticle2, ParticleStack outputParticle3, int maxEnergy, double crossSection, int energyReleased) {
        List<ParticleStack> inParticle = List.of();
        String name = "";
        if (inputParticle != null) {
            int recipeAmount = (int) (QMDConstants.moleAmount / crossSection);
            inputParticle.setAmount(inputParticle.getAmount() * recipeAmount);
            inParticle = List.of(inputParticle);
            name += inputParticle.getParticleString() + "_" + inputParticle.getMeanEnergy() + "_";
        }

        List<SizedChanceItemIngredient> inItem = List.of();
        if (itemInput != null) {
            inItem = List.of(itemInput);
        }

        List<SizedChanceItemIngredient> outItem = List.of();
        if (itemOutput != null) {
            outItem = List.of(itemOutput);
            name += BuiltInRegistries.ITEM.getKey(itemOutput.getStackRaw().getItem()).getPath() + "_";
        }

        List<SizedChanceFluidIngredient> inputfluid = List.of();
        if (fluidInput != null) {
            inputfluid = List.of(fluidInput);
        }

        List<SizedChanceFluidIngredient> outputfluid = List.of();
        if (fluidOutput != null) {
            outputfluid = List.of(fluidOutput);
        }

        ArrayList<ParticleStack> list = new ArrayList<>();
        if (outputParticle1 != null) {
            list.add(outputParticle1);
            name += outputParticle1.getParticleString() + "_" + outputParticle1.getMeanEnergy() + "_";
        }
        if (outputParticle2 != null) {
            list.add(outputParticle2);
            name += outputParticle2.getParticleString() + "_" + outputParticle2.getMeanEnergy() + "_";
        }
        if (outputParticle3 != null) {
            list.add(outputParticle3);
            name += outputParticle3.getParticleString() + "_" + outputParticle3.getMeanEnergy() + "_";
        }

        new QMDRecipeBuilder<>(new TargetChamberRecipe(inItem, inputfluid, inParticle, outItem, outputfluid, list, maxEnergy, crossSection, energyReleased)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, name.replaceFirst("_$", "")));
    }
}