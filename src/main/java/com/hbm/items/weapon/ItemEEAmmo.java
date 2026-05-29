package com.hbm.items.weapon;

import com.hbm.config.BombConfig;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemEEAmmo extends Item {

    private static final Map<String, Note[]> TRAITS = buildTraits();

    public ItemEEAmmo(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(MainRegistry.controlTab);
        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        String name = this.getRegistryName() != null ? this.getRegistryName().getPath() : "";

        if ("ammo_nuke_low".equals(name)) {
            nuke(tooltip, 20);
        } else if ("ammo_nuke".equals(name)) {
            nuke(tooltip, 35);
        } else if ("ammo_nuke_high".equals(name)) {
            nuke(tooltip, 50);
        } else if ("ammo_nuke_tots".equals(name)) {
            nuke(tooltip, 10);
        }

        Note[] notes = TRAITS.get(name);
        if (notes != null) {
            for (Note note : notes) {
                note.add(tooltip);
            }
        }
    }

    private static Map<String, Note[]> buildTraits() {
        Map<String, Note[]> map = new HashMap<>();

        traits(map, "ammo_75bolt", neu("ammo.trait.75b1"), neu("ammo.trait.75b2"));
        traits(map, "ammo_75bolt_incendiary", neu("ammo.trait.75b3"), neu("ammo.trait.75b4"));
        traits(map, "ammo_75bolt_he", neu("ammo.trait.75b5"), neu("ammo.trait.75b6"));

        traits(map, "ammo_nuke_tots", pro("ammo.trait.incbomb"), special("ammo.trait.fun"), con("ammo.trait.daccuracy"), con("ammo.trait.dblast"), con("ammo.trait.noprotomirv"));
        traits(map, "ammo_nuke_safe", con("ammo.trait.dblast"), con("ammo.trait.noblock"));
        traits(map, "ammo_nuke_pumpkin", con("ammo.trait.nonuke"));
        traits(map, "ammo_mirv_low", con("ammo.trait.dblast"));
        traits(map, "ammo_mirv_high", pro("ammo.trait.blast"), pro("ammo.trait.fallout"));
        traits(map, "ammo_mirv_safe", con("ammo.trait.dblast"), con("ammo.trait.noblock"));
        traits(map, "ammo_mirv_special", pro("ammo.trait.mirv1"), pro("ammo.trait.mirv2"), pro("ammo.trait.mirv3"), pro("ammo.trait.mirv4"), white("ammo.trait.mirv5"));

        traits(map, "ammo_fuel_napalm", pro("ammo.trait.damage"), pro("ammo.trait.range"), con("ammo.trait.highwear"));
        traits(map, "ammo_fuel_phosphorus", pro("ammo.trait.phosphor"), pro("ammo.trait.damage"), pro("ammo.trait.range"), pro("ammo.trait.accuracy"), special("ammo.trait.warcrime"), con("ammo.trait.single"), con("ammo.trait.highwear"));
        traits(map, "ammo_fuel_gas", pro("ammo.trait.nograv"), pro("ammo.trait.poison"), con("ammo.trait.nodamage"), con("ammo.trait.noinc"));
        traits(map, "ammo_fuel_vaporizer", pro("ammo.trait.phosphorburn"), pro("ammo.trait.vapor1"), pro("ammo.trait.damage"), special("ammo.trait.vapor2"), con("ammo.trait.daccuracy"), con("ammo.trait.vapor3"), con("ammo.trait.highwear"), con("ammo.trait.vapor4"));

        traits(map, "ammo_44_phosphorus", pro("ammo.trait.phosphorburn"), special("ammo.trait.warcrime"), con("ammo.trait.wear"), con("ammo.trait.nopen"));
        traits(map, "ammo_50bmg", neu("ammo.trait.bmg1"), neu("ammo.trait.bmg2"), neu("ammo.trait.bmg3"));
        traits(map, "ammo_50bmg_phosphorus", pro("ammo.trait.phosphorburn"), special("ammo.trait.warcrime"), con("ammo.trait.wear"), con("ammo.trait.nopen"));
        traits(map, "ammo_50bmg_ap", pro("ammo.trait.damage"), con("ammo.trait.wear"));
        traits(map, "ammo_rocket_phosphorus", pro("ammo.trait.phosphor"), special("ammo.trait.warcrime"), con("ammo.trait.wear"));
        traits(map, "ammo_grenade_phosphorus", pro("ammo.trait.phosphor"), special("ammo.trait.warcrime"), con("ammo.trait.wear"));
        traits(map, "ammo_grenade_kampf", pro("ammo.trait.rocketprop"), pro("ammo.trait.blast"), pro("ammo.trait.accuracy"), con("ammo.trait.wear"));

        traits(map, "ammo_12gauge_incendiary", pro("ammo.trait.inc"), con("ammo.trait.wear"));
        traits(map, "ammo_12gauge_shrapnel", pro("ammo.trait.damage"), special("ammo.trait.bounce"), con("ammo.trait.wear"));
        traits(map, "ammo_12gauge_du", pro("ammo.trait.damage"), pro("ammo.trait.pen"), special("ammo.trait.heavymetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_12gauge_marauder", pro("ammo.trait.12gm1"), special("ammo.trait.12gm2"));
        traits(map, "ammo_12gauge_sleek", special("ammo.trait.gaugesleek"));
        traits(map, "ammo_20gauge_flechette", pro("ammo.trait.damage"), special("ammo.trait.dbounce"), con("ammo.trait.wear"));
        traits(map, "ammo_20gauge_slug", pro("ammo.trait.highaccuracy"), pro("ammo.trait.damage"), pro("ammo.trait.dwear"), con("ammo.trait.single"));
        traits(map, "ammo_20gauge_incendiary", pro("ammo.trait.inc"), con("ammo.trait.wear"));
        traits(map, "ammo_20gauge_shrapnel", pro("ammo.trait.damage"), special("ammo.trait.bounce"), con("ammo.trait.wear"));
        traits(map, "ammo_20gauge_explosive", pro("ammo.trait.explosive"), pro("ammo.trait.damage"), con("ammo.trait.highwear"));
        traits(map, "ammo_20gauge_caustic", pro("ammo.trait.toxic"), pro("ammo.trait.1"), special("ammo.trait.nobounce"), con("ammo.trait.highwear"));
        traits(map, "ammo_20gauge_shock", pro("ammo.trait.damage"), pro("ammo.trait.2"), pro("ammo.trait.emp"), special("ammo.trait.nobounce"), con("ammo.trait.highwear"));
        traits(map, "ammo_20gauge_wither", pro("ammo.trait.damage"), pro("ammo.trait.wither"));
        traits(map, "ammo_20gauge_sleek", special("ammo.trait.gaugesleek"));

        traits(map, "ammo_357_desh", pro("ammo.trait.357d1"), pro("ammo.trait.357d2"));
        traits(map, "ammo_44_ap", pro("ammo.trait.damage"), con("ammo.trait.wear"));
        traits(map, "ammo_44_du", pro("ammo.trait.highdamage"), special("ammo.trait.heavymetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_44_pip", pro("ammo.trait.3"), con("ammo.trait.ddamage"));
        traits(map, "ammo_44_bj", pro("ammo.trait.4"), con("ammo.trait.ddamage"));
        traits(map, "ammo_44_silver", pro("ammo.trait.5"), con("ammo.trait.ddamage"));
        traits(map, "ammo_44_rocket", pro("ammo.trait.rocket"), special("ammo.trait.uhhh"));
        traits(map, "ammo_44_star", pro("ammo.trait.highdamage"), special("ammo.trait.starmetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_5mm_explosive", pro("ammo.trait.explosive"), pro("ammo.trait.damage"), con("ammo.trait.highwear"));
        traits(map, "ammo_5mm_du", pro("ammo.trait.highdamage"), special("ammo.trait.heavymetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_5mm_star", pro("ammo.trait.highdamage"), special("ammo.trait.starmetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_9mm_ap", pro("ammo.trait.damage"), con("ammo.trait.wear"));
        traits(map, "ammo_9mm_du", pro("ammo.trait.highdamage"), special("ammo.trait.heavymetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_9mm_rocket", pro("ammo.trait.rocket"), special("ammo.trait.uhhh"));
        traits(map, "ammo_22lr_ap", pro("ammo.trait.damage"), con("ammo.trait.wear"));

        traits(map, "ammo_50bmg_incendiary", pro("ammo.trait.inc"), con("ammo.trait.wear"));
        traits(map, "ammo_50bmg_explosive", pro("ammo.trait.explosive"), pro("ammo.trait.damage"), con("ammo.trait.highwear"));
        traits(map, "ammo_50bmg_du", pro("ammo.trait.highdamage"), special("ammo.trait.heavymetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_50bmg_star", pro("ammo.trait.highdamage"), special("ammo.trait.starmetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_50bmg_sleek", special("ammo.trait.meteorite"));
        traits(map, "ammo_50ae_ap", pro("ammo.trait.damage"), con("ammo.trait.wear"));
        traits(map, "ammo_50ae_du", pro("ammo.trait.highdamage"), special("ammo.trait.heavymetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_50ae_star", pro("ammo.trait.highdamage"), special("ammo.trait.starmetal"), con("ammo.trait.highwear"));

        traits(map, "ammo_rocket_he", pro("ammo.trait.blast"), con("ammo.trait.wear"));
        traits(map, "ammo_rocket_incendiary", pro("ammo.trait.incexplosive"), con("ammo.trait.wear"));
        traits(map, "ammo_rocket_shrapnel", pro("ammo.trait.shrapnel"));
        traits(map, "ammo_rocket_emp", pro("ammo.trait.emp"), con("ammo.trait.dblast"));
        traits(map, "ammo_rocket_glare", pro("ammo.trait.9"), pro("ammo.trait.incexplosive"), con("ammo.trait.wear"));
        traits(map, "ammo_rocket_toxic", pro("ammo.trait.8"), con("ammo.trait.noexplosive"), con("ammo.trait.dspeed"));
        traits(map, "ammo_rocket_sleek", pro("ammo.trait.10"), pro("ammo.trait.nograv"), special("ammo.trait.jolt"));
        traits(map, "ammo_rocket_nuclear", pro("ammo.trait.nuclear"), con("ammo.trait.hhighwear"), con("ammo.trait.dspeed"));
        traits(map, "ammo_rocket_rpc", pro("ammo.trait.6"), pro("ammo.trait.pen"), pro("ammo.trait.nograv"), con("ammo.trait.wear"), con("ammo.trait.noexplosive"), special("ammo.trait.uhhh"));

        traits(map, "ammo_grenade_he", pro("ammo.trait.blast"), con("ammo.trait.wear"));
        traits(map, "ammo_grenade_incendiary", pro("ammo.trait.incexplosive"), con("ammo.trait.wear"));
        traits(map, "ammo_grenade_toxic", pro("ammo.trait.8"), con("ammo.trait.noexplosive"));
        traits(map, "ammo_grenade_concussion", pro("ammo.trait.blast"), con("ammo.trait.noblock"));
        traits(map, "ammo_grenade_finned", pro("ammo.trait.11"), con("ammo.trait.dblast"));
        traits(map, "ammo_grenade_sleek", pro("ammo.trait.blast"), special("ammo.trait.jolt"));
        traits(map, "ammo_grenade_nuclear", pro("ammo.trait.nuclear"), pro("ammo.trait.range"), con("ammo.trait.highwear"));

        traits(map, "ammo_folly", pro("ammo.trait.folly1"));
        traits(map, "ammo_folly_nuclear", pro("ammo.trait.folly2"));
        traits(map, "ammo_folly_du", pro("ammo.trait.folly3"));

        traits(map, "ammo_4gauge_slug", pro("ammo.trait.highaccuracy"), pro("ammo.trait.damage"), pro("ammo.trait.dwear"), con("ammo.trait.single"));
        traits(map, "ammo_4gauge_explosive", pro("ammo.trait.explosive"), pro("ammo.trait.damage"), special("ammo.trait.12"), con("ammo.trait.highwear"), con("ammo.trait.single"));
        traits(map, "ammo_4gauge_semtex", pro("ammo.trait.explosive"), pro("ammo.trait.7"), con("ammo.trait.13"), con("ammo.trait.highwear"), con("ammo.trait.single"));
        traits(map, "ammo_4gauge_balefire", pro("ammo.trait.explosive"), pro("ammo.trait.balefire"), pro("ammo.trait.damage"), con("ammo.trait.highwear"), con("ammo.trait.single"));
        traits(map, "ammo_4gauge_kampf", pro("ammo.trait.explosive"), pro("ammo.trait.rocketprop"), pro("ammo.trait.accuracy"), pro("ammo.trait.damage"), con("ammo.trait.wear"), con("ammo.trait.single"));
        traits(map, "ammo_4gauge_sleek", special("ammo.trait.gaugesleek"));
        traits(map, "ammo_4gauge_flechette", pro("ammo.trait.damage"), special("ammo.trait.dbounce"), con("ammo.trait.wear"));
        traits(map, "ammo_4gauge_flechette_phosphorus", pro("ammo.trait.damage"), pro("ammo.trait.phosphorburn"), special("ammo.trait.highwarcrime"), special("ammo.trait.dbounce"), con("ammo.trait.wear"));

        traits(map, "ammo_556_phosphorus", pro("ammo.trait.phosphorburn"), special("ammo.trait.warcrime"), con("ammo.trait.wear"), con("ammo.trait.nopen"));
        traits(map, "ammo_556_ap", pro("ammo.trait.damage"), con("ammo.trait.wear"));
        traits(map, "ammo_556_du", pro("ammo.trait.highdamage"), special("ammo.trait.heavymetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_556_star", pro("ammo.trait.highdamage"), special("ammo.trait.starmetal"), con("ammo.trait.highwear"));
        traits(map, "ammo_556_sleek", special("ammo.trait.meteorite"));
        traits(map, "ammo_556_flechette", pro("ammo.trait.damage"), special("ammo.trait.dbounce"), con("ammo.trait.wear"), con("ammo.trait.nopen"));
        traits(map, "ammo_556_flechette_incendiary", pro("ammo.trait.damage"), pro("ammo.trait.inc"), special("ammo.trait.dbounce"), con("ammo.trait.wear"), con("ammo.trait.nopen"));
        traits(map, "ammo_556_flechette_phosphorus", pro("ammo.trait.damage"), pro("ammo.trait.phosphorburn"), special("ammo.trait.highwarcrime"), special("ammo.trait.dbounce"), con("ammo.trait.wear"), con("ammo.trait.nopen"));
        traits(map, "ammo_556_flechette_du", pro("ammo.trait.highdamage"), pro("ammo.trait.pen"), special("ammo.trait.heavymetal"), special("ammo.trait.dbounce"), con("ammo.trait.highwear"));
        traits(map, "ammo_556_flechette_sleek", special("ammo.trait.meteorite"));
        traits(map, "ammo_556_tracer", special("ammo.trait.tracer"));
        traits(map, "ammo_556_k", special("ammo.trait.blank"));

        Note[] chlorophyte = new Note[] { pro("ammo.trait.damage"), pro("ammo.trait.dwear"), dark("ammo.trait.chlorophyte"), special("ammo.trait.homing"), con("ammo.trait.nopen") };
        traits(map, "ammo_44_chlorophyte", chlorophyte);
        traits(map, "ammo_5mm_chlorophyte", chlorophyte);
        traits(map, "ammo_9mm_chlorophyte", chlorophyte);
        traits(map, "ammo_22lr_chlorophyte", chlorophyte);
        traits(map, "ammo_50bmg_chlorophyte", chlorophyte);
        traits(map, "ammo_50ae_chlorophyte", chlorophyte);
        traits(map, "ammo_556_chlorophyte", chlorophyte);
        traits(map, "ammo_556_flechette_chlorophyte", chlorophyte);

        return Collections.unmodifiableMap(map);
    }

    private static void traits(Map<String, Note[]> map, String item, Note... notes) {
        map.put(item, notes);
    }

    private static Note neu(String key) {
        return new Note(TextFormatting.YELLOW, "", key);
    }

    private static Note pro(String key) {
        return new Note(TextFormatting.BLUE, "+", key);
    }

    private static Note con(String key) {
        return new Note(TextFormatting.RED, "-", key);
    }

    private static Note special(String key) {
        return new Note(TextFormatting.YELLOW, "*", key);
    }

    private static Note dark(String key) {
        return new Note(TextFormatting.DARK_GREEN, "*", key);
    }

    private static Note white(String key) {
        return new Note(TextFormatting.WHITE, "*", key);
    }

    private static void nuke(List<String> tooltip, int radius) {
        tooltip.add(TextFormatting.YELLOW + "" + I18nUtil.resolveKey("desc.radius", radius));

        if (!BombConfig.disableNuclear) {
            tooltip.add(TextFormatting.DARK_GREEN + "[" + I18nUtil.resolveKey("trait.fallout") + "]");
            tooltip.add("" + TextFormatting.GREEN + I18nUtil.resolveKey("desc.radius", radius * (1 + BombConfig.falloutRange / 100)));
        }
    }

    private static class Note {
        private final TextFormatting color;
        private final String prefix;
        private final String key;

        private Note(TextFormatting color, String prefix, String key) {
            this.color = color;
            this.prefix = prefix;
            this.key = key;
        }

        private void add(List<String> tooltip) {
            tooltip.add(this.color + this.prefix + I18nUtil.resolveKey(this.key));
        }
    }
}
