package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.info.NCFluid;
import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.BlockTypes;
import lach_01298.qmd.enums.MaterialTypes.*;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static lach_01298.qmd.block.QMDBlocks.*;
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
        add(NuclearcraftNeohaul.MODID + ".menu.title.irradiator", "Irradiator");
        add(NuclearcraftNeohaul.MODID + ".menu.title.ore_leacher", "Ore Leacher");
        add(NuclearcraftNeohaul.MODID + ".menu.title.creative_particle_source", "Creative Particle Source");

        add("gui.qmd.container.creative_particle_source.set", "Set");
        add("gui.qmd.container.creative_particle_source.particle_name", "Particle Name");
        add("gui.qmd.container.creative_particle_source.particle_amount", "Amount (pu/t)");
        add("gui.qmd.container.creative_particle_source.particle_energy", "Energy (keV)");
        add("gui.qmd.container.creative_particle_source.particle_focus", "Focus");
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
        add(dischargeLamps.get(BlockTypes.LampType.EMPTY).get(), "Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.HYDROGEN).get(), "Hydrogen Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.HELIUM).get(), "Helium Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.NITROGEN).get(), "Nitrogen Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.OXYGEN).get(), "Oxygen Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.NEON).get(), "Neon Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.ARGON).get(), "Argon Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.SODIUM).get(), "Sodium Discharge Lamp");
        add(dischargeLamps.get(BlockTypes.LampType.MERCURY).get(), "Mercury Discharge Lamp");
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
}