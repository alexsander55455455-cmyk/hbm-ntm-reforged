package com.hbmspace.enums;


import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.PlantEnums;
import com.hbm.blocks.generic.BlockMeta;
import com.hbm.blocks.generic.BlockNTMFlower;
import com.hbm.render.block.BlockBakeFrame;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class EnumAddonFlowerPlantTypes {

    public static PlantEnums.EnumFlowerPlantType STRAWBERRY;
    public static PlantEnums.EnumFlowerPlantType MINT;

    private static final Class<?>[] PARAM_TYPES = new Class<?>[] { boolean.class };
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        STRAWBERRY = addPlantType("STRAWBERRY", false);
        MINT = addPlantType("MINT", false);

        PlantEnums.EnumFlowerPlantType[] flowerTypes = uniqueValues();
        EnumAddonTypes.setStaticField(PlantEnums.EnumFlowerPlantType.class, "VALUES", flowerTypes);
        EnumAddonTypes.setInstanceField(BlockNTMFlower.class, "blockEnum", ModBlocks.plant_flower, flowerTypes);

        EnumAddonTypes.setInstanceField(
                BlockMeta.class,
                "META_COUNT",
                ModBlocks.plant_flower,
                (short) flowerTypes.length
        );

        BlockBakeFrame[] newFrames = Arrays.stream(flowerTypes)
                .sorted(Comparator.comparing(Enum::ordinal))
                .map(Enum::name)
                .map(name -> "hbm:" + name.toLowerCase(Locale.US))
                .map(texture -> BlockBakeFrame.cross("plant_flower_" + texture.split(":")[1]))
                .toArray(BlockBakeFrame[]::new);

        EnumAddonTypes.setInstanceField(
                BlockMeta.class,
                "blockFrames",
                ModBlocks.plant_flower,
                newFrames
        );
    }

    private static PlantEnums.EnumFlowerPlantType addPlantType(String name, boolean needsOil) {
        return EnumAddonTypes.addEnum(PlantEnums.EnumFlowerPlantType.class, name, PARAM_TYPES, needsOil);
    }

    private static PlantEnums.EnumFlowerPlantType[] uniqueValues() {
        Map<String, PlantEnums.EnumFlowerPlantType> unique = new LinkedHashMap<>();
        Arrays.stream(PlantEnums.EnumFlowerPlantType.values())
                .sorted(Comparator.comparing(Enum::ordinal))
                .forEach(type -> unique.putIfAbsent(type.name(), type));
        return unique.values().toArray(new PlantEnums.EnumFlowerPlantType[0]);
    }

}
