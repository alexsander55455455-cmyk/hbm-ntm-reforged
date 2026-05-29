package com.hbmspace.handler;

import com.hbm.tileentity.TileMappings;
import net.minecraft.tileentity.TileEntity;

public final class X5687LegacyTileMappings {

    private static boolean registered;

    private X5687LegacyTileMappings() {
    }

    public static void register() {
        if (registered) return;
        registered = true;

        put(com.hbmspace.tileentity.machine.TileEntityFurnaceSpace.class, "tileentity_furnace_space");
        put(com.hbmspace.tileentity.bomb.TileEntityLaunchPadRocket.class, "tileentity_launchpad_rocket", "tileentity_launch_pad_rocket");
        put(com.hbmspace.tileentity.machine.TileEntityMachineMagma.class, "tileentity_magma", "tileentity_machine_magma");
        put(com.hbmspace.tileentity.machine.TileEntityMachineGasDock.class, "tileentity_gas_dock", "tileentity_machine_gas_dock");
        put(com.hbmspace.tileentity.machine.TileEntityMachineRocketAssembly.class, "tileentity_rocket_assembly", "tileentity_machine_rocket_assembly");
        put(com.hbmspace.tileentity.machine.storage.TileEntityCombatDropPod.class, "tileentity_combat_pod", "tileentity_combat_drop_pod");
        put(com.hbmspace.tileentity.machine.TileEntityMachineWarController.class, "tileentity_war", "tileentity_machine_war_controller");
        put(com.hbmspace.tileentity.machine.TileEntityMachineSolarPanel.class, "tileentity_solarpanel", "tileentity_machine_solar_panel");
        put(com.hbmspace.tileentity.machine.TileEntityMachineStardar.class, "tileentity_stardar", "tileentity_machine_stardar");
        put(com.hbmspace.tileentity.machine.TileEntityMachineDriveProcessor.class, "tileentity_drive_processor", "tileentity_machine_drive_processor");
        put(com.hbmspace.tileentity.machine.TileEntityAirPump.class, "tileentity_air_vent", "tileentity_air_pump");
        put(com.hbmspace.tileentity.machine.TileEntityAirScrubber.class, "tileentity_air_scrubber");
        put(com.hbmspace.tileentity.machine.TileEntityAlgaeFilm.class, "tileentity_algae_film");
        put(com.hbmspace.tileentity.machine.TileEntityHydroponic.class, "tileentity_hydrobay", "tileentity_hydroponic");
        put(com.hbmspace.blocks.machine.BlockAtmosphereEditor.TileEntityAtmosphereEditor.class, "tileentity_atmosphere_editor");
        put(com.hbmspace.tileentity.machine.TileEntityRadiator.class, "tileentity_radiator");
        put(com.hbmspace.tileentity.machine.TileEntityAtmoTower.class, "tileentity_atmospheric_tower", "tileentity_atmo_tower");
        put(com.hbmspace.tileentity.machine.TileEntityAtmosphericCompressor.class, "tileentity_atmospheric_vent", "tileentity_atmospheric_compressor");
        put(com.hbmspace.tileentity.machine.TileEntityMachineVacuumCircuit.class, "tileentity_vacuum_circuit", "tileentity_machine_vacuum_circuit");
        put(com.hbmspace.tileentity.machine.TileEntityMachineCryoDistill.class, "tileentity_cryogenic_distillator", "tileentity_machine_cryo_distill");
        put(com.hbmspace.tileentity.machine.oil.TileEntityMachineAlkylation.class, "tileentity_alkylation", "tileentity_machine_alkylation");
        put(com.hbmspace.tileentity.machine.TileEntityMachineMilkReformer.class, "tileentity_milk_reformer", "tileentity_machine_milk_reformer");
        put(com.hbmspace.tileentity.machine.TileEntityTransporterRocket.class, "tileentity_transporter", "tileentity_transporter_rocket");
        put(com.hbmspace.tileentity.machine.TileEntityOrbitalStation.class, "tileentity_orbital_station");
        put(com.hbmspace.tileentity.machine.TileEntityOrbitalStationLauncher.class, "tileentity_orbital_station_launcher");
        put(com.hbmspace.tileentity.machine.TileEntityOrbitalStationComputer.class, "tileentity_orbital_station_computer");
        put(com.hbmspace.tileentity.machine.TileEntityStationPropulsionCreative.class, "tileentity_propulsion_creative", "tileentity_station_propulsion_creative");
        put(com.hbmspace.tileentity.machine.TileEntityDysonLauncher.class, "tileentity_dyson_launcher");
        put(com.hbmspace.tileentity.machine.TileEntityDysonReceiver.class, "tileentity_dyson_receiver");
        put(com.hbmspace.tileentity.machine.TileEntityDysonConverterTU.class, "tileentity_dyson_converter_tu");
        put(com.hbmspace.tileentity.machine.TileEntityDysonConverterHE.class, "tileentity_dyson_converter_he");
        put(com.hbmspace.tileentity.machine.TileEntityDysonConverterAnatmogenesis.class, "tileentity_dyson_converter_anatmogenesis");
        put(com.hbmspace.blocks.generic.BlockOrrery.TileEntityOrrery.class, "tileentity_orrery");
        put(com.hbmspace.tileentity.machine.TileEntityDishControl.class, "tileentity_dish_control");
        put(com.hbmspace.tileentity.machine.TileEntityXenonThruster.class, "tileentity_xenon_thruster");
        put(com.hbmspace.tileentity.machine.TileEntityMachineLPW2.class, "tileentity_machine_lpw2");
        put(com.hbmspace.tileentity.machine.TileEntityMachineHTR3.class, "tileentity_machine_htr3");
        put(com.hbmspace.tileentity.machine.TileEntityMachineHTRF4.class, "tileentity_machine_htrf4");
        put(com.hbmspace.tileentity.machine.TileEntityMachineHTRNeo.class, "tileentity_machine_htrneo");
        put(com.hbmspace.blocks.generic.BlockVolcanoV2.TileEntityLightningVolcano.class, "tileentity_electric_volcano", "tileentity_lightning_volcano");
        put(com.hbmspace.blocks.generic.BlockGeysierDCM.TileEntityDCM.class, "tileentity_dcm");
        put(com.hbmspace.tileentity.machine.TileEntityMachineDischarger.class, "tileentity_machine_discharger");
    }

    private static void put(Class<? extends TileEntity> clazz, String... names) {
        TileMappings.putLegacy("hbm", clazz, names);
        TileMappings.putLegacy("hbmspace", clazz, names);
    }
}
