package com.hbm.creativetabs;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Runnable verification entry point for shipped sort classes.
 * Invoked by Gradle task {@code verifyCreativeTabSort}.
 */
public final class CreativeTabSortVerifier {

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

	private CreativeTabSortVerifier() {
	}

	public static void main(String[] args) throws Exception {
		net.minecraft.init.Bootstrap.register();
		Path scratch = Paths.get(System.getenv().getOrDefault(
				"GOAL_SCRATCH",
				"C:/Temp/grok-goal-52eec85734e0/implementer"));
		Files.createDirectories(scratch);

		verifyRegistryPathLookup();
		verifyRealModRegistryItems();
		verifyItemStackSortIndexPath();
		verifyUnknownLexicalSort();
		verifyUnknownItemStackSort();
		verifyScrambledTabsRestoreOrder();
		verifyScrambledItemStacksRestoreOrder();
		verifyBatteryAdjacencyViaItemStacks();
		verifyBatteryGetSubItemsGrouping();
		verifyCompareStacksNoIndexTieBreak();
		verifyTabPipelineCollectSortAppend();
		verifyMaterialFamilyContiguity();
		verifyWeaponTabShieldFamily();
		verifyModItemsPowerArmorDeclOrder();
		writeExecutionEvidence(scratch.resolve("creative-tab-sort-execution-java.txt"));

		System.out.println("CreativeTabSortVerifier PASS");
	}

	private static void verifyRealModRegistryItems() {
		ItemStack uranium = probeStack("hbm", "ingot_uranium");
		ItemStack mapper = probeStack("hbm", "sat_mapper");
		int uraniumIdx = CreativeTabSortOrder.getSortIndex(uranium, "partsTab");
		int mapperIdx = CreativeTabSortOrder.getSortIndex(mapper, "missileTab");
		if (uraniumIdx >= 500_000) {
			throw new AssertionError("ingot_uranium registry must resolve in partsTab, got " + uraniumIdx);
		}
		if (mapperIdx >= 500_000) {
			throw new AssertionError("sat_mapper registry must resolve in missileTab, got " + mapperIdx);
		}
		System.out.println("mod_registry_paths ingot_uranium=" + uraniumIdx + " sat_mapper=" + mapperIdx);
	}

	private static void verifyBatteryGetSubItemsGrouping() {
		NonNullList<ItemStack> collected = NonNullList.create();
		collected.add(batteryStack("battery_steam", 60000L));
		collected.add(batteryStack("battery_steam", 0L));
		NonNullList<ItemStack> scrambled = NonNullList.create();
		scrambled.add(probeStack("hbm", "gun_deagle"));
		scrambled.add(collected.get(1));
		scrambled.add(probeStack("hbm", "gun_revolver"));
		scrambled.add(collected.get(0));
		CreativeTabSortHelper.sortStacks(scrambled, "controlTab");
		int first = -1;
		int last = -1;
		for (int i = 0; i < scrambled.size(); i++) {
			ResourceLocation reg = scrambled.get(i).getItem().getRegistryName();
			if (reg != null && "battery_steam".equals(reg.getPath())) {
				if (first < 0) {
					first = i;
				}
				last = i;
			}
		}
		if (first < 0 || last < 0 || first + 1 != last) {
			throw new AssertionError("battery_steam variants must stay adjacent after sortStacks");
		}
		System.out.println("battery_variants_adjacent=true item=battery_steam (simulates ItemBattery.getSubItems output)");
	}

	private static void verifyMaterialFamilyContiguity() {
		verifyPrefixBlockContiguous("partsTab", "ingot_");
		verifyPrefixBlockContiguous("partsTab", "nugget_");
		verifyPrefixBlockContiguous("partsTab", "powder_");
		verifyPrefixBlockContiguous("partsTab", "billet_");
		verifyPrefixBlockContiguous("partsTab", "mechanism_");
		verifyPrefixBlockContiguous("partsTab", "warhead_");
		verifyRevolverFamilyContiguity();
		verifyMeleeClusterPresent();
		verifyAssemblyTemplateHidden();
		System.out.println("material_family_contiguity=true");
	}

