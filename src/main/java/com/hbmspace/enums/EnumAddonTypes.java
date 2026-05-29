package com.hbmspace.enums;

import com.hbm.blocks.PlantEnums;
import net.minecraftforge.common.util.EnumHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import static com.hbm.lib.internal.UnsafeHolder.U;

/**
 * Generic utility for adding values to any enum at runtime and updating
 * fields that cache the enum's values array.
 *
 * @author Th3_Sl1ze
 */
public final class EnumAddonTypes {

    private EnumAddonTypes() {}

    public static void init() {
        EnumAddonWatzTypes.init();
        EnumAddonWasteTypes.init();
        EnumAddonRBMKColumn.init();
        EnumAddonRBMKRodTypes.init();
        EnumAddonBedrockOreTypes.init();
        EnumAddonFlowerPlantTypes.init();
    }

    /**
     * Adds a new constant to the given enum type.
     *
     * @param enumClass  the enum class to extend
     * @param name       the name of the new constant (must be unique)
     * @param paramTypes the parameter types of the enum constructor to invoke
     *                   (excluding the synthetic {@code String name, int ordinal})
     * @param args       the arguments matching {@code paramTypes}
     * @param <E>        enum type
     * @return the newly created enum constant
     */
    public static <E extends Enum<E>> E addEnum(Class<E> enumClass, String name,
                                                Class<?>[] paramTypes, Object... args) {
        for(E existing : enumClass.getEnumConstants()) {
            if(existing.name().equals(name)) {
                return existing;
            }
        }
        try {
            return EnumHelper.addEnum(enumClass, name, paramTypes, args);
        } catch (RuntimeException e) {
            return addEnumUnsafe(enumClass, name, args, e);
        }
    }

    private static <E extends Enum<E>> E addEnumUnsafe(Class<E> enumClass, String name,
                                                       Object[] args, RuntimeException original) {
        if (!isKnownAddonEnum(enumClass)) {
            throw original;
        }

        try {
            E[] previousValues = enumClass.getEnumConstants();
            int ordinal = previousValues.length;
            E newValue = enumClass.cast(U.allocateInstance(enumClass));

            setEnumBaseFields(newValue, name, ordinal);
            setKnownAddonFields(enumClass, newValue, args);
            appendEnumValue(enumClass, previousValues, newValue);
            cleanEnumCache(enumClass);

            return newValue;
        } catch (Exception fallbackFailure) {
            original.addSuppressed(fallbackFailure);
            throw original;
        }
    }

    private static boolean isKnownAddonEnum(Class<?> enumClass) {
        String name = enumClass.getName();
        return name.equals("com.hbm.items.machine.ItemWatzPellet$EnumWatzType")
                || name.equals("com.hbm.items.special.ItemWasteShort$WasteClass")
                || name.equals("com.hbm.tileentity.machine.rbmk.RBMKColumn$ColumnType")
                || name.equals("com.hbm.items.machine.ItemRBMKRod$EnumBurnFunc")
                || name.equals("com.hbm.items.machine.ItemRBMKRod$EnumDepleteFunc")
                || name.equals("com.hbm.items.special.ItemBedrockOreNew$BedrockOreType")
                || name.equals("com.hbm.blocks.PlantEnums$EnumFlowerPlantType");
    }

    private static void setEnumBaseFields(Enum<?> value, String name, int ordinal) throws NoSuchFieldException {
        setInstanceFieldUnsafe(findFieldInHierarchy(Enum.class, "name"), value, name);
        setInstanceIntFieldUnsafe(findFieldInHierarchy(Enum.class, "ordinal"), value, ordinal);
    }

