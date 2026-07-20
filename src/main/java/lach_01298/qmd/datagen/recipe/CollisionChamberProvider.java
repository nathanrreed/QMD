package lach_01298.qmd.datagen.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeBuilder;
import lach_01298.qmd.recipe.types.CollisionChamberRecipe;
import lach_01298.qmd.util.Util;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CollisionChamberProvider {
    public CollisionChamberProvider(RecipeOutput recipeOutput) {

        //neutron absorption
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.neutron, 1, 0, 5), new ParticleStack(Particles.deuteron), new ParticleStack(Particles.photon), null, null, 0.5, 0, 2220, 30000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antineutron, 1, 0, 5), new ParticleStack(Particles.antideuteron), new ParticleStack(Particles.photon), null, null, 0.5, 0, 2220, 30000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.deuteron, 1, 0, 5), new ParticleStack(Particles.neutron, 1, 0, 5), new ParticleStack(Particles.triton), new ParticleStack(Particles.photon), null, null, 0.5, 0, 6260, 30000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antideuteron, 1, 0, 5), new ParticleStack(Particles.antineutron, 1, 0, 5), new ParticleStack(Particles.antitriton), new ParticleStack(Particles.photon), null, null, 0.5, 0, 6260, 30000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.helion, 1, 0, 5), new ParticleStack(Particles.neutron, 1, 0, 5), new ParticleStack(Particles.alpha), new ParticleStack(Particles.photon), null, null, 0.5, 0, 20600, 30000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antihelion, 1, 0, 5), new ParticleStack(Particles.antineutron, 1, 0, 5), new ParticleStack(Particles.antialpha), new ParticleStack(Particles.photon), null, null, 0.5, 0, 20600, 30000);

        //fusion
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.deuteron), new ParticleStack(Particles.positron), new ParticleStack(Particles.electron_neutrino), null, 0.25, 700, 420, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antideuteron), new ParticleStack(Particles.electron), new ParticleStack(Particles.electron_antineutrino), null, 0.25, 700, 420, 10000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.deuteron, 1, 0, 5), new ParticleStack(Particles.helion), new ParticleStack(Particles.photon), null, null, 0.5, 700, 5490, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antideuteron, 1, 0, 5), new ParticleStack(Particles.antihelion), new ParticleStack(Particles.photon), null, null, 0.5, 700, 5490, 10000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.helion, 1, 0, 5), new ParticleStack(Particles.helion, 1, 0, 5), new ParticleStack(Particles.alpha), new ParticleStack(Particles.proton, 2), null, null, 0.5, 700, 12900, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antihelion, 1, 0, 5), new ParticleStack(Particles.antihelion, 1, 0, 5), new ParticleStack(Particles.antialpha), new ParticleStack(Particles.antiproton, 2), null, null, 0.5, 700, 12900, 10000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.deuteron, 1, 0, 5), new ParticleStack(Particles.triton, 1, 0, 5), new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), null, null, 0.5, 700, 17600, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antideuteron, 1, 0, 5), new ParticleStack(Particles.antitriton, 1, 0, 5), new ParticleStack(Particles.antialpha), new ParticleStack(Particles.antineutron), null, null, 0.5, 700, 17600, 10000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.triton, 1, 0, 5), new ParticleStack(Particles.triton, 1, 0, 5), new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron, 2), null, null, 0.5, 700, 11300, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antitriton, 1, 0, 5), new ParticleStack(Particles.antitriton, 1, 0, 5), new ParticleStack(Particles.antialpha), new ParticleStack(Particles.antineutron, 2), null, null, 0.5, 700, 11300, 10000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.deuteron, 1, 0, 5), new ParticleStack(Particles.helion, 1, 0, 5), new ParticleStack(Particles.alpha), new ParticleStack(Particles.proton), null, null, 0.5, 700, 18400, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antideuteron, 1, 0, 5), new ParticleStack(Particles.antihelion, 1, 0, 5), new ParticleStack(Particles.antialpha), new ParticleStack(Particles.antiproton), null, null, 0.5, 700, 18400, 10000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.helion, 1, 0, 5), new ParticleStack(Particles.triton, 1, 0, 5), new ParticleStack(Particles.alpha), new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), null, 0.5, 700, 12100, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antihelion, 1, 0, 5), new ParticleStack(Particles.antitriton, 1, 0, 5), new ParticleStack(Particles.antialpha), new ParticleStack(Particles.antiproton), new ParticleStack(Particles.antineutron), null, 0.5, 700, 12100, 10000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.deuteron, 1, 0, 5), new ParticleStack(Particles.deuteron, 1, 0, 5), new ParticleStack(Particles.triton), new ParticleStack(Particles.proton), null, null, 0.5, 700, 4030, 10000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antideuteron, 1, 0, 5), new ParticleStack(Particles.antideuteron, 1, 0, 5), new ParticleStack(Particles.antitriton), new ParticleStack(Particles.antiproton), null, null, 0.5, 700, 4030, 10000);

        // antimatter annihrecipeOutput, ilation
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.pion_plus, 4), new ParticleStack(Particles.pion_naught, 4), new ParticleStack(Particles.pion_minus, 4), null, 1.0, 0, 220000, 50000000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.neutron, 1, 0, 5), new ParticleStack(Particles.antineutron, 1, 0, 5), new ParticleStack(Particles.pion_plus, 4), new ParticleStack(Particles.pion_naught, 4), new ParticleStack(Particles.pion_minus, 4), null, 1.0, 0, 223000, 50000000);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.antineutron, 1, 0, 5), new ParticleStack(Particles.pion_plus, 5), new ParticleStack(Particles.pion_naught, 4), new ParticleStack(Particles.pion_minus, 4), null, 1.0, 0, 81800, 50000000);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.neutron, 1, 0, 5), new ParticleStack(Particles.pion_plus, 4), new ParticleStack(Particles.pion_naught, 4), new ParticleStack(Particles.pion_minus, 5), null, 1.0, 0, 81800, 50000000);

        // High energy collrecipeOutput, isions
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.electron, 1, 0, 5), new ParticleStack(Particles.electron, 1, 0, 5), new ParticleStack(Particles.electron_neutrino, 2), new ParticleStack(Particles.muon, 2), new ParticleStack(Particles.muon_antineutrino, 2), null, 0.025);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.positron, 1, 0, 5), new ParticleStack(Particles.positron, 1, 0, 5), new ParticleStack(Particles.electron_antineutrino, 2), new ParticleStack(Particles.antimuon, 2), new ParticleStack(Particles.muon_neutrino, 2), null, 0.025);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.electron, 1, 0, 5), new ParticleStack(Particles.positron, 1, 0, 5), new ParticleStack(Particles.muon), new ParticleStack(Particles.antimuon), null, null, 0.10);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.pion_minus), new ParticleStack(Particles.pion_naught), 0.10);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antiproton, 2), new ParticleStack(Particles.pion_minus), new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.pion_naught), 0.10);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.delta_minus, 1), new ParticleStack(Particles.sigma_plus), new ParticleStack(Particles.kaon_plus), new ParticleStack(Particles.pion_plus), 0.025);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antidelta_minus, 1), new ParticleStack(Particles.antisigma_plus), new ParticleStack(Particles.kaon_minus), new ParticleStack(Particles.pion_minus), 0.025);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.electron, 1, 0, 5), new ParticleStack(Particles.positron, 1, 0, 5), new ParticleStack(Particles.tau), new ParticleStack(Particles.antitau), null, null, 0.025);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.neutron, 1), new ParticleStack(Particles.delta_plus_plus), new ParticleStack(Particles.z_boson), null, 0.025);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antineutron, 1), new ParticleStack(Particles.antidelta_plus_plus), new ParticleStack(Particles.z_boson), null, 0.025);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.glueball), new ParticleStack(Particles.w_plus_boson), new ParticleStack(Particles.w_minus_boson), new ParticleStack(Particles.charmed_eta), 0.025);

        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.proton, 1, 0, 5), new ParticleStack(Particles.higgs_boson, 2), new ParticleStack(Particles.delta_plus_plus), new ParticleStack(Particles.kaon_plus), new ParticleStack(Particles.sigma_minus), 0.025);
        addCollisionRecipe(recipeOutput, new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.antiproton, 1, 0, 5), new ParticleStack(Particles.higgs_boson, 2), new ParticleStack(Particles.antidelta_plus_plus), new ParticleStack(Particles.kaon_minus), new ParticleStack(Particles.antisigma_minus), 0.025);
    }

    public void addCollisionRecipe(RecipeOutput recipeOutput, @Nonnull ParticleStack particleIn1, @Nonnull ParticleStack particleIn2, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3, ParticleStack particleOut4, double crossSection, long minEnergy, long energyReleased, long maxEnergy) {
        particleIn1.setMeanEnergy(minEnergy);
        particleIn2.setMeanEnergy(minEnergy);

        ArrayList<ParticleStack> list = new ArrayList<>();
        if (particleOut1 != null) {
            list.add(particleIn1);
        }
        if (particleOut2 != null) {
            list.add(particleOut2);
        }
        if (particleOut3 != null) {
            list.add(particleOut3);
        }
        if (particleOut4 != null) {
            list.add(particleOut4);
        }

        new QMDRecipeBuilder<>(new CollisionChamberRecipe(List.of(particleIn1, particleIn2), list, maxEnergy, crossSection, energyReleased)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, particleIn1.getParticleString() + "_" + particleIn2.getParticleString()));
    }

    public void addCollisionRecipe(RecipeOutput recipeOutput, @Nonnull ParticleStack particleIn1, @Nonnull ParticleStack particleIn2, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3, ParticleStack particleOut4, double crossSection) {
        double inputMass = 0;
        double outputMass = 0;

        ArrayList<ParticleStack> list = new ArrayList<>();
        if (particleOut1 != null) {
            outputMass += particleOut1.getParticle().getMass() * particleOut1.getAmount();
            list.add(particleIn1);
        }
        if (particleOut2 != null) {
            outputMass += particleOut2.getParticle().getMass() * particleOut2.getAmount();
            list.add(particleOut2);
        }
        if (particleOut3 != null) {
            outputMass += particleOut3.getParticle().getMass() * particleOut3.getAmount();
            list.add(particleOut3);
        }
        if (particleOut4 != null) {
            outputMass += particleOut4.getParticle().getMass() * particleOut4.getAmount();
            list.add(particleOut4);
        }
        inputMass = particleIn1.getParticle().getMass() * particleIn1.getAmount() + particleIn2.getParticle().getMass() * particleIn2.getAmount();
        long energyReleased = (long) (Util.roundToSigFigs((inputMass - outputMass) * 1000, 3));
        long minEnergy = (long) (Util.roundToSigFigs(Math.abs((inputMass - outputMass) * 1000) * 1.1, 3)); // just an arbitrary amount more energy than the minimum possible
        long maxEnergy = (long) (Util.roundToSigFigs(minEnergy * 1.5, 3));

        particleIn1.setMeanEnergy(minEnergy);
        particleIn2.setMeanEnergy(minEnergy);

        new QMDRecipeBuilder<>(new CollisionChamberRecipe(List.of(particleIn1, particleIn2), list, maxEnergy, crossSection, energyReleased)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "he_" + particleIn1.getParticleString() + "_" + particleIn2.getParticleString() + "_" + (int) outputMass));
    }
}