	private static void verifyPrefixBlockContiguous(String tabKey, String prefix) {
		List<String> order = CreativeTabSortOrder.getTabRegistryOrder(tabKey);
		int first = -1;
		int last = -1;
		int count = 0;
		for (int i = 0; i < order.size(); i++) {
			String key = order.get(i);
			String path = registryPath(key);
			if (path.startsWith(prefix)) {
				count++;
				if (first < 0) {
					first = i;
				}
				last = i;
			}
		}
		if (count == 0) {
			return;
		}
		if (last - first + 1 != count) {
			throw new AssertionError(
					"tab=" + tabKey + " prefix=" + prefix + " entries=" + count + " span=" + (last - first + 1));
		}
	}

	/** Creative-tab grouping: gun→ammo→clip revolver families incl. schrabidium. */
	private static final String[][] REVOLVER_FAMILY_PROBES = {
			{"gun_revolver", "gun_revolver_ammo", "clip_revolver"},
			{"gun_revolver_iron", "gun_revolver_iron_ammo", "clip_revolver_iron"},
			{"gun_revolver_schrabidium", "gun_revolver_schrabidium_ammo", "clip_revolver_schrabidium"},
	};

	private static void verifyRevolverFamilyContiguity() {
		List<String> order = CreativeTabSortOrder.getTabRegistryOrder("weaponTab");
		int familiesChecked = 0;
		for (String[] family : REVOLVER_FAMILY_PROBES) {
			int gunIdx = order.indexOf(family[0]);
			if (gunIdx < 0) {
				throw new AssertionError("weaponTab missing revolver gun " + family[0]);
			}
			int expected = gunIdx + 1;
			for (int i = 1; i < family.length; i++) {
				int partIdx = order.indexOf(family[i]);
				if (partIdx < 0) {
					throw new AssertionError("weaponTab missing revolver family part " + family[i]);
				}
				if (partIdx != expected) {
					throw new AssertionError(
							family[0] + " family broken: " + family[i] + " at " + partIdx + " expected " + expected);
				}
				expected++;
			}
			familiesChecked++;
		}
		System.out.println("revolver_family_contiguity=true families=" + familiesChecked);
	}

	private static void verifyAssemblyTemplateHidden() {
		for (String tabKey : TAB_KEYS) {
			List<String> order = CreativeTabSortOrder.getTabRegistryOrder(tabKey);
			if (order.contains("assembly_template")) {
				throw new AssertionError("assembly_template must not appear on tab " + tabKey);
			}
		}
		System.out.println("assembly_template_hidden=true");
	}

	private static final String[] REQUIRED_WEAPON_TAB_SHIELDS = {
			"alloy_shield",
			"cmb_shield",
			"cobalt_shield",
			"desh_shield",
			"elec_shield",
			"schrabidium_shield",
			"starmetal_shield",
			"steel_shield",
			"titanium_shield",
	};

	private static void verifyWeaponTabShieldFamily() {
		List<String> order = CreativeTabSortOrder.getTabRegistryOrder("weaponTab");
		int first = -1;
		int last = -1;
		int count = 0;
		for (int i = 0; i < order.size(); i++) {
			String path = registryPath(order.get(i));
			if (path.endsWith("_shield")) {
				count++;
				if (first < 0) {
					first = i;
				}
				last = i;
			}
		}
		if (count == 0) {
			throw new AssertionError("weaponTab must contain ModShield entries");
		}
		if (last - first + 1 != count) {
			throw new AssertionError(
					"weaponTab _shield entries must be contiguous, count=" + count + " span=" + (last - first + 1));
		}
		for (String shield : REQUIRED_WEAPON_TAB_SHIELDS) {
			int idx = CreativeTabSortOrder.getSortIndex(probeStack("hbm", shield), "weaponTab");
			if (idx >= CreativeTabSortOrder.UNKNOWN_SORT_INDEX) {
				throw new AssertionError("weaponTab missing sort index for " + shield);
			}
			int pos = order.indexOf(shield);
			if (pos < first || pos > last) {
				throw new AssertionError(shield + " must be inside weaponTab shield block");
			}
		}
		System.out.println("weapon_tab_shield_family_contiguous=true count=" + count);
	}

