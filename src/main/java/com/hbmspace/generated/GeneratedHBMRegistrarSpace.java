// AUTO-GENERATED FILE. DO NOT MODIFY.
package com.hbmspace.generated;

import com.hbm.tileentity.IConfigurableMachine;
import com.hbmspace.Tags;
import com.hbmspace.blocks.generic.BlockGeysierDCM;
import com.hbmspace.blocks.generic.BlockOrrery;
import com.hbmspace.blocks.generic.BlockVolcanoV2;
import com.hbmspace.blocks.machine.BlockAtmosphereEditor;
import com.hbmspace.entity.effect.EntityDepress;
import com.hbmspace.entity.missile.EntityCombatDropPod;
import com.hbmspace.entity.missile.EntityRideableRocket;
import com.hbmspace.entity.mob.EntityCreeperFlesh;
import com.hbmspace.entity.mob.EntityDepthSquid;
import com.hbmspace.entity.mob.EntityMoonCow;
import com.hbmspace.entity.mob.EntityScrapFish;
import com.hbmspace.entity.mob.EntityScutterfish;
import com.hbmspace.entity.mob.EntityScuttlecrab;
import com.hbmspace.entity.mob.EntitySifterEel;
import com.hbmspace.entity.mob.siege.EntitySiegeCraft;
import com.hbmspace.entity.mob.siege.EntitySiegeUFO;
import com.hbmspace.entity.projectile.EntitySiegeLaser;
import com.hbmspace.items.ModItemsSpace;
import com.hbmspace.main.SpaceMain;
import com.hbmspace.render.entity.RenderEmpty;
import com.hbmspace.render.entity.missile.RenderRocketCustom;
import com.hbmspace.render.entity.mob.RenderEntityMulti;
import com.hbmspace.render.entity.mob.RenderMoonCow;
import com.hbmspace.render.entity.mob.RenderScutter;
import com.hbmspace.render.entity.mob.RenderSiegeCraft;
import com.hbmspace.render.entity.mob.RenderSiegeUFO;
import com.hbmspace.render.entity.projectile.RenderSiegeLaser;
import com.hbmspace.render.item.ItemRenderSwarmMember;
import com.hbmspace.render.tileentity.RenderAirScrubber;
import com.hbmspace.render.tileentity.RenderAlkylation;
import com.hbmspace.render.tileentity.RenderAtmoTower;
import com.hbmspace.render.tileentity.RenderAtmosphericCompressor;
import com.hbmspace.render.tileentity.RenderCombatPod;
import com.hbmspace.render.tileentity.RenderCryoDistill;
import com.hbmspace.render.tileentity.RenderDishControl;
import com.hbmspace.render.tileentity.RenderDriveProcessor;
import com.hbmspace.render.tileentity.RenderDysonConverterAnatmogenesis;
import com.hbmspace.render.tileentity.RenderDysonConverterHE;
import com.hbmspace.render.tileentity.RenderDysonConverterTU;
import com.hbmspace.render.tileentity.RenderDysonLauncher;
import com.hbmspace.render.tileentity.RenderDysonReceiver;
import com.hbmspace.render.tileentity.RenderGasDock;
import com.hbmspace.render.tileentity.RenderHTR3;
import com.hbmspace.render.tileentity.RenderHTRF4;
import com.hbmspace.render.tileentity.RenderHTRNeo;
import com.hbmspace.render.tileentity.RenderHydroponic;
import com.hbmspace.render.tileentity.RenderLPW2;
import com.hbmspace.render.tileentity.RenderLaunchPadRocket;
import com.hbmspace.render.tileentity.RenderMagma;
import com.hbmspace.render.tileentity.RenderMilkReformer;
import com.hbmspace.render.tileentity.RenderOrbitalComputer;
import com.hbmspace.render.tileentity.RenderOrbitalStation;
import com.hbmspace.render.tileentity.RenderOrrery;
import com.hbmspace.render.tileentity.RenderRadiator;
import com.hbmspace.render.tileentity.RenderRocketAssembly;
import com.hbmspace.render.tileentity.RenderSolarPanel;
import com.hbmspace.render.tileentity.RenderStardar;
import com.hbmspace.render.tileentity.RenderTransporterRocket;
import com.hbmspace.render.tileentity.RenderVacuumCircuit;
import com.hbmspace.render.tileentity.RenderVol2;
import com.hbmspace.render.tileentity.RenderXenonThruster;
import com.hbmspace.tileentity.bomb.TileEntityLaunchPadRocket;
import com.hbmspace.tileentity.machine.TileEntityAirPump;
import com.hbmspace.tileentity.machine.TileEntityAirScrubber;
import com.hbmspace.tileentity.machine.TileEntityAlgaeFilm;
import com.hbmspace.tileentity.machine.TileEntityAtmoTower;
import com.hbmspace.tileentity.machine.TileEntityAtmosphericCompressor;
import com.hbmspace.tileentity.machine.TileEntityDishControl;
import com.hbmspace.tileentity.machine.TileEntityDysonConverterAnatmogenesis;
import com.hbmspace.tileentity.machine.TileEntityDysonConverterHE;
import com.hbmspace.tileentity.machine.TileEntityDysonConverterTU;
import com.hbmspace.tileentity.machine.TileEntityDysonLauncher;
import com.hbmspace.tileentity.machine.TileEntityDysonReceiver;
import com.hbmspace.tileentity.machine.TileEntityFurnaceSpace;
import com.hbmspace.tileentity.machine.TileEntityHydroponic;
import com.hbmspace.tileentity.machine.TileEntityMachineCryoDistill;
import com.hbmspace.tileentity.machine.TileEntityMachineDischarger;
import com.hbmspace.tileentity.machine.TileEntityMachineDriveProcessor;
import com.hbmspace.tileentity.machine.TileEntityMachineGasDock;
import com.hbmspace.tileentity.machine.TileEntityMachineHTR3;
import com.hbmspace.tileentity.machine.TileEntityMachineHTRF4;
import com.hbmspace.tileentity.machine.TileEntityMachineHTRNeo;
import com.hbmspace.tileentity.machine.TileEntityMachineLPW2;
import com.hbmspace.tileentity.machine.TileEntityMachineMagma;
import com.hbmspace.tileentity.machine.TileEntityMachineMilkReformer;
import com.hbmspace.tileentity.machine.TileEntityMachineRocketAssembly;
import com.hbmspace.tileentity.machine.TileEntityMachineSolarPanel;
import com.hbmspace.tileentity.machine.TileEntityMachineStardar;
import com.hbmspace.tileentity.machine.TileEntityMachineVacuumCircuit;
import com.hbmspace.tileentity.machine.TileEntityMachineWarController;
import com.hbmspace.tileentity.machine.TileEntityOrbStation;
import com.hbmspace.tileentity.machine.TileEntityOrbitalStation;
import com.hbmspace.tileentity.machine.TileEntityOrbitalStationComputer;
import com.hbmspace.tileentity.machine.TileEntityOrbitalStationLauncher;
import com.hbmspace.tileentity.machine.TileEntityRadiator;
import com.hbmspace.tileentity.machine.TileEntityStationPropulsionCreative;
import com.hbmspace.tileentity.machine.TileEntityTransporterRocket;
import com.hbmspace.tileentity.machine.TileEntityXenonThruster;
import com.hbmspace.tileentity.machine.oil.TileEntityMachineAlkylation;
import com.hbmspace.tileentity.machine.rbmk.TileEntityRBMKBurner;
import com.hbmspace.tileentity.machine.storage.TileEntityCombatDropPod;
import java.lang.Class;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * AUTO-GENERATED FILE. DO NOT MODIFY.
 */
