package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.info.NCFluid;
import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import lach_01298.qmd.enums.MaterialTypes.*;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static lach_01298.qmd.block.QMDBlocks.*;
import static lach_01298.qmd.enums.BlockTypes.CoolerType;
import static lach_01298.qmd.enums.BlockTypes.LampType;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;
import static lach_01298.qmd.item.QMDItems.*;

public class QMDLanguageProvider extends LanguageProvider {
    public QMDLanguageProvider(DataGenerator gen, String locale) {
        super(gen.getPackOutput(), QMD.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        patchouli();
        sounds();
        deathMessages();
        creativeTabs();
        info();
        particleStack();
        particles();
        fluids();
        menus();
        recipeViewer();
        descriptions();
        messages();
        blocks();
        items();
        multiblock_validation();
    }

    private void patchouli() {
        add("qmd.guide_book.name", "QMD Guide");
        add("qmd.guide_book.edition", "Version 1.5.1");
        add("qmd.guide_book.desc", "This is a Guide to Quantum Minecraft Dynamics.");
    }

    private void sounds() {
        add("neutral.qmd.gluon_gun_start", "Gluon Gun Starts Firing");
        add("neutral.qmd.gluon_gun_stop", "Gluon Gun Stops Firing");
        add("neutral.qmd.gluon_gun", "Gluon Gun Firing");
        add("neutral.qmd.lepton_cannon", "Lepton Cannon Firing");
    }

    private void deathMessages() {
        add("death.attack.gluon_gun", "%1$s was vaporized by %2$s with a Gluon Gun");
        add("death.attack.lepton_cannon", "%1$s was killed by %2$s with a Lepton Cannon");
        add("death.attack.antimatter_launcher", "%1$s was annihilated by %2$s with a Antimatter Launcher");
        add("death.attack.antimatter_annihilation", "%1$s got too curious and was annihilated for it");
        add("death.attack.self_poisoning", "%1$s chemically poisoned themselves");
    }

    private void creativeTabs() {
        add("itemGroup.qmd.items", "Quantum Minecraft Dynamics Items");
        add("itemGroup.qmd.blocks", "Quantum Minecraft Dynamics Blocks");
        add("itemGroup.qmd.multiblocks", "QMD Multiblocks");
    }

    private void info() {
        add("info.qmd.rf_cavity.voltage", "Accelerating Voltage: %s kV");
        add("info.qmd.accelerator_magnet.strength", "Magnet Strength: %s T");
        add("info.qmd.item.efficiency", "Power Efficiency: %s");
        add("info.qmd.item.heat", "Heat Produced: %s H/t");
        add("info.qmd.item.power", "Base Power: %s RF/t");
        add("info.qmd.item.max_temp", "Maximum Operating Temperature: %s K");
        add("info.qmd.item.energy_used", "Energy Used: %s");
        add("info.qmd.item.amount", "Amount: %s / %s");

        add("info.qmd.item.mode.switch", "Switched Mode to: %s");
        add("info.qmd.item.mode", "Mode: %s");
        add("info.qmd.item.mode.silk_touch", "Silk Touch");
        add("info.qmd.item.mode.breaking", "Breaking");

        add("info.qmd.ion_source.output_multiplier", "Particle Output Amount: x%s");
        add("info.qmd.ion_source.focus", "Output Focus: %s");

        add("info.qmd.particle_chamber.detector.efficiency", "Particle Efficiency: %s");
        add("info.qmd.particle_chamber.detector.power", "Base Power: %s RF/t");

        add("info.qmd.liquefier.compressor.energy_efficiency", "Energy Efficiency: %s");
        add("info.qmd.liquefier.compressor.heat_efficiency", "Heat Efficiency: %s");

        add("info.qmd.beamline.attenuation", "Attenuation Rate: %s");
        add("info.qmd.item.drill.desc", "Mines a %s x %s area.");
        add("info.qmd.liquefier_nozzle.speed", "Max Recipe Rate: %s recipes/t/bar");
    }

    private void particleStack() {
        add("gui.qmd.particlestack.name", "Type: %s");
        add("gui.qmd.particlestack.amount", "Amount: %s");
        add("gui.qmd.particlestack.mean_energy", "Energy: %s");
        add("gui.qmd.particlestack.focus", "Focus: %s");
        add("gui.qmd.particlestack.focus_loss", "Focus Loss: %s/Block");
        add("gui.qmd.particlestack.travel_distance", "Travel Distance: %s Blocks");
        add("gui.qmd.particlestack.empty", "No Particles");

        add("gui.qmd.particlestack.line", "%s %s %s %s\n%s %s");
    }

    private void particles() {
        add("qmd.particle.none.name", "None");
        add("qmd.particle.up_quark.name", "Up Quark");
        add("qmd.particle.antiup_quark.name", "Anti-Up Quark");
        add("qmd.particle.down_quark.name", "Down Quark");
        add("qmd.particle.antidown_quark.name", "Anti-Down Quark");
        add("qmd.particle.charm_quark.name", "Charm Quark");
        add("qmd.particle.anticharm_quark.name", "Anti-Charm Quark");
        add("qmd.particle.strange_quark.name", "Strange Quark");
        add("qmd.particle.antistrange_quark.name", "Anti-Strange Quark");
        add("qmd.particle.top_quark.name", "Top Quark");
        add("qmd.particle.antitop_quark.name", "Anti-Top Quark");
        add("qmd.particle.bottom_quark.name", "Bottom Quark");
        add("qmd.particle.antibottom_quark.name", "Anti-Bottom Quark");
        add("qmd.particle.electron.name", "Electron");
        add("qmd.particle.positron.name", "Positron");
        add("qmd.particle.electron_neutrino.name", "Electron Neutrino");
        add("qmd.particle.electron_antineutrino.name", "Electron Antineutrino");
        add("qmd.particle.muon.name", "Muon");
        add("qmd.particle.antimuon.name", "Anti-Muon");
        add("qmd.particle.muon_neutrino.name", "Muon Neutrino");
        add("qmd.particle.muon_antineutrino.name", "Muon Antineutrino");
        add("qmd.particle.tau.name", "Tau");
        add("qmd.particle.antitau.name", "Anti-Tau");
        add("qmd.particle.tau_neutrino.name", "Tau Neutrino");
        add("qmd.particle.tau_antineutrino.name", "Tau Antineutrino");
        add("qmd.particle.photon.name", "Photon");
        add("qmd.particle.gluon.name", "Gluon");
        add("qmd.particle.w_plus_boson.name", "W+ Boson");
        add("qmd.particle.w_minus_boson.name", "W- Boson");
        add("qmd.particle.z_boson.name", "Z Boson");
        add("qmd.particle.higgs_boson.name", "Higgs Boson");
        add("qmd.particle.proton.name", "Proton");
        add("qmd.particle.antiproton.name", "Anti-Proton");
        add("qmd.particle.neutron.name", "Neutron");
        add("qmd.particle.antineutron.name", "Anti-Neutron");
        add("qmd.particle.deuteron.name", "Deuteron");
        add("qmd.particle.antideuteron.name", "Anti-Deuteron");
        add("qmd.particle.alpha.name", "Alpha Particle");
        add("qmd.particle.antialpha.name", "Anti-Alpha Particle");
        add("qmd.particle.pion_plus.name", "Pion +");
        add("qmd.particle.pion_naught.name", "Pion 0");
        add("qmd.particle.pion_minus.name", "Pion -");
        add("qmd.particle.triton.name", "Triton");
        add("qmd.particle.antitriton.name", "Anti-Triton");
        add("qmd.particle.helion.name", "Helion");
        add("qmd.particle.antihelion.name", "Anti-Helion");
        add("qmd.particle.boron_ion.name", "Boron Ion");
        add("qmd.particle.calcium_48_ion.name", "Calcium-48 Ion");
        add("qmd.particle.kaon_plus.name", "Kaon +");
        add("qmd.particle.kaon_minus.name", "Kaon -");
        add("qmd.particle.kaon_naught.name", "Kaon 0");
        add("qmd.particle.antikaon_naught.name", "Anti-Kaon 0");
        add("qmd.particle.eta.name", "Eta Meson");
        add("qmd.particle.eta_prime.name", "Eta Prime Meson");
        add("qmd.particle.charmed_eta.name", "Charmed Eta Meson");
        add("qmd.particle.bottom_eta.name", "Bottom Eta Meson");
        add("qmd.particle.glueball.name", "Glueball");
        add("qmd.particle.sigma_plus.name", "Sigma +");
        add("qmd.particle.antisigma_plus.name", "Anti-Sigma +");
        add("qmd.particle.sigma_naught.name", "Sigma 0");
        add("qmd.particle.antisigma_naught.name", "Anti-Sigma 0");
        add("qmd.particle.sigma_minus.name", "Sigma -");
        add("qmd.particle.antisigma_minus.name", "Anti-Sigma -");
        add("qmd.particle.delta_plus_plus.name", "Delta ++");
        add("qmd.particle.antidelta_plus_plus.name", "Anti-Delta ++");
        add("qmd.particle.delta_minus.name", "Delta -");
        add("qmd.particle.antidelta_minus.name", "Anti-Delta -");

        add("qmd.particle.up_quark.desc", "The Up Quark is the lightest quark. Up and Down quarks combine to form Protons and Neutrons");
        add("qmd.particle.antiup_quark.desc", "The Anti-Up Quark is the antimatter partner of the Up Quark.");
        add("qmd.particle.down_quark.desc", "The Down Quark is the second lightest quark. Up and Down quarks combine to form Protons and Neutrons");
        add("qmd.particle.antidown_quark.desc", "The Anti-Down Quark is the antimatter partner of the Up Down.");
        add("qmd.particle.charm_quark.desc", "The Charm Quark is a heavy version of the Up Quark.");
        add("qmd.particle.anticharm_quark.desc", "The Anti-Charm Quark is the antimatter partner of the Charm Quark.");
        add("qmd.particle.strange_quark.desc", "The Strange Quark is a heavy version of the Down Quark.");
        add("qmd.particle.antistrange_quark.desc", "The Anti-Strange Quark is the antimatter partner of the Strange Quark.");
        add("qmd.particle.top_quark.desc", "The Top Quark is a very heavy version of the Up Quark.");
        add("qmd.particle.antitop_quark.desc", "The Anti-Top Quark is the antimatter partner of the Top Quark.");
        add("qmd.particle.bottom_quark.desc", "The Bottom Quark is a very heavy version of the Down Quark.");
        add("qmd.particle.antibottom_quark.desc", "The Anti-Bottom Quark is the antimatter partner of the Bottom Quark.");
        add("qmd.particle.electron.desc", "The Electron is lightest charged lepton. It is commonly found in orbitals around nuclei, forming atoms.");
        add("qmd.particle.positron.desc", "The positron is the Antimatter particle of the electron. When a electron and positron meet the annihilate resulting in all there mass being converted into energy in the form of 2 gamma rays.");
        add("qmd.particle.electron_neutrino.desc", "The Electron Neutrino is the neutrino partner of the Electron.");
        add("qmd.particle.electron_antineutrino.desc", "The Electron Antineutrino is the antimatter partner of the Electron Neutrino.");
        add("qmd.particle.muon.desc", "The Muon is basically a heavy electron.");
        add("qmd.particle.antimuon.desc", "The Anti-Muon is the antimatter partner of the Muon.");
        add("qmd.particle.muon_neutrino.desc", "The Muon Neutrino is the neutrino partner of the Muon.");
        add("qmd.particle.muon_antineutrino.desc", "The Muon Antineutrino is the antimatter partner of the Muon Neutrino.");
        add("qmd.particle.tau.desc", "The Tau is basically a really heavy electron");
        add("qmd.particle.antitau.desc", "The Anti-Tau is the antimatter partner of the Tau.");
        add("qmd.particle.tau_neutrino.desc", "The Tau Neutrino is the neutrino partner of the Tau.");
        add("qmd.particle.tau_antineutrino.desc", "The Tau Antineutrino is the antimatter partner of the Tau Neutrino.");
        add("qmd.particle.photon.desc", "Photons are the particles that make up light. They are the carries of the electromagnetic force. High energy Photons are called gamma rays.");
        add("qmd.particle.gluon.desc", "Gluons are the carries of the strong force. The strong force is what keeps quarks bound together to create composite particles like protons and neutrons.");
        add("qmd.particle.w_plus_boson.desc", "The Z and W bosons are the carries of the weak force. The weak force is what allows some particles to decay and is responsible for beta decay.");
        add("qmd.particle.w_minus_boson.desc", " The Z and W bosons are the carries of the weak force. The weak force is what allows some particles to decay and is responsible for beta decay.");
        add("qmd.particle.z_boson.desc", "The Z and W bosons are the carries of the weak force. The weak force is what allows some particles to decay and is responsible for beta decay.");
        add("qmd.particle.higgs_boson.desc", "The Higgs Boson is the boson of the Higgs Field which is responsible for giving particles their mass.");
        add("qmd.particle.proton.desc", "The Proton is a Nucleon along with the Neutron make up the nucleus of atoms.");
        add("qmd.particle.antiproton.desc", "The Anti-Proton is the antimatter partner of the Proton.");
        add("qmd.particle.neutron.desc", "The Neutron is a Nucleon along with the Protons make up the nucleus of atoms. The Neutron is used in nuclear fission to split fissile nuclei.");
        add("qmd.particle.antineutron.desc", "The Anti-Neutron is the antimatter partner of the Neutron.");
        add("qmd.particle.deuteron.desc", "The Deuteron is the nucleus of a Deuterium Atom.");
        add("qmd.particle.antideuteron.desc", "The Anti-Deuteron is the antimatter partner of the Deuteron.");
        add("qmd.particle.alpha.desc", "Alpha Particle is another name for the nucleus of helium 4. It is commonly released in the decay of heavy elements like uranium and plutonium.");
        add("qmd.particle.antialpha.desc", "The Anti-Alpha Particle is the antimatter partner of the Alpha Particle.");
        add("qmd.particle.pion_plus.desc", "Pions are the particles that are responsible for holding nuclei together. Although not colored themselves they distribute the \"residual\" strong force to keep nuclei together.");
        add("qmd.particle.pion_naught.desc", "Pions are the particles that are responsible for holding nuclei together. Although not colored themselves they distribute the \"residual\" strong force to keep nuclei together.");
        add("qmd.particle.pion_minus.desc", "Pions are the particles that are responsible for holding nuclei together. Although not colored themselves they distribute the \"residual\" strong force to keep nuclei together.");
        add("qmd.particle.triton.desc", "The Triton is the nucleus of a Tritium Atom.");
        add("qmd.particle.antitriton.desc", "The Anti-Triton is the antimatter partner of the Triton.");
        add("qmd.particle.helion.desc", "The Helion is the nucleus of a Helium-3 Atom.");
        add("qmd.particle.antihelion.desc", "The Anti-Helion is the antimatter partner of the Helion.");

        add("qmd.particle.boron_ion.desc", "A Boron atom with one electron missing.");
        add("qmd.particle.calcium_48_ion.desc", "A Calcium-48 atom with one electron missing. This is a particularly neutron rich atom useful for creating Super heavy elements.");

        add("qmd.particle.kaon_plus.desc", "The Kaon + is a meson with strangeness of 1.");
        add("qmd.particle.kaon_naught.desc", "The Kaon 0 is a meson with strangeness of 1.");
        add("qmd.particle.antikaon_naught.desc", "The Anti-Kaon 0 is a meson with strangeness of -1.");
        add("qmd.particle.kaon_minus.desc", "The Kaon - is a meson with strangeness of -1.");

        add("qmd.particle.eta.desc", "Eta Mesons are flavourless mesons meaning their flavor numbers like strangeness and isospin are 0.");
        add("qmd.particle.eta_prime.desc", "Eta Mesons are flavorless mesons meaning their flavor numbers like strangeness and isospin are 0.");
        add("qmd.particle.charmed_eta.desc", "Eta Mesons are flavorless mesons meaning their flavor numbers like strangeness and isospin are 0.");
        add("qmd.particle.bottom_eta.desc", "Eta Mesons are flavorless mesons meaning their flavor numbers like strangeness and isospin are 0.");
        add("qmd.particle.glueball.desc", "Glueballs are particle made entirely out of gluons.");

        add("qmd.particle.sigma_plus.desc", "Sigma Baryons contain one Strange Quark. They are heavier than Protons and Neutrons.");
        add("qmd.particle.antisigma_plus.desc", "Anti-Sigma Baryons contain one Anti-Strange Quark. They are heavier than Anti-Protons and Anti-Neutrons.");
        add("qmd.particle.sigma_naught.desc", "Sigma Baryons contain one Strange Quark. They are heavier than Protons and Neutrons.");
        add("qmd.particle.antisigma_naught.desc", "Anti-Sigma Baryons contain one Anti-Strange Quark. They are heavier than Anti-Protons and Anti-Neutrons.");
        add("qmd.particle.sigma_minus.desc", "Sigma Baryons contain one Strange Quark. They are heavier than Protons and Neutrons.");
        add("qmd.particle.antisigma_minus.desc", "Anti-Sigma Baryons contain one Anti-Strange Quark. They are heavier than Anti-Protons and Anti-Neutrons.");

        add("qmd.particle.delta_plus_plus.desc", "Delta ++ is a baryon containing three Up Quarks. They quickly decay via the strong force.");
        add("qmd.particle.antidelta_plus_plus.desc", "Anti-Delta ++ is a baryon containing three Anti-Up Quarks. They quickly decay via the strong force.");

        add("qmd.particle.delta_minus.desc", "Delta - is a baryon containing three Down Quarks. They quickly decay via the strong force.");
        add("qmd.particle.antidelta_minus.desc", "Anti-Delta - is a baryon containing three Anti-Down Quarks. They quickly decay via the strong force.");

    }

    private void fluids() {
        fluidAndBucket(QMD_FLUIDS.get("antideuterium"), "Anti-Deuterium");
        fluidAndBucket(QMD_FLUIDS.get("antihelium3"), "Anti-Helium-3");
        fluidAndBucket(QMD_FLUIDS.get("antihelium"), "Anti-Helium");
        fluidAndBucket(QMD_FLUIDS.get("antihydrogen"), "Anti-Hydrogen");
        fluidAndBucket(QMD_FLUIDS.get("antitritium"), "Anti-Tritium");
        fluidAndBucket(QMD_FLUIDS.get("argon"), "Argon");
        fluidAndBucket(QMD_FLUIDS.get("bismuth"), "Molten Bismuth");
        fluidAndBucket(QMD_FLUIDS.get("calcium"), "Molten Calcium");
        fluidAndBucket(QMD_FLUIDS.get("carbon"), "Sublimated Carbon");
        fluidAndBucket(QMD_FLUIDS.get("chlorine"), "Chlorine");
        fluidAndBucket(QMD_FLUIDS.get("chromium"), "Molten Chromium");
        fluidAndBucket(QMD_FLUIDS.get("cobalt"), "Molten Cobalt");
        fluidAndBucket(QMD_FLUIDS.get("compressed_air"), "Compressed Air");
        fluidAndBucket(QMD_FLUIDS.get("erbium"), "Molten Erbium");
        fluidAndBucket(QMD_FLUIDS.get("exhaust_mercury"), "Exhaust Mercury");
        fluidAndBucket(QMD_FLUIDS.get("glueballs"), "Glueballs");
        fluidAndBucket(QMD_FLUIDS.get("hafnium"), "Molten Hafnium");
        fluidAndBucket(QMD_FLUIDS.get("high_pressure_mercury"), "High Pressure Mercury");
        fluidAndBucket(QMD_FLUIDS.get("hot_mercury"), "Hot Mercury");
        fluidAndBucket(QMD_FLUIDS.get("hydrochloric_acid"), "Hydrochloric Acid");
        fluidAndBucket(QMD_FLUIDS.get("iodine"), "Liquid Iodine");
        fluidAndBucket(QMD_FLUIDS.get("iridium"), "Molten Iridium");
        fluidAndBucket(QMD_FLUIDS.get("lead_nitrate_solution"), "Lead Nitrate Solution");
        fluidAndBucket(QMD_FLUIDS.get("lead_tungstate_solution"), "Lead Tungstate Solution");
        fluidAndBucket(QMD_FLUIDS.get("liquid_air"), "Liquid Air");
        fluidAndBucket(QMD_FLUIDS.get("liquid_argon"), "Liquid Argon");
        fluidAndBucket(QMD_FLUIDS.get("liquid_hydrogen"), "Liquid Hydrogen");
        fluidAndBucket(QMD_FLUIDS.get("liquid_neon"), "Liquid Neon");
        fluidAndBucket(QMD_FLUIDS.get("liquid_oxygen"), "Liquid Oxygen");
        fluidAndBucket(QMD_FLUIDS.get("mercury"), "Mercury");
        fluidAndBucket(QMD_FLUIDS.get("muonium"), "Muonium");
        fluidAndBucket(QMD_FLUIDS.get("nd_yag"), "Molten Neodymium Doped YAG");
        fluidAndBucket(QMD_FLUIDS.get("neodymium"), "Molten Neodymium");
        fluidAndBucket(QMD_FLUIDS.get("neon"), "Neon");
        fluidAndBucket(QMD_FLUIDS.get("nickel"), "Molten Nickel");
        fluidAndBucket(QMD_FLUIDS.get("niobium"), "Molten Niobium");
        fluidAndBucket(QMD_FLUIDS.get("nitric_acid"), "Nitric Acid");
        fluidAndBucket(QMD_FLUIDS.get("nitric_oxide"), "Nitric Oxide");
        fluidAndBucket(QMD_FLUIDS.get("nitrogen_dioxide"), "Nitrogen Dioxide");
        fluidAndBucket(QMD_FLUIDS.get("osmium"), "Molten Osmium");
        fluidAndBucket(QMD_FLUIDS.get("platinum"), "Molten Platinum");
        fluidAndBucket(QMD_FLUIDS.get("polonium"), "Molten Polonium");
        fluidAndBucket(QMD_FLUIDS.get("positronium"), "Positronium");
        fluidAndBucket(QMD_FLUIDS.get("radium"), "Molten Radium");
        fluidAndBucket(QMD_FLUIDS.get("salt_water"), "Salt Water");
        fluidAndBucket(QMD_FLUIDS.get("samarium"), "Molten Samarium");
        fluidAndBucket(QMD_FLUIDS.get("silicon"), "Molten Silicon");
        fluidAndBucket(QMD_FLUIDS.get("sodium_chloride"), "Molten Sodium Chloride");
        fluidAndBucket(QMD_FLUIDS.get("sodium_chloride_solution"), "Sodium Chloride Solution");
        fluidAndBucket(QMD_FLUIDS.get("sodium_nitrate_solution"), "Sodium Nitrate Solution");
        fluidAndBucket(QMD_FLUIDS.get("sodium_tungstate_solution"), "Sodium Tungstate Solution");
        fluidAndBucket(QMD_FLUIDS.get("strontium"), "Molten Strontium");
        fluidAndBucket(QMD_FLUIDS.get("tauonium"), "Tauonium");
        fluidAndBucket(QMD_FLUIDS.get("terbium"), "Molten Terbium");
        fluidAndBucket(QMD_FLUIDS.get("titanium"), "Molten Titanium");
        fluidAndBucket(QMD_FLUIDS.get("tungsten"), "Molten Tungsten");
        fluidAndBucket(QMD_FLUIDS.get("yag"), "Molten Yttrium Aluminum Garnet");
        fluidAndBucket(QMD_FLUIDS.get("ytterbium"), "Molten Ytterbium");
        fluidAndBucket(QMD_FLUIDS.get("yttrium"), "Molten Yttrium");
        fluidAndBucket(QMD_FLUIDS.get("zinc"), "Molten Zinc");
    }

    private void fluidAndBucket(NCFluid fluid, String name) {
        add(fluid.bucket.asItem(), name + " Bucket");
        add(fluid.type.get().getDescriptionId(), name);
        add(fluid.block.get(), name);
    }

    private void menus() {
        add(QMD.MOD_ID + ".menu.title.irradiator", "Irradiator");
        add(QMD.MOD_ID + ".menu.title.ore_leacher", "Ore Leacher");
        add(QMD.MOD_ID + ".menu.title.creative_particle_source", "Creative Particle Source");

        add(QMD.MOD_ID + ".menu.title.accelerator_source", "Accelerator Ion Source");
        add(QMD.MOD_ID + ".menu.title.accelerator_laser_ion_source", "Accelerator Laser Ion Source");

        add("gui.qmd.container.creative_particle_source.set", "Set");
        add("gui.qmd.container.creative_particle_source.particle_name", "Particle Name");
        add("gui.qmd.container.creative_particle_source.particle_amount", "Amount (pu/t)");
        add("gui.qmd.container.creative_particle_source.particle_energy", "Energy (keV)");
        add("gui.qmd.container.creative_particle_source.particle_focus", "Focus");

        add("gui.qmd.container.required_energy", "Required Energy: %s");
        add("gui.qmd.container.speed", "Speed Multiplier: x%s");

        add("gui.qmd.container.heat_stored", "Heat Stored: %s/%s");
        add("gui.qmd.container.energy_stored", "Energy Stored: %s/%s");

        add("gui.qmd.container.cryo.heat_stored", "Magnet Heat Stored: %s/%s");
        add("gui.qmd.container.cryo.coolant_stored", "Magnet Coolant: %s/%s");

        add("gui.qmd.container.temperature", "Temperature: %s");
        add("gui.qmd.container.max_temperature", "Maximum Temperature: %s");

        add("gui.qmd.container.max_heating", "Maximum Heating: %s");
        add("gui.qmd.container.heating", "Current Heating: %s");
        add("gui.qmd.container.external_heating", "Maximum External Heating: %s");

        add("gui.qmd.container.cooling", "Cooling: %s");
        add("gui.qmd.container.coolant_stored", "Coolant: %s/%s");
        add("gui.qmd.container.max_coolant_in", "Maximum Coolant Usage: %s");
        add("gui.qmd.container.max_coolant_out", "Maximum Hot Coolant Out: %s");

        add(QMD.MOD_ID + ".menu.title.linear_accelerator_controller", "Linear Accelerator");
        add(QMD.MOD_ID + ".menu.title.ring_accelerator_controller", "Synchrotron Accelerator");
        add(QMD.MOD_ID + ".menu.title.beam_diverter_controller", "Beam Diverter");
        add(QMD.MOD_ID + ".menu.title.beam_splitter_controller", "Beam Splitter");
        add(QMD.MOD_ID + ".menu.title.decelerator_controller", "Decelerator");
        add(QMD.MOD_ID + ".menu.title.mass_spectrometer_controller", "Mass Spectrometer");

        add("gui.qmd.container.accelerator.cavities", "FE Cavities: %s at %s");
        add("gui.qmd.container.accelerator.quadrupoles", "Quadrupoles: %s at %s");
        add("gui.qmd.container.accelerator.dipoles", "Dipoles: %s at %s");
        add("gui.qmd.container.accelerator.radius", "Radius: %s");
        add("gui.qmd.container.accelerator.length", "Length: %s");
        add("gui.qmd.container.accelerator.efficiency", " (%s%%)");
        add("gui.qmd.container.accelerator.error.1", "Too hot");
        add("gui.qmd.container.accelerator.error.2", "Does not have enough power");
        add("gui.qmd.container.accelerator.error.3", "Focus to low");
        add("gui.qmd.container.accelerator.error.4", "Particle energy to low");
        add("gui.qmd.container.accelerator.error.5", "Particle energy to high");
        add("gui.qmd.container.beam_diverter.max_energy", "Maximum Energy: %s");
        add("gui.qmd.container.beam_diverter.energy_loss", "Energy Loss: %s");
        add("gui.qmd.container.accelerator_source", "Accelerator Ion Source");
    }

    private void recipeViewer() {
        add("gui.qmd.recipe_viewer.collector.blocks", "Valid Blocks: %s");
        add("gui.qmd.recipe_viewer.collector.biomes", "Valid Biomes: %s");
        add("gui.qmd.recipe_viewer.collector.dimensions", "Valid Dimensions: %s");
        add("gui.qmd.recipe_viewer.collector.any", "Any");
    }

    private void descriptions() {
        add("block.qmd.atmosphere_collector.desc", "Collects gas from the atmosphere. Checks a 5x5x5 Cube where it is the bottom of the center face for clear space.");
        add("block.qmd.liquid_collector.desc", "Collects liquids from large bodies of liquid such as oceans and rivers. Checks a 5x5x5 Cube where it is the top of the center face for blocks.");

        add("item.qmd.beam_meter.desc", "Measures the stats of a Beamline or Beam Port. If measuring a Beamline the stats are the end of the beamline.");
        add("item.qmd.potassium_iodine_tablet.desc", "Gives temporary radiation immunity. Don't take too many!");
    }

    private void messages() {
        add("message.qmd.collector", "Collecting %2$s of %1$s");
        add("message.qmd.collector_no_fluid", "Collecting no fluid");
    }

    private void blocks() {
        add(dischargeLamps.get(LampType.EMPTY).get(), "Discharge Lamp");
        add(dischargeLamps.get(LampType.HYDROGEN).get(), "Hydrogen Discharge Lamp");
        add(dischargeLamps.get(LampType.HELIUM).get(), "Helium Discharge Lamp");
        add(dischargeLamps.get(LampType.NITROGEN).get(), "Nitrogen Discharge Lamp");
        add(dischargeLamps.get(LampType.OXYGEN).get(), "Oxygen Discharge Lamp");
        add(dischargeLamps.get(LampType.NEON).get(), "Neon Discharge Lamp");
        add(dischargeLamps.get(LampType.ARGON).get(), "Argon Discharge Lamp");
        add(dischargeLamps.get(LampType.SODIUM).get(), "Sodium Discharge Lamp");
        add(dischargeLamps.get(LampType.MERCURY).get(), "Mercury Discharge Lamp");
        add(strontium90.get(), "Strontium-90 Block");
        add(rtgStrontium.get(), "Strontium RTG");


        add(irradiator.get(), "Irradiator");
        add(oreLeacher.get(), "Ore Leacher");
        add(atmosphereCollector.get(), "Atmosphere Collector");
        add(liquidCollector.get(), "Liquid Collector");
        add(creativeParticleSource.get(), "Creative Particle Source");

        add(fissionReflector.get(), "Tungsten Carbide Neutron Reflector");
        add(fissionShield.get(), "Hafnium Fission Neutron Shield");
        add(turbineBladeSuperAlloy.get(), "Super Alloy Turbine Rotor Blade");

        add(beamline.get(), "Beamline Pipe");

        add(linearAcceleratorController.get(), "Linear Accelerator Controller");
        add(ringAcceleratorController.get(), "Synchrotron Accelerator Controller");
        add(acceleratorBeam.get(), "Accelerator Beam Block");
        add(acceleratorCasing.get(), "Accelerator Casing");
        add(acceleratorGlass.get(), "Accelerator Glass");
        add(acceleratorVent.get(), "Accelerator Coolant Vent");
        add(acceleratorBeamPort.get(), "Accelerator Beam Port");
        add(acceleratorSynchrotronPort.get(), "Accelerator Synchrotron Port");
        add(acceleratorYoke.get(), "Accelerator Electromagnet Yoke");
        add(acceleratorSource.get(), "Accelerator Ion Source");
        add(acceleratorEnergyPort.get(), "Accelerator Energy Port");
        add(beamDiverterController.get(), "Beam Diverter Controller");
        add(beamSplitterController.get(), "Beam Splitter Controller");
        add(deceleratorController.get(), "Decelerator Controller");
        add(acceleratorComputerPort.get(), "Accelerator Computer Port");
        add(acceleratorPort.get(), "Accelerator Ion Source Port");
        add(acceleratorRedstonePort.get(), "Accelerator Redstone Port");
        add(massSpectrometerController.get(), "Mass Spectrometer Controller");
        add(acceleratorLaserIonSource.get(), "Accelerator Laser Ion Source");
        add(acceleratorIonCollector.get(), "Accelerator Ion Collector");

        add(RFCavities.get(RFCavityType.COPPER).get(), "Copper FE Cavity");
        add(RFCavities.get(RFCavityType.MAGNESIUM_DIBORIDE).get(), "Magnesium Diboride  FE Cavity");
        add(RFCavities.get(RFCavityType.NIOBIUM_TIN).get(), "Niobium-Tin FE Cavity");
        add(RFCavities.get(RFCavityType.NIOBIUM_TITANIUM).get(), "Niobium-Titanium FE Cavity");
        add(RFCavities.get(RFCavityType.BSCCO).get(), "BSCCO FE Cavity");
        add(RFCavities.get(RFCavityType.Aluminium).get(), "Aluminum FE Cavity");
        add(RFCavities.get(RFCavityType.SSFAF).get(), "SSFAF FE Cavity");
        add(RFCavities.get(RFCavityType.YBCO).get(), "YBCO FE Cavity");

        add(acceleratorMagnets.get(MagnetType.COPPER).get(), "Copper Accelerator Electromagnet");
        add(acceleratorMagnets.get(MagnetType.MAGNESIUM_DIBORIDE).get(), "Magnesium Diboride Accelerator Electromagnet");
        add(acceleratorMagnets.get(MagnetType.NIOBIUM_TIN).get(), "Niobium-Tin Accelerator Electromagnet");
        add(acceleratorMagnets.get(MagnetType.NIOBIUM_TITANIUM).get(), "Niobium-Titanium Accelerator Electromagnet");
        add(acceleratorMagnets.get(MagnetType.BSCCO).get(), "BSCCO Accelerator Electromagnet");
        add(acceleratorMagnets.get(MagnetType.Aluminium).get(), "Aluminum Accelerator Electromagnet");
        add(acceleratorMagnets.get(MagnetType.SSFAF).get(), "SSFAF Accelerator Electromagnet");
        add(acceleratorMagnets.get(MagnetType.YBCO).get(), "YBCO Accelerator Electromagnet");

        for (CoolerType type : CoolerType.values()) {
            add(acceleratorCoolers.get(type).get(), String.join(" ", Arrays.stream(type.getName().split("_")).map(StringUtils::capitalize).toList()) + " Accelerator Cooler");
        }
    }

    private void items() {
        add(dusts.get(DustType.TUNGSTEN).get(), "Tungsten Dust");
        add(dusts.get(DustType.NIOBIUM).get(), "Niobium Dust");
        add(dusts.get(DustType.CHROMIUM).get(), "Chromium Dust");
        add(dusts.get(DustType.TITANIUM).get(), "Titanium Dust");
        add(dusts.get(DustType.COBALT).get(), "Cobalt Dust");
        add(dusts.get(DustType.NICKEL).get(), "Nickel Dust");
        add(dusts.get(DustType.HAFNIUM).get(), "Hafnium Dust");
        add(dusts.get(DustType.ZINC).get(), "Zinc Dust");
        add(dusts.get(DustType.OSMIUM).get(), "Osmium Dust");
        add(dusts.get(DustType.IRIDIUM).get(), "Iridium Dust");
        add(dusts.get(DustType.PLATINUM).get(), "Platinum Dust");
        add(dusts.get(DustType.SODIUM).get(), "Sodium Dust");
        add(dusts.get(DustType.POTASSIUM).get(), "Potassium Dust");
        add(dusts.get(DustType.CALCIUM).get(), "Calcium Dust");
        add(dusts.get(DustType.STRONTIUM).get(), "Strontium Dust");
        add(dusts.get(DustType.BARIUM).get(), "Barium Dust");
        add(dusts.get(DustType.YTTRIUM).get(), "Yttrium Dust");
        add(dusts.get(DustType.NEODYMIUM).get(), "Neodymium Dust");
        add(dusts.get(DustType.IODINE).get(), "Iodine Dust");
        add(dusts.get(DustType.SAMARIUM).get(), "Samarium Dust");
        add(dusts.get(DustType.TERBIUM).get(), "Terbium Dust");
        add(dusts.get(DustType.ERBIUM).get(), "Erbium Dust");
        add(dusts.get(DustType.YTTERBIUM).get(), "Ytterbium Dust");

        add(ingots.get(IngotType.TUNGSTEN).get(), "Tungsten Ingot");
        add(ingots.get(IngotType.NIOBIUM).get(), "Niobium Ingot");
        add(ingots.get(IngotType.CHROMIUM).get(), "Chromium Ingot");
        add(ingots.get(IngotType.TITANIUM).get(), "Titanium Ingot");
        add(ingots.get(IngotType.COBALT).get(), "Cobalt Ingot");
        add(ingots.get(IngotType.NICKEL).get(), "Nickel Ingot");
        add(ingots.get(IngotType.HAFNIUM).get(), "Hafnium Ingot");
        add(ingots.get(IngotType.ZINC).get(), "Zinc Ingot");
        add(ingots.get(IngotType.OSMIUM).get(), "Osmium Ingot");
        add(ingots.get(IngotType.IRIDIUM).get(), "Iridium Ingot");
        add(ingots.get(IngotType.PLATNIUM).get(), "Platinum Ingot");
        add(ingots.get(IngotType.SODIUM).get(), "Sodium Ingot");
        add(ingots.get(IngotType.POTASSIUM).get(), "Potassium Ingot");
        add(ingots.get(IngotType.CALCIUM).get(), "Calcium Ingot");
        add(ingots.get(IngotType.STRONTIUM).get(), "Strontium Ingot");
        add(ingots.get(IngotType.BARIUM).get(), "Barium Ingot");
        add(ingots.get(IngotType.YTTRIUM).get(), "Yttrium Ingot");
        add(ingots.get(IngotType.NEODYMIUM).get(), "Neodymium Ingot");
        add(ingots.get(IngotType.MERCURY).get(), "Mercury");

        add(fissionWastes.get(FissionWasteType.LIGHT).get(), "Light Fission Waste");
        add(fissionWastes.get(FissionWasteType.HEAVY).get(), "Heavy Fission Waste");

        add(spallationWastes.get(SpallationWasteType.CALIFORNIUM).get(), "Californium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.BERKELIUM).get(), "Berkelium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.CURIUM).get(), "Curium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.AMERICIUM).get(), "Americium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.PLUTONIUM).get(), "Plutonium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.NEPTUNIUM).get(), "Neptunium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.URANIUM).get(), "Uranium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.PROTACTINIUM).get(), "Protactinium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.THORIUM).get(), "Thorium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.RADIUM).get(), "Radium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.POLONIUM).get(), "Polonium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.BISMUTH).get(), "Bismuth Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.LEAD).get(), "Lead Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.GOLD).get(), "Gold Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.PLATINUM).get(), "Platinum Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.IRIDIUM).get(), "Iridium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.OSMIUM).get(), "Osmium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.TUNGSTEN).get(), "Tungsten Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.HAFNIUM).get(), "Hafnium Spallation Waste");
        add(spallationWastes.get(SpallationWasteType.MERCURY).get(), "Mercury Spallation Waste");

        add(ingotAlloys.get(IngotAlloyType.TUNGSTEN_CARBIDE).get(), "Tungsten Carbide Ingot");
        add(ingotAlloys.get(IngotAlloyType.NIOBIUM_TIN).get(), "Niobium-Tin Ingot");
        add(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL).get(), "Stainless Steel Ingot");
        add(ingotAlloys.get(IngotAlloyType.NIOBIUM_TITANIUM).get(), "Niobium-Titanium Ingot");
        add(ingotAlloys.get(IngotAlloyType.OSMIRIDIUM).get(), "Osmiridium Ingot");
        add(ingotAlloys.get(IngotAlloyType.NICHROME).get(), "Nichrome Ingot");
        add(ingotAlloys.get(IngotAlloyType.SUPER_ALLOY).get(), "Super Alloy Ingot");

        add(sources.get(SourceType.TUNGSTEN_FILAMENT).get(), "Tungsten Filament");
        add(sources.get(SourceType.SODIUM_22).get(), "Sodium-22 Source");
        add(sources.get(SourceType.COBALT_60).get(), "Cobalt-60 Source");
        add(sources.get(SourceType.IRIDIUM_192).get(), "Iridium-192 Source");
        add(sources.get(SourceType.CALCIUM_48).get(), "Calcium-48 Source");

        add(isotopes.get(IsotopeType.SODIUM_22).get(), "Sodium-22");
        add(isotopes.get(IsotopeType.MAGNESIUM_26).get(), "Magnesium-26");
        add(isotopes.get(IsotopeType.MAGNESIUM_24).get(), "Magnesium-24");
        add(isotopes.get(IsotopeType.BERYLLIUM_7).get(), "Beryllium-7");
        add(isotopes.get(IsotopeType.Uranium_234).get(), "Uranium-234");
        add(isotopes.get(IsotopeType.PROTACTINIUM_231).get(), "Protactinium-231 Dust");
        add(isotopes.get(IsotopeType.COBALT_60).get(), "Cobalt-60");
        add(isotopes.get(IsotopeType.IRIDIUM_192).get(), "Iridium-192");
        add(isotopes.get(IsotopeType.CALCIUM_48).get(), "Calcium-48 ");

        add(parts.get(PartType.EMPTY_COOLER).get(), "Empty Cooler");
        add(parts.get(PartType.SCINTILLATOR_PWO).get(), "Lead Tungstate Scintillator");
        add(parts.get(PartType.SCINTILLATOR_PLASTIC).get(), "Plastic Scintillator");
        add(parts.get(PartType.DETECTOR_CASING).get(), "Empty Detector Casing");
        add(parts.get(PartType.WIRE_BSCCO).get(), "BSCCO Wire");
        add(parts.get(PartType.ROD_ND_YAG).get(), "Neodymium Doped YAG Rod");
        add(parts.get(PartType.WIRE_GOLD_TUNGSTEN).get(), "Gold Plated Tungsten Wire");
        add(parts.get(PartType.WIRE_CHAMBER_CASING).get(), "Empty Wire Chamber");
        add(parts.get(PartType.MAGNET_ND).get(), "Neodymium Magnet");
        add(parts.get(PartType.ACCELERATING_BARREL).get(), "Accelerating Barrel");
        add(parts.get(PartType.LASER_ASSEMBLY).get(), "Laser Assembly");
        add(parts.get(PartType.WIRE_SSFAF).get(), "SSFAF Wire");
        add(parts.get(PartType.WIRE_YBCO).get(), "YBCO Wire");
        add(parts.get(PartType.MAGNET_SMC).get(), "Samarium Cobalt Magnet");

        add(semiconductors.get(SemiconductorType.SILICON_P_DOPED).get(), "P-Type Doped Silicon");
        add(semiconductors.get(SemiconductorType.SILICON_N_DOPED).get(), "N-Type Doped Silicon");
        add(semiconductors.get(SemiconductorType.SILICON_BOULE).get(), "Silicon Boule");
        add(semiconductors.get(SemiconductorType.SILICON_WAFER).get(), "Silicon Wafer");
        add(semiconductors.get(SemiconductorType.BASIC_PROCESSOR).get(), "Basic Processor");
        add(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR).get(), "Advanced Processor");
        add(semiconductors.get(SemiconductorType.ELITE_PROCESSOR).get(), "Elite Processor");

        add(chemicalDusts.get(ChemicalDustType.TUNGSTEN_OXIDE).get(), "Tungsten Oxide");
        add(chemicalDusts.get(ChemicalDustType.BSCCO).get(), "BSCCO Dust");
        add(chemicalDusts.get(ChemicalDustType.SODIUM_NITRATE).get(), "Sodium Nitrate");
        add(chemicalDusts.get(ChemicalDustType.SODIUM_CHLORIDE).get(), "Sodium Chloride");
        add(chemicalDusts.get(ChemicalDustType.COPPER_OXIDE).get(), "Copper Oxide");
        add(chemicalDusts.get(ChemicalDustType.HAFNIUM_OXIDE).get(), "Hafnium Oxide");
        add(chemicalDusts.get(ChemicalDustType.STRONTIUM_CHLORIDE).get(), "Strontium Chloride");
        add(chemicalDusts.get(ChemicalDustType.ZINC_SULFIDE).get(), "Zinc Sulfide");
        add(chemicalDusts.get(ChemicalDustType.IRON_FLUORIDE).get(), "Iron Fluoride");
        add(chemicalDusts.get(ChemicalDustType.SSFAF).get(), "SSFAF Dust");
        add(chemicalDusts.get(ChemicalDustType.YBCO).get(), "YBCO Dust");

        add(sword_tungsten_carbide.get(), "Tungsten Carbide Sword");
        add(shovel_tungsten_carbide.get(), "Tungsten Carbide Shovel");
        add(axe_tungsten_carbide.get(), "Tungsten Carbide Axe");
        add(hoe_tungsten_carbide.get(), "Tungsten Carbide Hoe");
        add(pickaxe_tungsten_carbide.get(), "Tungsten Carbide Pickaxe");
        add(beamMeter.get(), "Beam Meter");

        // TODO drill / advanced drill

        add(flesh.get(), "Not So Rotten Flesh");
        add(potassiumIodineTablet.get(), "Potassium Iodine Tablet");
        add(luminousPaints.get(LuminousPaintType.GREEN).get(), "Green Radioluminescent Paint");
        add(luminousPaints.get(LuminousPaintType.BLUE).get(), "Blue Radioluminescent Paint");
        add(luminousPaints.get(LuminousPaintType.ORANGE).get(), "Orange Radioluminescent Paint");

        add(cells.get(CellType.EMPTY).get(), "Empty Cell");
        add(cells.get(CellType.ANTIHYDROGEN).get(), "Anti-Hydrogen Cell");
        add(cells.get(CellType.ANTIDEUTERIUM).get(), "Anti-Deuterium Cell");
        add(cells.get(CellType.ANTITRITIUM).get(), "Anti-Tritium Cell");
        add(cells.get(CellType.ANTIHELIUM3).get(), "Anti-Helium-3 Cell");
        add(cells.get(CellType.ANTIHELIUM).get(), "Anti-Helium Cell");
        add(cells.get(CellType.POSITRONIUM).get(), "Positronium Cell");
        add(cells.get(CellType.MUONIUM).get(), "Muonium Cell");
        add(cells.get(CellType.TAUONIUM).get(), "Tauonium Cell");
        add(cells.get(CellType.GLUEBALLS).get(), "Glueball Cell");


        // TODO add remaining

        add(copernicium.get(CoperniciumType._291).get(), "Copernicium-291");
        add(copernicium.get(CoperniciumType._291_C).get(), "Copernicium-291 Carbide");
        add(copernicium.get(CoperniciumType._291_OX).get(), "Copernicium-291 Oxide");
        add(copernicium.get(CoperniciumType._291_NI).get(), "Copernicium-291 Nitride");
        add(copernicium.get(CoperniciumType._291_ZA).get(), "Copernicium-291-Zirconium Alloy");

        add(pellet_copernicium.get(CoperniciumPelletType.MIX_291).get(), "MIX-291");
        add(pellet_copernicium.get(CoperniciumPelletType.MIX_291_C).get(), "MIX-291 Carbide");

        add(fuel_copernicium.get(CoperniciumFuelType.MIX_291_TR).get(), "MTRISO-291 Fuel Pebble");
        add(fuel_copernicium.get(CoperniciumFuelType.MIX_291_OX).get(), "MOX-291 Oxide Fuel Pellet");
        add(fuel_copernicium.get(CoperniciumFuelType.MIX_291_NI).get(), "MNI-291 Fuel Pellet");
        add(fuel_copernicium.get(CoperniciumFuelType.MIX_291_ZA).get(), "MZA-291 Fuel Pellet");
        add(depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_TR).get(), "Depleted MTRISO-291 Fuel Pebble");
        add(depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_OX).get(), "Depleted MOX-291 Fuel Pellet");
        add(depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_NI).get(), "Depleted MNI-291 Fuel Pellet");
        add(depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_ZA).get(), "Depleted MZA-291 Fuel Pellet");
    }

    private void multiblock_validation() {
        add("zerocore.api.nc.multiblock.validation.cannot_be_multiblock_part", "This block position cannot be part of the multiblock.");

        add("qmd.multiblock_validation.no_inlet", "There must be at least 1 Coolant Vent INPUT.");
        add("qmd.multiblock_validation.no_outlet", "There must be at least 1 Coolant Vent OUTPUT.");
        add("qmd.multiblock_validation.need_energy_ports", "There must be at least 1 Energy Port.");

        add("qmd.multiblock_validation.accelerator.wrong_height", "Accelerators must be 5 blocks tall.");
        add("qmd.multiblock_validation.accelerator.invalid_beam_port", "A Beam Port can not be here.");
        add("qmd.multiblock_validation.accelerator.something_is_wrong", "You got a Beam Port with no facing. Well how did you manage to do that?");
        add("qmd.multiblock_validation.accelerator.no_yokes", "There cannot be any Electromagnet Yokes in this type of accelerator.");
        add("qmd.multiblock_validation.accelerator.no_synch_ports", "There cannot be any Synchrotron Ports on this type of accelerator.");
        add("qmd.multiblock_validation.accelerator.no_rf_cavity", "There cannot be any FE Cavities in this type of accelerator.");
        add("qmd.multiblock_validation.accelerator.no_source", "There cannot be any Ion Sources on this type of accelerator.");
        add("qmd.multiblock_validation.accelerator.no_ion_collectors", "There cannot be any Ion Collectors on this type of accelerator.");
        add("qmd.multiblock_validation.accelerator.no_ion_ports", "There cannot be any Ion Source Ports on this type of accelerator.");

        add("qmd.multiblock_validation.accelerator.invalid_coolers", "There is %s invalid coolers.");

        add("qmd.multiblock_validation.accelerator.linear.to_short", "Linear Accelerator is not long enough.");
        add("qmd.multiblock_validation.accelerator.linear.must_be_5_wide", "Linear Accelerators must be 5 blocks wide.");
        add("qmd.multiblock_validation.accelerator.linear.must_be_beam", "There must be a straight line of Accelerator Beam blocks down the middle of the Linear Accelerator.");
        add("qmd.multiblock_validation.accelerator.linear.must_have_io", "There must be a Accelerator Beam Port INPUT and OUTPUT at either end of the beam.");
        add("qmd.multiblock_validation.accelerator.linear.have_source_and_beam_port", "There must be a Ion Source at one end of the beam and a Beam Port at the other.");
        add("qmd.multiblock_validation.accelerator.linear.source_must_face_in", "The Ion Source must face inwards at the beam.");
        add("qmd.multiblock_validation.accelerator.linear.only_one_source", "There can only be one Ion Source.");
        add("qmd.multiblock_validation.accelerator.linear.to_many_beam_ports", "There can only be a Beam Port at the ends of the beam.");

        add("qmd.multiblock_validation.accelerator.ring.must_be_square", "The Accelerator must have the same length in the X and Z direction.");
        add("qmd.multiblock_validation.accelerator.ring.to_short", " Synchrotron Accelerator is not large enough.");
        add("qmd.multiblock_validation.accelerator.ring.to_long", " Synchrotron Accelerator is too large.");
        add("qmd.multiblock_validation.accelerator.ring.must_be_beam", "There must be a square ring of Accelerator Beam blocks down the middle of the Accelerator.");
        add("qmd.multiblock_validation.accelerator.ring.must_be_dipole_in_conner", "There must be a dipole in every corner of the accelerator. A dipole is made by placing a Accelerator Electromagnet on the top and bottom of a Accelerator Beam, then surrounding the it with Accelerator Electromagnet Yokes.");
        add("qmd.multiblock_validation.accelerator.ring.must_be_inline_with_beam", "Beam Ports must be on the same Y level as the beam ring.");
        add("qmd.multiblock_validation.accelerator.ring.beam_port_must_connect", "There must be a Beam Block to Connect the Beam Port.");
        add("qmd.multiblock_validation.accelerator.ring.must_be_dipole", "There must be a dipole where a Beam Port enters.");
        add("qmd.multiblock_validation.accelerator.ring.must_have_io", "There must be only one INPUT and OUTPUT Beam Port.");
        add("qmd.multiblock_validation.accelerator.ring.to_many_synchrotron_ports", "There can only be one Synchrotron Port.");

        add("qmd.multiblock_validation.beam_director.must_be_cube", "The Beam Diverter must be a 5x5x5 Cube with a dipole in the center.");
        add("qmd.multiblock_validation.beam_director.must_be_beam", "There must have a Accelerator Beam Block at the center.");
        add("qmd.multiblock_validation.beam_director.must_be_dipole", "There Must be a vertical or horizontal dipole at the center.");

        add("qmd.multiblock_validation.accelerator.splitter.must_have_io", "There must be one INPUT and two OUTPUT Beam Ports.");

        add("qmd.multiblock_validation.accelerator.mass_spectrometer.wrong_height", "Mass Spectrometers must be 7 blocks tall.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.wrong_width", "Mass Spectrometers must be 7 blocks wide in at least one direction.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.must_be_odd_length", "Mass Spectrometers must be an odd number of blocks long.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.need_ion_source_amount", "There must be one Ion Source on every second vertical layer along the length of the Mass Spectrometer. For this size Mass Spectrometer there Must be %s Ion Sources.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos", "This is not a valid position for a Ion Source. Ion Sources must be placed one block up on the side of every second vertical layer along the length of the mass spectrometer.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_facing", "The Ion Source must face inwards.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_type", "This type of Accelerator Ion Source is not allowed for a Mass Spectrometer.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.ion_source_in_layer_already", "There is already a Ion Source on this vertical layer.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.must_be_ion_collector", "This must be an Ion Collector. There must be 4 Ion Collectors above every Ion Source.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.ion_collector_wrong_facing", "The Ion Collector must face inwards.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.need_ion_collector_amount", "There must be 4 Ion Collectors above every Ion Source, not anywhere else.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.must_be_yoke", "This must be an Accelerator Electromagnet Yoke.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.must_be_magnet", "This must be an Accelerator Electromagnet.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.magnet_wrong_type", "This type of Accelerator Electromagnet is not allowed for a Mass Spectrometer.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.must_be_one_magnet_type", "There can only be one type of Accelerator Electromagnet in a Mass Spectrometer.");
        add("qmd.multiblock_validation.accelerator.mass_spectrometer.must_be_beam", "This must be an Accelerator Beam Block.");

        add("qmd.multiblock_validation.chamber.no_beams", "There cannot be any Particle Chamber Beams in this type of particle chamber.");
        add("qmd.multiblock_validation.chamber.no_detectors", "There cannot be any Detectors in this type of particle chamber.");
        add("qmd.multiblock_validation.chamber.no_item_ports", "There cannot be any Item Ports on this type of particle chamber.");
        add("qmd.multiblock_validation.chamber.no_fluid_ports", "There cannot be any Fluid Ports on this type of particle chamber.");

        add("qmd.multiblock_validation.chamber.must_have_target", "Must have a Particle Chamber at the center.");
        add("qmd.multiblock_validation.chamber.must_be_beam", "There must be a line of Particle Chamber Beam Blocks from each Beam Port to the Particle Chamber.");
        add("qmd.multiblock_validation.chamber.beam_port_wrong_spot", "Beam Ports must be at the center of a horizontal face.");
        add("qmd.multiblock_validation.chamber.must_be_square", "Chamber must have the same X and Z dimensions.");
        add("qmd.multiblock_validation.chamber.must_be_odd", "Chamber must have an odd width.");
        add("qmd.multiblock_validation.chamber.must_have_input_beam", "There must be 1 INPUT Beam Port.");

        add("qmd.multiblock_validation.chamber.beam_dump.only_input_beam", "There can only be 1 INPUT Beam Port on a Beam Dump.");
        add("qmd.multiblock_validation.chamber.beam_dump.must_have_fluid_output", "There must be an OUTPUT Fluid Port on a Beam Dump.");
        add("qmd.multiblock_validation.chamber.invalid_detectors", "There is %s invalid detectors.");

        add("qmd.multiblock_validation.collision_chamber.wrong_length", "Collision Chamber must be 17 blocks long.");
        add("qmd.multiblock_validation.collision_chamber.must_be_odd_square", "The end faces must be squares of odd side length.");
        add("qmd.multiblock_validation.collision_chamber.must_be_target", "There must be a straight line of Particle Chamber blocks down the middle of the Collision chamber.");
        add("qmd.multiblock_validation.collision_chamber.must_be_beam", "This must be a Particle Chamber Beam Block.");
        add("qmd.multiblock_validation.collision_chamber.must_be_input_beam_port", "This must be an INPUT Particle Chamber Beam Port.");
        add("qmd.multiblock_validation.collision_chamber.must_be_output_beam_port", "This must be an OUTPUT Particle Chamber Beam Port.");
        add("qmd.multiblock_validation.collision_chamber.beam_port_wrong_spot", "There are too many Beam Ports. There must be 2 INPUT Particle Chamber Beam Ports and 4 OUTPUT Particle Chamber Beam Ports.");

        add("qmd.multiblock_validation.vacuum_chamber.no_fluid_ports", "There cannot be any Fluid Ports on this type of vacuum chamber.");
        add("qmd.multiblock_validation.vacuum_chamber.no_item_ports", "There cannot be any Item Ports on this type of vacuum chamber.");
        add("qmd.multiblock_validation.vacuum_chamber.no_lasers", "There cannot be any Vacuum Chamber Lasers in this type of vacuum chamber.");
        add("qmd.multiblock_validation.vacuum_chamber.no_heater_vents", "There cannot be any Heater Vents in this type of vacuum chamber.");
        add("qmd.multiblock_validation.vacuum_chamber.invalid_heaters", "There is %s invalid heaters.");

        add("qmd.multiblock_validation.exotic_containment.must_be_square", "Exotic Containment must have the same X and Z dimensions.");
        add("qmd.multiblock_validation.exotic_containment.must_be_odd", "Exotic Containment must have an odd width.");
        add("qmd.multiblock_validation.exotic_containment.must_be_coil", "There must be a ring of Vacuum Chamber Coils at the top and bottom.");
        add("qmd.multiblock_validation.exotic_containment.beam_port_or_laser", "There must be a Beam Port or Laser at the center of the face.");
        add("qmd.multiblock_validation.exotic_containment.must_be_laser", "There must be a Laser at the center of the face.");
        add("qmd.multiblock_validation.exotic_containment.must_be_input_beam", "There must be a INPUT Beam Port at the center of the face.");
        add("qmd.multiblock_validation.exotic_containment.must_be_empty", "This block must be an empty space.");
        add("qmd.multiblock_validation.exotic_containment.must_have_2_lasers", "There can only be 2 Vacuum Chamber Lasers.");
        add("qmd.multiblock_validation.exotic_containment.must_have_2_beam_ports", "There can only be 2 INPUT Beam Ports.");

        add("qmd.multiblock_validation.nucleosynthesis_chamber.size", "Nucleosynthesis Chamber must be size 11x5 with height of 7.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_beam", "This must be a Vacuum Chamber Beam Block.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_plasma_glass", "This must be a Vacuum Chamber Plasma Glass block.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_plasma_nozzle", "This must be a Vacuum Chamber Plasma Nozzle block that is facing in.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_heater_or_empty", "This can only be a Vacuum Chamber Heater block or an empty space.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_empty", "This must be an empty space.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_input_beam", "There must be only 1 INPUT Beam Port either here or in the same position on the opposite face.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_output_fluid_port", "This must be an OUTPUT Fluid Port");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_be_input_fluid_port", "This must be an INPUT Fluid Port");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.must_have_4_fluid_ports", "There must be 2 INPUT Fluid Ports and 2 OUTPUT Fluid Ports.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.no_heater_inlet", "There must be at least 1 INPUT Heater Vent.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.no_heater_outlet", "There must be at least 1 OUTPUT Heater Vent.");
        add("qmd.multiblock_validation.nucleosynthesis_chamber.wrong_amount_of_beam_ports", "There must be only 1 INPUT Beam Port and no OUTPUT Beam Ports.");

        add("qmd.multiblock_validation.liquefier.invalid_nozzle_network", "Each Liquefier Expansion Nozzle must be connected to a compressor by heat exchanger tubes.");
        add("qmd.multiblock_validation.liquefier.must_be_baffle_or_nozzle", "The layer above the bottom interior layer must be made of Heat Exchanger Baffles or Liquefier Expansion Nozzles.");
        add("qmd.multiblock_validation.liquefier.must_be_empty_layer", "The bottom interior layer must be empty.");
        add("qmd.multiblock_validation.liquefier.compressor_must_be_in_roof", "Compressors must be located on the top face of the multiblock.");
        add("qmd.multiblock_validation.liquefier.invalid_compressor_amount", "There must be at least the same amount of Liquefier Compressors as Liquefier Nozzles.");
        add("qmd.multiblock_validation.liquefier.must_have_fluid_input", "There must be at least one INPUT Liquefier Port.");
        add("qmd.multiblock_validation.liquefier.must_have_fluid_output", "There must be at least one OUTPUT Liquefier Port.");
        add("qmd.multiblock_validation.liquefier.must_have_energy_port", "There must be at least one Liquefier Energy Port.");
        add("qmd.multiblock_validation.liquefier.inlet_outlet_must_be_in_top_section", "Heat Exchanger Inlets and Heat Exchanger Outlets must be above the two bottom interior layers.");
    }
}