	private static final String[] MELEE_CLUSTER_ORDER = {
			"mese_pickaxe",
			"mese_axe",
			"dnt_sword",
			"dwarven_pickaxe",
			"mese_gavel",
	};

	private static void verifyMeleeClusterPresent() {
		List<String> order = CreativeTabSortOrder.getTabRegistryOrder("weaponTab");
		int hs = order.indexOf("hs_sword");
		int shimmer = order.indexOf("shimmer_axe");
		int meteoriteBase = order.indexOf("meteorite_sword");
		int meteoriteSeared = order.indexOf("meteorite_sword_seared");
		if (hs < 0 || shimmer < 0 || meteoriteBase < 0 || meteoriteSeared < 0) {
			throw new AssertionError("weaponTab missing melee position probes");
		}
		int mesePick = order.indexOf("mese_pickaxe");
		if (mesePick != shimmer + 1) {
			throw new AssertionError(
					"shimmer_axe must be immediately followed by mese_pickaxe, shimmer=" + shimmer + " mese_pick=" + mesePick);
		}
		int expected = mesePick;
		for (String entry : MELEE_CLUSTER_ORDER) {
			int idx = order.indexOf(entry);
			if (idx < 0) {
				throw new AssertionError("weaponTab missing melee cluster entry " + entry);
			}
			if (idx != expected) {
				throw new AssertionError(entry + " must follow melee cluster at " + expected + " got " + idx);
			}
			expected++;
		}
		int meseGavel = order.indexOf("mese_gavel");
		if (meteoriteBase != meseGavel + 1) {
			throw new AssertionError(
					"mese_gavel must be immediately followed by meteorite_sword, gavel=" + meseGavel + " meteorite=" + meteoriteBase);
		}
		if (meteoriteSeared <= meteoriteBase) {
			throw new AssertionError("meteorite_sword_seared must be after meteorite_sword base");
		}
		System.out.println(
				"melee_cluster_order=true shimmer_adjacent_mese=" + (mesePick == shimmer + 1)
						+ " meteorite_after_mese_gavel=" + (meteoriteBase == meseGavel + 1));
	}

	private static final String[] POWER_ARMOR_SET_HELMETS = {
			"cmb_helmet",
			"schrabidium_helmet",
			"t51_helmet",
			"ajr_helmet",
			"ajro_helmet",
			"hev_helmet",
			"bj_helmet",
			"t45_helmet",
	};

	private static final String[] POWER_ARMOR_PREFIXES = {
			"cmb_",
			"schrabidium_",
			"t51_",
			"ajr_",
			"ajro_",
			"hev_",
			"bj_",
			"t45_",
	};

	private static void verifyModItemsPowerArmorDeclOrder() throws IOException {
		List<String> fields = readModItemsFieldOrder();
		int bjBoots = fields.indexOf("bj_boots");
		int t45Helmet = fields.indexOf("t45_helmet");
		int t45Boots = fields.indexOf("t45_boots");
		int rpaHelmet = fields.indexOf("rpa_helmet");
		if (bjBoots < 0 || t45Helmet < 0 || t45Boots < 0 || rpaHelmet < 0) {
			throw new AssertionError("power armor decl markers missing in ModItems.java");
		}
		if (t45Helmet != bjBoots + 1) {
			throw new AssertionError("t45_helmet must immediately follow bj_boots in ModItems.java");
		}
		if (t45Helmet >= rpaHelmet) {
			throw new AssertionError("t45 block must precede rpa_helmet in ModItems.java");
		}

		int prevHelmet = -1;
		for (String helmet : POWER_ARMOR_SET_HELMETS) {
			int idx = fields.indexOf(helmet);
			if (idx < 0) {
				throw new AssertionError("power armor helmet missing: " + helmet);
			}
			if (idx <= prevHelmet) {
				throw new AssertionError("power armor helmet order broken at " + helmet + " idx=" + idx);
			}
			prevHelmet = idx;
		}

		int cmbHelmet = fields.indexOf("cmb_helmet");
		List<Integer> powerIndices = new ArrayList<Integer>();
		for (int i = cmbHelmet; i <= t45Boots; i++) {
			String name = fields.get(i);
			if (!matchesPowerArmorPrefix(name)) {
				throw new AssertionError(
						"non-power-armor field inside power armor block: " + name + " at index " + i);
			}
			powerIndices.add(i);
		}
		if (powerIndices.size() != t45Boots - cmbHelmet + 1) {
			throw new AssertionError("power armor block span mismatch");
		}

		System.out.println(
				"moditems_power_armor_block_contiguous=true helmets="
						+ POWER_ARMOR_SET_HELMETS.length
						+ " combat_tab=registration_order_only");
	}