public final class GeneratedHBMRegistrarSpace {
    public static final List<Class<? extends IConfigurableMachine>> CONFIGURABLE_MACHINES = new ArrayList<>();

    static {
        CONFIGURABLE_MACHINES.add(TileEntityRadiator.class);
    }

    /**
     * @param startId The starting ID for entity registration.
     * @return The next available entity ID.
     */
    public static int registerEntities(int startId) {
        int currentId = startId;
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_rideable_rocket"), EntityRideableRocket.class, "entity_rideable_rocket", currentId++, SpaceMain.instance, 1000, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_meme_ufo"), EntitySiegeUFO.class, "entity_meme_ufo", currentId++, SpaceMain.instance, 250, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_micro_ufo"), EntitySiegeCraft.class, "entity_micro_ufo", currentId++, SpaceMain.instance, 250, 3, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_ntm_siege_laser"), EntitySiegeLaser.class, "entity_ntm_siege_laser", currentId++, SpaceMain.instance, 1000, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_rideable_rocket_dummy"), EntityRideableRocket.EntityRideableRocketDummy.class, "entity_rideable_rocket_dummy", currentId++, SpaceMain.instance, 1000, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_moon_cow"), EntityMoonCow.class, "entity_moon_cow", currentId++, SpaceMain.instance, 80, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_depress"), EntityDepress.class, "entity_depress", currentId++, SpaceMain.instance, 250, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_combat_pod"), EntityCombatDropPod.class, "entity_combat_pod", currentId++, SpaceMain.instance, 1000, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_scuttlecrab"), EntityScuttlecrab.class, "entity_scuttlecrab", currentId++, SpaceMain.instance, 80, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_scrapfish"), EntityScrapFish.class, "entity_scrapfish", currentId++, SpaceMain.instance, 80, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_siftereel"), EntitySifterEel.class, "entity_siftereel", currentId++, SpaceMain.instance, 80, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_scutterfish"), EntityScutterfish.class, "entity_scutterfish", currentId++, SpaceMain.instance, 80, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_mob_flesh_creeper"), EntityCreeperFlesh.class, "entity_mob_flesh_creeper", currentId++, SpaceMain.instance, 250, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MODID, "entity_depthsquid"), EntityDepthSquid.class, "entity_depthsquid", currentId++, SpaceMain.instance, 80, 1, true);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_meme_ufo"), 3158064, 8388608);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_micro_ufo"), 6002238, 12628614);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_moon_cow"), 15520630, 2501188);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_scuttlecrab"), 15825233, 15588027);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_scrapfish"), 14653493, 5312019);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_siftereel"), 6002238, 12628614);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_scutterfish"), 13158861, 8751252);
        EntityRegistry.registerEgg(new ResourceLocation(Tags.MODID, "entity_depthsquid"), 46303, 90245);
        return currentId;
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityDysonConverterHE.class, new ResourceLocation(Tags.MODID, "tileentity_dyson_converter_he"));
        GameRegistry.registerTileEntity(TileEntityAlgaeFilm.class, new ResourceLocation(Tags.MODID, "tileentity_algae_film"));
        GameRegistry.registerTileEntity(TileEntityTransporterRocket.class, new ResourceLocation(Tags.MODID, "tileentity_transporter_rocket"));
        GameRegistry.registerTileEntity(TileEntityDysonReceiver.class, new ResourceLocation(Tags.MODID, "tileentity_dyson_receiver"));
        GameRegistry.registerTileEntity(TileEntityMachineWarController.class, new ResourceLocation(Tags.MODID, "tileentity_machine_war_controller"));
        GameRegistry.registerTileEntity(TileEntityMachineMilkReformer.class, new ResourceLocation(Tags.MODID, "tileentity_machine_milk_reformer"));
        GameRegistry.registerTileEntity(BlockVolcanoV2.TileEntityLightningVolcano.class, new ResourceLocation(Tags.MODID, "tileentity_lightning_volcano"));
        GameRegistry.registerTileEntity(TileEntityMachineDriveProcessor.class, new ResourceLocation(Tags.MODID, "tileentity_machine_drive_processor"));
        GameRegistry.registerTileEntity(TileEntityAtmoTower.class, new ResourceLocation(Tags.MODID, "tileentity_atmo_tower"));
        GameRegistry.registerTileEntity(TileEntityHydroponic.class, new ResourceLocation(Tags.MODID, "tileentity_hydroponic"));
        GameRegistry.registerTileEntity(TileEntityMachineRocketAssembly.class, new ResourceLocation(Tags.MODID, "tileentity_machine_rocket_assembly"));
        GameRegistry.registerTileEntity(TileEntityMachineHTRNeo.class, new ResourceLocation(Tags.MODID, "tileentity_machine_htrneo"));
        GameRegistry.registerTileEntity(TileEntityMachineSolarPanel.class, new ResourceLocation(Tags.MODID, "tileentity_machine_solar_panel"));
        GameRegistry.registerTileEntity(TileEntityMachineHTRF4.class, new ResourceLocation(Tags.MODID, "tileentity_machine_htrf4"));
        GameRegistry.registerTileEntity(TileEntityRBMKBurner.class, new ResourceLocation(Tags.MODID, "tileentity_rbmkburner"));
        GameRegistry.registerTileEntity(TileEntityOrbitalStationComputer.class, new ResourceLocation(Tags.MODID, "tileentity_orbital_station_computer"));
        GameRegistry.registerTileEntity(TileEntityDysonLauncher.class, new ResourceLocation(Tags.MODID, "tileentity_dyson_launcher"));
        GameRegistry.registerTileEntity(TileEntityLaunchPadRocket.class, new ResourceLocation(Tags.MODID, "tileentity_launch_pad_rocket"));
        GameRegistry.registerTileEntity(TileEntityMachineMagma.class, new ResourceLocation(Tags.MODID, "tileentity_machine_magma"));
        GameRegistry.registerTileEntity(TileEntityAirPump.class, new ResourceLocation(Tags.MODID, "tileentity_air_pump"));
        GameRegistry.registerTileEntity(TileEntityCombatDropPod.class, new ResourceLocation(Tags.MODID, "tileentity_combat_drop_pod"));
        GameRegistry.registerTileEntity(TileEntityMachineAlkylation.class, new ResourceLocation(Tags.MODID, "tileentity_machine_alkylation"));
        GameRegistry.registerTileEntity(TileEntityMachineCryoDistill.class, new ResourceLocation(Tags.MODID, "tileentity_machine_cryo_distill"));
        GameRegistry.registerTileEntity(TileEntityAirScrubber.class, new ResourceLocation(Tags.MODID, "tileentity_air_scrubber"));
        GameRegistry.registerTileEntity(BlockGeysierDCM.TileEntityDCM.class, new ResourceLocation(Tags.MODID, "tileentity_dcm"));
        GameRegistry.registerTileEntity(TileEntityDysonConverterAnatmogenesis.class, new ResourceLocation(Tags.MODID, "tileentity_dyson_converter_anatmogenesis"));
        GameRegistry.registerTileEntity(TileEntityMachineStardar.class, new ResourceLocation(Tags.MODID, "tileentity_machine_stardar"));
        GameRegistry.registerTileEntity(TileEntityMachineVacuumCircuit.class, new ResourceLocation(Tags.MODID, "tileentity_machine_vacuum_circuit"));
        GameRegistry.registerTileEntity(TileEntityMachineGasDock.class, new ResourceLocation(Tags.MODID, "tileentity_machine_gas_dock"));
        GameRegistry.registerTileEntity(TileEntityMachineLPW2.class, new ResourceLocation(Tags.MODID, "tileentity_machine_lpw2"));
        GameRegistry.registerTileEntity(BlockOrrery.TileEntityOrrery.class, new ResourceLocation(Tags.MODID, "tileentity_orrery"));
        GameRegistry.registerTileEntity(TileEntityDishControl.class, new ResourceLocation(Tags.MODID, "tileentity_dish_control"));
        GameRegistry.registerTileEntity(BlockAtmosphereEditor.TileEntityAtmosphereEditor.class, new ResourceLocation(Tags.MODID, "tileentity_atmosphere_editor"));
        GameRegistry.registerTileEntity(TileEntityXenonThruster.class, new ResourceLocation(Tags.MODID, "tileentity_xenon_thruster"));
        GameRegistry.registerTileEntity(TileEntityOrbitalStation.class, new ResourceLocation(Tags.MODID, "tileentity_orbital_station"));
        GameRegistry.registerTileEntity(TileEntityFurnaceSpace.class, new ResourceLocation(Tags.MODID, "tileentity_furnace_space"));
        GameRegistry.registerTileEntity(TileEntityAtmosphericCompressor.class, new ResourceLocation(Tags.MODID, "tileentity_atmospheric_compressor"));
        GameRegistry.registerTileEntity(TileEntityStationPropulsionCreative.class, new ResourceLocation(Tags.MODID, "tileentity_station_propulsion_creative"));
        GameRegistry.registerTileEntity(TileEntityMachineHTR3.class, new ResourceLocation(Tags.MODID, "tileentity_machine_htr3"));
        GameRegistry.registerTileEntity(TileEntityOrbitalStationLauncher.class, new ResourceLocation(Tags.MODID, "tileentity_orbital_station_launcher"));
        GameRegistry.registerTileEntity(TileEntityDysonConverterTU.class, new ResourceLocation(Tags.MODID, "tileentity_dyson_converter_tu"));
        GameRegistry.registerTileEntity(TileEntityRadiator.class, new ResourceLocation(Tags.MODID, "tileentity_radiator"));
        GameRegistry.registerTileEntity(TileEntityMachineDischarger.class, new ResourceLocation(Tags.MODID, "tileentity_machine_discharger"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySiegeLaser.class, RenderSiegeLaser.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySiegeCraft.class, RenderSiegeCraft.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityScutterfish.class, RenderScutter.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityDepress.class, RenderEmpty.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityRideableRocket.EntityRideableRocketDummy.class, com.hbmspace.render.misc.RenderEmpty.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityScuttlecrab.class, RenderEntityMulti.FACTORY_1);
        RenderingRegistry.registerEntityRenderingHandler(EntityDepthSquid.class, RenderEntityMulti.FACTORY_2);
        RenderingRegistry.registerEntityRenderingHandler(EntityScrapFish.class, RenderEntityMulti.FACTORY_3);
        RenderingRegistry.registerEntityRenderingHandler(EntitySifterEel.class, RenderEntityMulti.FACTORY_4);
        RenderingRegistry.registerEntityRenderingHandler(EntitySiegeUFO.class, RenderSiegeUFO.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityRideableRocket.class, RenderRocketCustom.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityMoonCow.class, RenderMoonCow.FACTORY);
    }

    @SideOnly(Side.CLIENT)
    public static void registerTileEntityRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityXenonThruster.class, new RenderXenonThruster());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMagma.class, new RenderMagma());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineVacuumCircuit.class, new RenderVacuumCircuit());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDysonConverterHE.class, new RenderDysonConverterHE());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasDock.class, new RenderGasDock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDishControl.class, new RenderDishControl());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMilkReformer.class, new RenderMilkReformer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDysonReceiver.class, new RenderDysonReceiver());
        ClientRegistry.bindTileEntitySpecialRenderer(BlockOrrery.TileEntityOrrery.class, new RenderOrrery());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOrbStation.class, new RenderOrbitalStation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAtmoTower.class, new RenderAtmoTower());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRocketAssembly.class, new RenderRocketAssembly());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDysonConverterAnatmogenesis.class, new RenderDysonConverterAnatmogenesis());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadiator.class, new RenderRadiator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineDriveProcessor.class, new RenderDriveProcessor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineHTRNeo.class, new RenderHTRNeo());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDysonLauncher.class, new RenderDysonLauncher());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAtmosphericCompressor.class, new RenderAtmosphericCompressor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDysonConverterTU.class, new RenderDysonConverterTU());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPadRocket.class, new RenderLaunchPadRocket());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAlkylation.class, new RenderAlkylation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHydroponic.class, new RenderHydroponic());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransporterRocket.class, new RenderTransporterRocket());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCryoDistill.class, new RenderCryoDistill());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCombatDropPod.class, new RenderCombatPod());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineLPW2.class, new RenderLPW2());
        ClientRegistry.bindTileEntitySpecialRenderer(BlockVolcanoV2.TileEntityLightningVolcano.class, new RenderVol2());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOrbitalStationComputer.class, new RenderOrbitalComputer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineStardar.class, new RenderStardar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineHTR3.class, new RenderHTR3());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineSolarPanel.class, new RenderSolarPanel());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAirScrubber.class, new RenderAirScrubber());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineHTRF4.class, new RenderHTRF4());
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRenderers() {
        ModItemsSpace.swarm_member.setTileEntityItemStackRenderer(new ItemRenderSwarmMember());
    }
}
