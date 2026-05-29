package com.hbmspace.enums;

import com.hbm.tileentity.machine.rbmk.RBMKColumn.ColumnType;

public final class EnumAddonRBMKColumn {

    public static ColumnType BURNER;

    private static boolean initialized = false;

    private EnumAddonRBMKColumn() {}

    public static void init() {
        if (initialized) return;
        initialized = true;
        BURNER = EnumAddonTypes.addEnum(
                ColumnType.class,
                "BURNER",
                new Class<?>[]{ int.class },
                140
        );

        EnumAddonTypes.updateStaticValuesField(ColumnType.class, "VALUES");
    }
}
