package com.hbm.creativetabs;

import com.hbm.items.machine.ItemBattery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreativeTabSortOrderTest {

	private static final List<String> TAB_KEYS = Arrays.asList(
			"blockTab",
			"consumableTab",
			"controlTab",
			"machineTab",
			"missileTab",
			"nukeTab",
			"partsTab",
			"resourceTab",
			"templateTab",
			"weaponTab"
	);

	private static ItemStack stack(String namespace, String path) {
		Item item = new Item();
		item.setRegistryName(namespace, path);
		return new ItemStack(item);
	}

	@Test
	void getSortIndexUsesRegistryPathNotFieldName() {
		ItemStack decoBlock = stack("hbm", "deco_sat_mapper");
		ItemStack satChip = stack("hbm", "sat_mapper");

		assertNotEquals(
				CreativeTabSortOrder.getSortIndex(decoBlock, "blockTab"),
				CreativeTabSortOrder.getSortIndex(satChip, "missileTab"));
		assertTrue(CreativeTabSortOrder.getSortIndex(decoBlock, "blockTab") < 500_000);
		assertTrue(CreativeTabSortOrder.getSortIndex(satChip, "missileTab") < 500_000);
	}

	@Test
	void unknownItemsSortLexicallyByRegistryPath() {
		NonNullList<ItemStack> stacks = NonNullList.create();
		stacks.add(stack("hbmspace", "zebra_probe"));
		stacks.add(stack("hbmspace", "alpha_probe"));
		stacks.add(stack("hbmspace", "mike_probe"));

		CreativeTabSortHelper.sortStacks(stacks, "weaponTab");

		assertEquals("hbmspace:alpha_probe", CreativeTabSortHelper.registrySortKey(stacks.get(0)));
		assertEquals("hbmspace:mike_probe", CreativeTabSortHelper.registrySortKey(stacks.get(1)));
		assertEquals("hbmspace:zebra_probe", CreativeTabSortHelper.registrySortKey(stacks.get(2)));
	}

	@Test
	void scrambledTabItemsRestoreOrderFileSequence() {
		for (String tabKey : TAB_KEYS) {
			List<String> keys = CreativeTabSortOrder.getTabRegistryOrder(tabKey);
			if (keys.isEmpty()) {
				continue;
			}

			NonNullList<ItemStack> stacks = NonNullList.create();
			for (int i = keys.size() - 1; i >= 0; i--) {
				String key = keys.get(i);
				if (key.contains(":")) {
					String[] parts = key.split(":", 2);
					stacks.add(stack(parts[0], parts[1]));
				} else {
					stacks.add(stack("hbm", key));
				}
			}

			CreativeTabSortHelper.sortStacks(stacks, tabKey);

			for (int i = 0; i < keys.size(); i++) {
				String expected = keys.get(i);
				ResourceLocation actual = stacks.get(i).getItem().getRegistryName();
				String actualKey = actual.getNamespace().equals("hbm")
						? actual.getPath()
						: actual.toString();
				assertEquals(expected, actualKey, "tab=" + tabKey + " index=" + i);
			}
		}
	}

	@Test
	void batteryFullEmptyVariantsStayAdjacentAfterSort() {
		ItemBattery battery = new ItemBattery(1000L, 100L, 100L, "battery_test_adjacent");
		NonNullList<ItemStack> stacks = NonNullList.create();
		stacks.add(stack("hbm", "gun_revolver"));
		stacks.add(ItemBattery.getEmptyBattery(battery));
		stacks.add(stack("hbm", "gun_deagle"));
		stacks.add(ItemBattery.getFullBattery(battery));

		CreativeTabSortHelper.sortStacks(stacks, "controlTab");

		int emptyIdx = indexOfRegistry(stacks, "battery_test_adjacent");
		int fullIdx = -1;
		for (int i = 0; i < stacks.size(); i++) {
			ItemStack candidate = stacks.get(i);
			if ("battery_test_adjacent".equals(candidate.getItem().getRegistryName().getPath())
					&& candidate.hasTagCompound()
					&& candidate.getTagCompound().getLong("charge") > 0L) {
				fullIdx = i;
			}
		}

		assertTrue(emptyIdx >= 0);
		assertTrue(fullIdx >= 0);
		assertEquals(1, Math.abs(emptyIdx - fullIdx));
	}

	@Test
	void dumpJavaSortExecutionEvidence() throws IOException {
		Path scratch = Paths.get(System.getenv().getOrDefault(
				"GOAL_SCRATCH",
				"C:/Temp/grok-goal-52eec85734e0/implementer"));
		Files.createDirectories(scratch);
		Path out = scratch.resolve("creative-tab-sort-execution-java.txt");

		StringBuilder sb = new StringBuilder();
		sb.append("source=CreativeTabSortOrderTest.dumpJavaSortExecutionEvidence\n");
		sb.append("classes=CreativeTabSortOrder,CreativeTabSortHelper\n\n");

		for (String tabKey : TAB_KEYS) {
			List<String> keys = CreativeTabSortOrder.getTabRegistryOrder(tabKey);
			NonNullList<ItemStack> stacks = NonNullList.create();
			for (int i = keys.size() - 1; i >= 0; i--) {
				String key = keys.get(i);
				if (key.contains(":")) {
					String[] parts = key.split(":", 2);
					stacks.add(stack(parts[0], parts[1]));
				} else {
					stacks.add(stack("hbm", key));
				}
			}

			CreativeTabSortHelper.sortStacks(stacks, tabKey);
			sb.append('@').append(tabKey).append(" (").append(stacks.size()).append(" items)\n");
			for (ItemStack stack : stacks) {
				int idx = CreativeTabSortOrder.getSortIndex(stack, tabKey);
				ResourceLocation reg = stack.getItem().getRegistryName();
				String regKey = reg.getNamespace().equals("hbm") ? reg.getPath() : reg.toString();
				sb.append(String.format("  %8d  %s%n", idx, regKey));
			}
			sb.append('\n');
		}

		Files.write(out, sb.toString().getBytes(StandardCharsets.UTF_8));
		assertTrue(Files.exists(out));
	}

	private static int indexOfRegistry(NonNullList<ItemStack> stacks, String path) {
		for (int i = 0; i < stacks.size(); i++) {
			ResourceLocation reg = stacks.get(i).getItem().getRegistryName();
			if (reg != null && path.equals(reg.getPath())) {
				return i;
			}
		}
		return -1;
	}
}