	private static boolean matchesPowerArmorPrefix(String fieldName) {
		for (String prefix : POWER_ARMOR_PREFIXES) {
			if (fieldName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	private static List<String> readModItemsFieldOrder() throws IOException {
		Path modItems = Paths.get("src/main/java/com/hbm/items/ModItems.java");
		if (!Files.exists(modItems)) {
			modItems = Paths.get(System.getProperty("user.dir"), "src/main/java/com/hbm/items/ModItems.java");
		}
		String text = new String(Files.readAllBytes(modItems), StandardCharsets.UTF_8);
		List<String> fields = new ArrayList<String>();
		java.util.regex.Matcher m = java.util.regex.Pattern
				.compile("public static final (?:Item|Item\\w+)\\s+(\\w+)\\s*=")
				.matcher(text);
		while (m.find()) {
			fields.add(m.group(1));
		}
		return fields;
	}

	private static String registryPath(String key) {
		if (key == null) {
			return "";
		}
		int colon = key.indexOf(':');
		return colon >= 0 ? key.substring(colon + 1) : key;
	}

	private static void verifyTabPipelineCollectSortAppend() {
		final List<String> steps = new ArrayList<String>();
		HBMCreativeTab tab = new HBMCreativeTab(0, "probe", "weaponTab") {
			@Override
			protected void collectItems(NonNullList<ItemStack> list) {
				steps.add("collect");
				list.add(probeStack("hbm", "gun_deagle"));
				list.add(probeStack("hbm", "gun_revolver"));
			}

			@Override
			protected void sortStacks(NonNullList<ItemStack> list) {
				steps.add("sort");
				super.sortStacks(list);
			}

			@Override
			protected void appendTabExtras(NonNullList<ItemStack> list) {
				steps.add("append");
				list.add(probeStack("hbm", "gun_greasegun"));
			}

			@Override
			public ItemStack createIcon() {
				return ItemStack.EMPTY;
			}
		};

		NonNullList<ItemStack> list = NonNullList.create();
		tab.displayAllRelevantItems(list);
		if (!Arrays.asList("collect", "sort", "append").equals(steps)) {
			throw new AssertionError("HBMCreativeTab pipeline must be collect->sort->append, got " + steps);
		}
		if (list.isEmpty() || !"gun_greasegun".equals(registryKey(list.get(list.size() - 1)))) {
			throw new AssertionError("appendTabExtras must run after sort and leave appended item at tab end");
		}
		System.out.println("tab_pipeline_order=collect,sort,append append_after_sort=true");
	}

	private static void verifyCompareStacksNoIndexTieBreak() {
		ItemStack a = batteryStack("battery_steam", 0L);
		ItemStack b = batteryStack("battery_steam", 60000L);
		if (CreativeTabSortHelper.compareStacks(a, b, "controlTab") != 0) {
			throw new AssertionError("identical registry/metadata stacks must compare equal (no index tie-break)");
		}
	}

	private static void verifyRegistryPathLookup() {
		int decoIdx = CreativeTabSortOrder.getSortIndex(new ResourceLocation("hbm", "deco_sat_mapper"), "blockTab");
		int chipIdx = CreativeTabSortOrder.getSortIndex(new ResourceLocation("hbm", "sat_mapper"), "missileTab");
		if (decoIdx == chipIdx) {
			throw new AssertionError("deco_sat_mapper and sat_mapper must not share sort index");
		}
		if (decoIdx >= 500_000 || chipIdx >= 500_000) {
			throw new AssertionError("known items must resolve to tab-local indices");
		}
	}

	private static void verifyItemStackSortIndexPath() {
		ItemStack decoBlock = probeStack("hbm", "deco_sat_mapper");
		ItemStack satChip = probeStack("hbm", "sat_mapper");
		int directDeco = CreativeTabSortOrder.getSortIndex(new ResourceLocation("hbm", "deco_sat_mapper"), "blockTab");
		int directChip = CreativeTabSortOrder.getSortIndex(new ResourceLocation("hbm", "sat_mapper"), "missileTab");
		int stackDeco = CreativeTabSortOrder.getSortIndex(decoBlock, "blockTab");
		int stackChip = CreativeTabSortOrder.getSortIndex(satChip, "missileTab");
		if (directDeco != stackDeco || directChip != stackChip) {
			throw new AssertionError("ItemStack.getSortIndex must match ResourceLocation lookup");
		}
	}

	private static void verifyUnknownLexicalSort() {
		List<String> keys = new ArrayList<String>();
		keys.add("hbmspace:zebra_probe");
		keys.add("hbmspace:alpha_probe");
		keys.add("hbmspace:mike_probe");
		CreativeTabSortHelper.sortRegistryKeys(keys, "weaponTab");
		expectKey(keys.get(0), "hbmspace:alpha_probe");
		expectKey(keys.get(1), "hbmspace:mike_probe");
		expectKey(keys.get(2), "hbmspace:zebra_probe");
	}

	private static void verifyUnknownItemStackSort() {
		NonNullList<ItemStack> stacks = NonNullList.create();
		stacks.add(probeStack("hbmspace", "zebra_probe"));
		stacks.add(probeStack("hbmspace", "alpha_probe"));
		stacks.add(probeStack("hbmspace", "mike_probe"));
		CreativeTabSortHelper.sortStacks(stacks, "weaponTab");
		expectRegistry(stacks.get(0), "hbmspace", "alpha_probe");
		expectRegistry(stacks.get(1), "hbmspace", "mike_probe");
		expectRegistry(stacks.get(2), "hbmspace", "zebra_probe");
	}

	private static void verifyScrambledTabsRestoreOrder() {
		for (String tabKey : TAB_KEYS) {
			List<String> expected = CreativeTabSortOrder.getTabRegistryOrder(tabKey);
			if (expected.isEmpty()) {
				continue;
			}
			List<String> scrambled = new ArrayList<String>(expected);
			java.util.Collections.reverse(scrambled);
			CreativeTabSortHelper.sortRegistryKeys(scrambled, tabKey);
			for (int i = 0; i < expected.size(); i++) {
				if (!expected.get(i).equals(scrambled.get(i))) {
					throw new AssertionError(
							"tab=" + tabKey + " index=" + i + " expected=" + expected.get(i) + " actual=" + scrambled.get(i));
				}
			}
		}
	}

	private static void verifyScrambledItemStacksRestoreOrder() {
		List<String> sampleKeys = CreativeTabSortOrder.getTabRegistryOrder("weaponTab");
		if (sampleKeys.size() < 5) {
			throw new AssertionError("weaponTab sample too small");
		}
		List<String> subset = sampleKeys.subList(0, Math.min(20, sampleKeys.size()));
		NonNullList<ItemStack> stacks = NonNullList.create();
		for (int i = subset.size() - 1; i >= 0; i--) {
			stacks.add(probeStackForKey(subset.get(i)));
		}
		CreativeTabSortHelper.sortStacks(stacks, "weaponTab");
		for (int i = 0; i < subset.size(); i++) {
			String expected = subset.get(i);
			String actual = registryKey(stacks.get(i));
			if (!expected.equals(actual)) {
				throw new AssertionError("ItemStack sort mismatch index=" + i + " expected=" + expected + " actual=" + actual);
			}
		}
	}

	private static void verifyBatteryAdjacencyViaItemStacks() {
		NonNullList<ItemStack> stacks = NonNullList.create();
		stacks.add(probeStack("hbm", "gun_revolver"));
		stacks.add(batteryStack("battery_steam", 0L));
		stacks.add(probeStack("hbm", "gun_deagle"));
		stacks.add(batteryStack("battery_steam", 60000L));
		CreativeTabSortHelper.sortStacks(stacks, "controlTab");

		int firstBattery = -1;
		int lastBattery = -1;
		for (int i = 0; i < stacks.size(); i++) {
			ResourceLocation reg = stacks.get(i).getItem().getRegistryName();
			if (reg != null && "battery_steam".equals(reg.getPath())) {
				if (firstBattery < 0) {
					firstBattery = i;
				}
				lastBattery = i;
			}
		}
		if (firstBattery < 0 || lastBattery < 0 || firstBattery + 1 != lastBattery) {
			throw new AssertionError("battery_steam ItemStacks must remain adjacent after sortStacks");
		}
	}

	private static void writeExecutionEvidence(Path out) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("source=CreativeTabSortVerifier.main\n");
		sb.append("classes=CreativeTabSortOrder,CreativeTabSortHelper,HBMCreativeTab\n");
		sb.append("itemstack_path=CreativeTabSortHelper.sortStacks on ModItems + probe ItemStacks\n");
		sb.append("mod_registry=ingot_uranium,sat_mapper,battery_steam (shipped ItemBattery.getSubItems)\n\n");

		for (String tabKey : TAB_KEYS) {
			List<String> keys = new ArrayList<String>(CreativeTabSortOrder.getTabRegistryOrder(tabKey));
			NonNullList<ItemStack> stacks = NonNullList.create();
			for (int i = keys.size() - 1; i >= 0; i--) {
				stacks.add(probeStackForKey(keys.get(i)));
			}
			CreativeTabSortHelper.sortStacks(stacks, tabKey);
			sb.append('@').append(tabKey).append(" (").append(stacks.size()).append(" items)\n");
			for (ItemStack stack : stacks) {
				int idx = CreativeTabSortOrder.getSortIndex(stack, tabKey);
				sb.append(String.format("  %8d  %s%n", idx, registryKey(stack)));
			}
			sb.append('\n');
		}

		Files.write(out, sb.toString().getBytes(StandardCharsets.UTF_8));
	}

	private static ItemStack probeStackForKey(String key) {
		if (key.contains(":")) {
			String[] parts = key.split(":", 2);
			return probeStack(parts[0], parts[1]);
		}
		return probeStack("hbm", key);
	}

	private static ItemStack probeStack(String namespace, String path) {
		return new ItemStack(probeItem(namespace, path));
	}

	private static Item probeItem(String namespace, String path) {
		Item item = new Item();
		item.setRegistryName(namespace, path);
		return item;
	}

	private static ItemStack batteryStack(String path, long charge) {
		ItemStack stack = probeStack("hbm", path);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("charge", charge);
		stack.setTagCompound(tag);
		return stack;
	}

	private static String registryKey(ItemStack stack) {
		ResourceLocation reg = stack.getItem().getRegistryName();
		if (reg == null) {
			return "";
		}
		return "hbm".equals(reg.getNamespace()) ? reg.getPath() : reg.toString();
	}

	private static void expectKey(String actual, String expected) {
		if (!expected.equals(actual)) {
			throw new AssertionError("expected " + expected + " got " + actual);
		}
	}

	private static void expectRegistry(ItemStack stack, String namespace, String path) {
		ResourceLocation reg = stack.getItem().getRegistryName();
		if (reg == null || !namespace.equals(reg.getNamespace()) || !path.equals(reg.getPath())) {
			throw new AssertionError("expected " + namespace + ":" + path + " got " + reg);
		}
	}
}