    private static void setKnownAddonFields(Class<?> enumClass, Object value, Object[] args)
            throws NoSuchFieldException {
        String className = enumClass.getName();

        switch (className) {
            case "com.hbm.items.machine.ItemWatzPellet$EnumWatzType":
                setInstanceIntFieldUnsafe(findFieldInHierarchy(enumClass, "colorLight"), value, ((Number) args[0]).intValue());
                setInstanceIntFieldUnsafe(findFieldInHierarchy(enumClass, "colorDark"), value, ((Number) args[1]).intValue());
                setInstanceDoubleFieldUnsafe(findFieldInHierarchy(enumClass, "passive"), value, ((Number) args[2]).doubleValue());
                setInstanceDoubleFieldUnsafe(findFieldInHierarchy(enumClass, "heatEmission"), value, ((Number) args[3]).doubleValue());
                setInstanceDoubleFieldUnsafe(findFieldInHierarchy(enumClass, "mudContent"), value, ((Number) args[4]).doubleValue() / 2D);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "burnFunc"), value, args[5]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "heatDiv"), value, args[6]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "absorbFunc"), value, args[7]);
                setInstanceDoubleFieldUnsafe(findFieldInHierarchy(enumClass, "yield"), value, 500_000_000D);
                return;
            case "com.hbm.items.special.ItemWasteShort$WasteClass":
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "name"), value, args[0]);
                setInstanceIntFieldUnsafe(findFieldInHierarchy(enumClass, "liquid"), value, ((Number) args[1]).intValue());
                setInstanceIntFieldUnsafe(findFieldInHierarchy(enumClass, "gas"), value, ((Number) args[2]).intValue());
                return;
            case "com.hbm.tileentity.machine.rbmk.RBMKColumn$ColumnType":
                setInstanceIntFieldUnsafe(findFieldInHierarchy(enumClass, "offset"), value, ((Number) args[0]).intValue());
                return;
            case "com.hbm.items.machine.ItemRBMKRod$EnumBurnFunc":
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "title"), value, args[0]);
                return;
            case "com.hbm.items.machine.ItemRBMKRod$EnumDepleteFunc":
                return;
            case "com.hbm.items.special.ItemBedrockOreNew$BedrockOreType":
                setInstanceIntFieldUnsafe(findFieldInHierarchy(enumClass, "light"), value, ((Number) args[0]).intValue());
                setInstanceIntFieldUnsafe(findFieldInHierarchy(enumClass, "dark"), value, ((Number) args[1]).intValue());
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "suffix"), value, args[2]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "primary1"), value, args[3]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "primary2"), value, args[4]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductAcid1"), value, args[5]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductAcid2"), value, args[6]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductAcid3"), value, args[7]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductSolvent1"), value, args[8]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductSolvent2"), value, args[9]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductSolvent3"), value, args[10]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductRad1"), value, args[11]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductRad2"), value, args[12]);
                setInstanceFieldUnsafe(findFieldInHierarchy(enumClass, "byproductRad3"), value, args[13]);
                return;
            case "com.hbm.blocks.PlantEnums$EnumFlowerPlantType":
                setInstanceBooleanFieldUnsafe(findFieldInHierarchy(enumClass, "needsOil"), value, (Boolean) args[0]);
                return;
            default:
                throw new IllegalArgumentException("Unsupported addon enum: " + className);
        }
    }

    private static <E extends Enum<E>> void appendEnumValue(Class<E> enumClass, E[] previousValues, E newValue)
            throws NoSuchFieldException {
        E[] newValues = (E[]) Array.newInstance(enumClass, previousValues.length + 1);
        System.arraycopy(previousValues, 0, newValues, 0, previousValues.length);
        newValues[previousValues.length] = newValue;
        setStaticFieldUnsafe(findValuesField(enumClass), newValues);
    }

    private static Field findValuesField(Class<?> enumClass) throws NoSuchFieldException {
        for (Field field : enumClass.getDeclaredFields()) {
            String name = field.getName();
            if (name.equals("$VALUES") || name.equals("ENUM$VALUES")) {
                return field;
            }
        }

        String valueType = "[L" + enumClass.getName().replace('.', '/') + ";";
        for (Field field : enumClass.getDeclaredFields()) {
            if (field.getType().getName().replace('.', '/').equals(valueType)) {
                return field;
            }
        }

        throw new NoSuchFieldException("Could not find values field for enum " + enumClass.getName());
    }

    private static void cleanEnumCache(Class<?> enumClass) {
        try {
            setInstanceFieldUnsafe(findFieldInHierarchy(Class.class, "enumConstants"), enumClass, null);
            setInstanceFieldUnsafe(findFieldInHierarchy(Class.class, "enumConstantDirectory"), enumClass, null);
        } catch (Exception ignored) {
        }
    }

    /**
     * Replaces a <b>static</b> field's value with the current {@code values()}
     * array of the given enum.  Useful for fields like
     * {@code private static final MyEnum[] VALUES = values();}.
     *
     * @param enumClass the enum whose {@code values()} to use
     * @param fieldName the name of the static field to update
     * @param <E>       enum type
     */
    public static <E extends Enum<E>> void updateStaticValuesField(Class<E> enumClass,
                                                                   String fieldName) {
        updateStaticValuesField(enumClass, enumClass, fieldName);
    }

    /**
     * Replaces a <b>static</b> field declared in {@code ownerClass} with the
     * current {@code values()} array of {@code enumClass}.
     *
     * @param enumClass  the enum whose {@code values()} to use
     * @param ownerClass the class that declares the static field
     * @param fieldName  the name of the static field
     * @param <E>        enum type
     */
    public static <E extends Enum<E>> void updateStaticValuesField(Class<E> enumClass,
                                                                   Class<?> ownerClass,
                                                                   String fieldName) {
        try {
            Field field = findFieldInHierarchy(ownerClass, fieldName);
            setStaticFieldUnsafe(field, enumClass.getEnumConstants());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Replaces an <b>instance</b> field on one or more objects with the current
     * {@code values()} array of the given enum.  The field is looked up starting
     * from {@code declaringClass} and walking up the hierarchy.
     *
     * @param enumClass      the enum whose {@code values()} to use
     * @param declaringClass the class (or a subclass) where the field is declared
     * @param fieldName      the name of the instance field
     * @param targets        one or more object instances to update
     * @param <E>            enum type
     */
    public static <E extends Enum<E>> void updateInstanceField(Class<E> enumClass,
                                                               Class<?> declaringClass,
                                                               String fieldName,
                                                               Object... targets) {
        try {
            Field field = findFieldInHierarchy(declaringClass, fieldName);
            Object newValues = enumClass.getEnumConstants();
            for (Object target : targets) {
                setInstanceFieldUnsafe(field, target, newValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets any static field to an arbitrary value via Unsafe.
     *
     * @param ownerClass the class declaring the field
     * @param fieldName  the field name
     * @param value      the new value
     */
    public static void setStaticField(Class<?> ownerClass, String fieldName, Object value) {
        try {
            Field field = findFieldInHierarchy(ownerClass, fieldName);
            setStaticFieldUnsafe(field, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets any instance field on the given target to an arbitrary value via
     * Unsafe.
     *
     * @param declaringClass the class (or superclass) where the field is declared
     * @param fieldName      the field name
     * @param target         the object instance
     * @param value          the new value
     */
    public static void setInstanceField(Class<?> declaringClass, String fieldName,
                                        Object target, Object value) {
        try {
            Field field = findFieldInHierarchy(declaringClass, fieldName);
            setInstanceFieldUnsafe(field, target, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Walks the class hierarchy upwards looking for a declared field.
     *
     * @throws NoSuchFieldException if the field is not found in any superclass
     */
    private static Field findFieldInHierarchy(Class<?> clazz, String fieldName)
            throws NoSuchFieldException {
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                return c.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
            }
        }
        throw new NoSuchFieldException("Field '" + fieldName
                + "' not found in hierarchy of " + clazz.getName());
    }

    private static void setStaticFieldUnsafe(Field field, Object value) {
        Object base = U.staticFieldBase(field);
        long offset = U.staticFieldOffset(field);
        U.putReference(base, offset, value);
    }

    private static void setInstanceFieldUnsafe(Field field, Object target, Object value) {
        long offset = U.objectFieldOffset(field);
        U.putReference(target, offset, value);
    }

    private static void setInstanceIntFieldUnsafe(Field field, Object target, int value) {
        long offset = U.objectFieldOffset(field);
        U.putInt(target, offset, value);
    }

    private static void setInstanceDoubleFieldUnsafe(Field field, Object target, double value) {
        long offset = U.objectFieldOffset(field);
        U.putDouble(target, offset, value);
    }

    private static void setInstanceBooleanFieldUnsafe(Field field, Object target, boolean value) {
        long offset = U.objectFieldOffset(field);
        U.putBoolean(target, offset, value);
    }
}
