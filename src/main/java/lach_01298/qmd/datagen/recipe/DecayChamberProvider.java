package lach_01298.qmd.datagen.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeBuilder;
import lach_01298.qmd.recipe.types.DecayChamberRecipe;
import lach_01298.qmd.util.Util;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DecayChamberProvider {
    public DecayChamberProvider(RecipeOutput recipeOutput) {
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), new ParticleStack(Particles.electron_antineutrino), new ParticleStack(Particles.electron), 1.0);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antineutron), new ParticleStack(Particles.positron), new ParticleStack(Particles.electron_neutrino), new ParticleStack(Particles.antiproton), 1.0);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.pion_naught), null, new ParticleStack(Particles.photon, 2), null, 0.98);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.antimuon), new ParticleStack(Particles.muon_neutrino), null, 0.99);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.pion_minus), null, new ParticleStack(Particles.muon_antineutrino), new ParticleStack(Particles.muon), 0.99);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.muon), new ParticleStack(Particles.electron_antineutrino), new ParticleStack(Particles.muon_neutrino), new ParticleStack(Particles.electron), 1.0);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antimuon), new ParticleStack(Particles.positron), new ParticleStack(Particles.muon_antineutrino), new ParticleStack(Particles.electron_neutrino), 1.0);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.tau), new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.tau_neutrino), new ParticleStack(Particles.pion_minus), 0.25);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antitau), new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.tau_antineutrino), new ParticleStack(Particles.pion_naught), 0.25);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.kaon_plus), new ParticleStack(Particles.antimuon), new ParticleStack(Particles.muon_neutrino), null, 0.63);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.kaon_minus), null, new ParticleStack(Particles.muon_antineutrino), new ParticleStack(Particles.muon), 0.63);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.kaon_naught), new ParticleStack(Particles.pion_plus), null, new ParticleStack(Particles.pion_minus), 0.77);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antikaon_naught), new ParticleStack(Particles.pion_plus), null, new ParticleStack(Particles.pion_minus), 0.77);


        addDecayRecipe(recipeOutput, new ParticleStack(Particles.w_minus_boson), null, null, new ParticleStack(Particles.pion_minus), 0.32);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.w_plus_boson), new ParticleStack(Particles.pion_plus), null, null, 0.32);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.z_boson), new ParticleStack(Particles.electron_neutrino), null, new ParticleStack(Particles.electron_antineutrino), 0.068);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.higgs_boson), null, new ParticleStack(Particles.bottom_eta), null, 0.57);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.eta), new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.pion_minus), 0.33);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.eta_prime), new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.eta), new ParticleStack(Particles.pion_minus), 0.33);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.charmed_eta), new ParticleStack(Particles.kaon_naught), null, new ParticleStack(Particles.antikaon_naught), 0.07);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.bottom_eta), new ParticleStack(Particles.antitau), null, new ParticleStack(Particles.tau), 0.08);

        new QMDRecipeBuilder<>(new DecayChamberRecipe(new ParticleStack(Particles.triton), List.of(new ParticleStack(Particles.helion), new ParticleStack(Particles.electron_antineutrino), new ParticleStack(Particles.electron)), Long.MAX_VALUE, 1, 19)).save(recipeOutput);
        new QMDRecipeBuilder<>(new DecayChamberRecipe(new ParticleStack(Particles.antitriton), List.of(new ParticleStack(Particles.positron), new ParticleStack(Particles.electron_neutrino), new ParticleStack(Particles.antihelion)), Long.MAX_VALUE, 1, 19)).save(recipeOutput);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.glueball), new ParticleStack(Particles.kaon_plus), null, new ParticleStack(Particles.kaon_minus), 0.33);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.sigma_plus), new ParticleStack(Particles.proton), new ParticleStack(Particles.pion_naught), null, 0.52);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antisigma_plus), null, new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.antiproton), 0.52);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.sigma_minus), null, new ParticleStack(Particles.neutron), new ParticleStack(Particles.pion_minus), 0.99);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antisigma_minus), new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.antineutron), null, 0.99);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.delta_plus_plus), new ParticleStack(Particles.proton), null, new ParticleStack(Particles.pion_plus), 1.0);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antidelta_plus_plus), new ParticleStack(Particles.antiproton), null, new ParticleStack(Particles.pion_minus), 1.0);

        addDecayRecipe(recipeOutput, new ParticleStack(Particles.delta_minus), new ParticleStack(Particles.neutron), null, new ParticleStack(Particles.pion_minus), 1.0);
        addDecayRecipe(recipeOutput, new ParticleStack(Particles.antidelta_minus), new ParticleStack(Particles.antineutron), null, new ParticleStack(Particles.pion_plus), 1.0);

        new QMDRecipeBuilder<>(new DecayChamberRecipe(new ParticleStack(Particles.photon, 1, 1120), List.of(new ParticleStack(Particles.electron), new ParticleStack(Particles.positron)), 230000L, 1.0, -1020L)).save(recipeOutput);
        new QMDRecipeBuilder<>(new DecayChamberRecipe(new ParticleStack(Particles.photon, 1, 233000), List.of(new ParticleStack(Particles.muon), new ParticleStack(Particles.antimuon)), Long.MAX_VALUE, 0.5, -211000L)).save(recipeOutput);
    }

    public void addDecayRecipe(RecipeOutput recipeOutput, ParticleStack particleIn, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3, double crossSection) {
        double outputMass = 0;

        ArrayList<ParticleStack> list = new ArrayList<>();
        if (particleOut1 != null) {
            outputMass += particleOut1.getParticle().getMass() * particleOut1.getAmount();
            list.add(particleOut1);
        }

        if (particleOut2 != null) {
            outputMass += particleOut2.getParticle().getMass() * particleOut2.getAmount();
            list.add(particleOut2);
        }

        if (particleOut3 != null) {
            outputMass += particleOut3.getParticle().getMass() * particleOut3.getAmount();
            list.add(particleOut3);
        }

        long energyReleased = (long) (Util.roundToSigFigs((particleIn.getParticle().getMass() * particleIn.getAmount() - outputMass) * 1000, 3));
        new QMDRecipeBuilder<>(new DecayChamberRecipe(particleIn, list, Long.MAX_VALUE, crossSection, energyReleased)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, particleIn.getParticleString()));
    }
}