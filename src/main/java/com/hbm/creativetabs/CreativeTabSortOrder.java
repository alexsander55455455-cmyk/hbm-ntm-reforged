package com.hbm.creativetabs;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creative-tab item order from assets/hbm/creative_tab_order.txt.
 * Keys are registry paths (e.g. deco_sat_mapper), not Java field names.
 */
public final class CreativeTabSortOrder {

    public static final int UNKNOWN_SORT_INDEX = 1_500_000;

    private static final Map<String, Map<String, Integer>> TAB_ORDERS = new HashMap<>();
    private static final Map<String, List<String>> TAB_REGISTRY_ORDER = new HashMap<>();
    private static final Map<String, Integer> GLOBAL_FALLBACK = new HashMap<>();
    private static boolean loaded = false;

    private CreativeTabSortOrder() {}

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        try (InputStream stream = CreativeTabSortOrder.class.getResourceAsStream("/assets/hbm/creative_tab_order.txt")) {
            if (stream == null) {
                loaded = true;
                return;
            }
            String currentTab = null;
            Map<String, Integer> currentOrder = null;
            int global = 0;
            for (String rawLine : IOUtils.readLines(stream, StandardCharsets.UTF_8)) {
                String line = rawLine.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("@")) {
                    currentTab = line.substring(1);
                    currentOrder = new HashMap<>();
                    TAB_ORDERS.put(currentTab, currentOrder);
                    continue;
                }
                int split = line.indexOf('=');
                if (split <= 0 || currentTab == null || currentOrder == null) {
                    continue;
                }
                String name = line.substring(0, split);
                int idx = Integer.parseInt(line.substring(split + 1));
                currentOrder.put(name, idx);
                GLOBAL_FALLBACK.putIfAbsent(name, global++);
            }
            for (Map.Entry<String, Map<String, Integer>> tabEntry : TAB_ORDERS.entrySet()) {
                List<Map.Entry<String, Integer>> sorted = new ArrayList<>(tabEntry.getValue().entrySet());
                sorted.sort(Comparator.comparingInt(Map.Entry::getValue));
                List<String> keys = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : sorted) {
                    keys.add(entry.getKey());
                }
                TAB_REGISTRY_ORDER.put(tabEntry.getKey(), Collections.unmodifiableList(keys));
            }
        } catch (Exception e) {
            System.err.println("Failed to load creative tab sort order: " + e);
        }
        loaded = true;
    }

    private static Integer lookup(Map<String, Integer> map, ResourceLocation key) {
        if (map == null || key == null) {
            return null;
        }
        Integer idx = map.get(key.toString());
        if (idx != null) {
            return idx;
        }
        return map.get(key.getPath());
    }

    public static int getSortIndex(ResourceLocation key, String tabKey) {
        ensureLoaded();
        if (key == null) {
            return Integer.MAX_VALUE;
        }
        Integer idx = lookup(TAB_ORDERS.get(tabKey), key);
        if (idx != null) {
            return idx;
        }
        Integer fallback = GLOBAL_FALLBACK.get(key.toString());
        if (fallback == null) {
            fallback = GLOBAL_FALLBACK.get(key.getPath());
        }
        if (fallback != null) {
            return 1_000_000 + fallback;
        }
        return UNKNOWN_SORT_INDEX;
    }

    public static int getSortIndex(ItemStack stack, String tabKey) {
        ensureLoaded();
        if (stack.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        ResourceLocation key = stack.getItem().getRegistryName();
        if (key == null) {
            return Integer.MAX_VALUE;
        }
        return getSortIndex(key, tabKey);
    }

    public static List<String> getTabRegistryOrder(String tabKey) {
        ensureLoaded();
        List<String> order = TAB_REGISTRY_ORDER.get(tabKey);
        if (order == null) {
            return Collections.emptyList();
        }
        return order;
    }
}