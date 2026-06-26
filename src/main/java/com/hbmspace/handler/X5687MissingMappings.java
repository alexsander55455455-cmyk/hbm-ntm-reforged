package com.hbmspace.handler;

import com.hbmspace.Tags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public final class X5687MissingMappings {

    private static final String HBM = "hbm";
    private static final String HBMSPACE = Tags.MODID;
    private static final Map<String, ResourceLocation> BLOCK_ALIASES = new HashMap<>();
    private static final Map<String, ResourceLocation> ITEM_ALIASES = new HashMap<>();

    static {
        addSpaceBlockAlias("machine_dish_controller", "dish_control");
        addSpaceBlockAlias("sapling_pvc", "sapling");
        addSpaceBlockAlias("laythe_short", "laythe_seagrass");
        addSpaceBlockAlias("laythe_glow", "laythe_glowgrass");
        addSpaceBlockAlias("spike_cacti", "rubber_tall");
        addSpaceBlockAlias("vinyl_vines", "vinyl_vine");

        addHbmBlockAlias("ore_cinnebar", "ore_cinnabar");
        addHbmBlockAlias("ore_depth_cinnebar", "ore_depth_cinnabar");
        addHbmBlockAlias("sliding_blast_door_legacy", "sliding_blast_door");

        addHbmItemAlias("cinnebar", "cinnabar");
        addHbmItemAlias("crystal_cinnebar", "crystal_cinnabar");
        addHbmItemAlias("ore_cinnebar", "ore_cinnabar");
        addHbmItemAlias("ore_depth_cinnebar", "ore_depth_cinnabar");
        addHbmItemAlias("laser_crystal_dnt", "laser_crystal_bale");

        addSpaceItemAlias("rubber_leaves", "rubber_leaf");
        addSpaceItemAlias("pet_leaves", "pet_leaf");
        addSpaceItemAlias("record_gs", "gs");
        addSpaceItemAlias("record_gp", "gp");
        addSpaceItemAlias("record_el", "el");
        addSpaceItemAlias("full_drive", "hard_drive_full");
        addSpaceItemAlias("rp_fuselage_20_12", "rp_f_20_12");
        addSpaceItemAlias("rp_fuselage_20_6", "rp_f_20_6");
        addSpaceItemAlias("rp_fuselage_20_3", "rp_f_20_3");
        addSpaceItemAlias("rp_fuselage_20_1", "rp_f_20_1");
        addSpaceItemAlias("rp_legs_20", "rp_l_20");
        addSpaceItemAlias("rp_capsule_20", "rp_c_20");
        addSpaceItemAlias("rp_station_core_20", "rp_sc_20");
        addSpaceItemAlias("rp_fuselage_20_12_hydrazine", "mp_fuselage_20_hydrazine");
    }

    private X5687MissingMappings() {
    }

    @SubscribeEvent
    public static void onMissingBlocks(RegistryEvent.MissingMappings<Block> event) {
        remapAll(event, BLOCK_ALIASES);
    }

    @SubscribeEvent
    public static void onMissingItems(RegistryEvent.MissingMappings<Item> event) {
        remapAll(event, ITEM_ALIASES);
    }

    @SubscribeEvent
    public static void onMissingEntities(RegistryEvent.MissingMappings<EntityEntry> event) {
        remapAll(event, null);
    }

    private static void addSpaceBlockAlias(String oldPath, String targetPath) {
        addAlias(BLOCK_ALIASES, oldPath, HBMSPACE, targetPath);
    }

    private static void addSpaceItemAlias(String oldPath, String targetPath) {
        addAlias(ITEM_ALIASES, oldPath, HBMSPACE, targetPath);
    }

    private static void addHbmBlockAlias(String oldPath, String targetPath) {
        addAlias(BLOCK_ALIASES, oldPath, HBM, targetPath);
    }

    private static void addHbmItemAlias(String oldPath, String targetPath) {
        addAlias(ITEM_ALIASES, oldPath, HBM, targetPath);
    }

    private static void addAlias(Map<String, ResourceLocation> aliases, String oldPath, String targetNamespace, String targetPath) {
        ResourceLocation target = new ResourceLocation(targetNamespace, targetPath);
        aliases.put(oldPath, target);
        aliases.put("tile." + oldPath, target);
        aliases.put("item." + oldPath, target);
    }

    private static <T extends IForgeRegistryEntry<T>> void remapAll(RegistryEvent.MissingMappings<T> event, Map<String, ResourceLocation> aliases) {
        IForgeRegistry<T> registry = event.getRegistry();
        for (RegistryEvent.MissingMappings.Mapping<T> mapping : event.getAllMappings()) {
            T target = findTarget(registry, mapping.key, aliases);
            if (target != null) {
                mapping.remap(target);
            }
        }
    }

    private static <T extends IForgeRegistryEntry<T>> T findTarget(IForgeRegistry<T> registry, ResourceLocation oldKey, Map<String, ResourceLocation> aliases) {
        String path = oldKey.getPath();
        String normalized = normalizeLegacyPath(path);

        T target = get(registry, aliases == null ? null : aliases.get(path));
        if (target != null) return target;

        target = get(registry, aliases == null ? null : aliases.get(normalized));
        if (target != null) return target;

        target = get(registry, new ResourceLocation(oldKey.getNamespace(), normalized));
        if (target != null) return target;

        if (HBM.equals(oldKey.getNamespace())) {
            target = get(registry, new ResourceLocation(HBMSPACE, normalized));
            if (target != null) return target;
        }

        if (HBMSPACE.equals(oldKey.getNamespace())) {
            target = get(registry, new ResourceLocation(HBM, normalized));
            if (target != null) return target;
        }

        return null;
    }

    private static String normalizeLegacyPath(String path) {
        if (path.startsWith("tile.")) return path.substring("tile.".length());
        if (path.startsWith("item.")) return path.substring("item.".length());
        return path;
    }

    private static <T extends IForgeRegistryEntry<T>> T get(IForgeRegistry<T> registry, ResourceLocation key) {
        return key == null ? null : registry.getValue(key);
    }
}
