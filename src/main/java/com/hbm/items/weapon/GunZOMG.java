package com.hbm.items.weapon;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GunZOMG extends Item {

    private final Random rand = new Random();

    public GunZOMG(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(MainRegistry.weaponTab);
        this.setMaxStackSize(1);
        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        player.setActiveHand(handIn);
        ensureTag(stack);

        if (!player.isSneaking()) {
            if (stack.getTagCompound().getBoolean("valid")) {
                if (!hasValidationItem(player)) {
                    stack.getTagCompound().setBoolean("valid", false);
                    if (!worldIn.isRemote) {
                        player.sendMessage(new TextComponentTranslation("[ZOMG] Validation lost!"));
                        player.sendMessage(new TextComponentTranslation("[ZOMG] Request new validation!"));
                    }
                }
            } else if (!worldIn.isRemote) {
                player.sendMessage(new TextComponentTranslation("[ZOMG] Gun not validated!"));
                player.sendMessage(new TextComponentTranslation("[ZOMG] Validate your gun with shift right-click."));
            }
        } else if (stack.getTagCompound().getBoolean("valid")) {
            if (!worldIn.isRemote) {
                player.sendMessage(new TextComponentTranslation("[ZOMG] Gun has already been validated."));
            }
        } else if (hasValidationItem(player)) {
            stack.getTagCompound().setBoolean("valid", true);
            if (!worldIn.isRemote) {
                player.sendMessage(new TextComponentTranslation("[ZOMG] Gun has been validated!"));
            }

            if (Library.superuser.contains(player.getUniqueID().toString())) {
                if (!worldIn.isRemote) {
                    player.sendMessage(new TextComponentTranslation("[ZOMG] Welcome, gigachad!"));
                }
                stack.getTagCompound().setBoolean("superuser", true);
            } else if (Library.hasInventoryItem(player.inventory, ModItems.polaroid)) {
                if (!worldIn.isRemote) {
                    player.sendMessage(new TextComponentTranslation("[ZOMG] Welcome, superuser!"));
                }
                stack.getTagCompound().setBoolean("superuser", true);
            } else {
                if (!worldIn.isRemote) {
                    player.sendMessage(new TextComponentTranslation("[ZOMG] Welcome, user!"));
                }
                stack.getTagCompound().setBoolean("superuser", false);
            }
        } else if (!worldIn.isRemote) {
            player.sendMessage(new TextComponentTranslation("[ZOMG] Validation failed!"));
            player.sendMessage(new TextComponentTranslation("[ZOMG] No external negative gravity well found!"));
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase ent, int count) {
        if (!(ent instanceof EntityPlayer)) {
            return;
        }

        EnumHand hand = ent.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
        if (hand == EnumHand.MAIN_HAND && ent.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.gun_zomg) {
            ent.getHeldItem(EnumHand.OFF_HAND).getItem().onUsingTick(ent.getHeldItem(EnumHand.OFF_HAND), ent, count);
        }

        EntityPlayer player = (EntityPlayer) ent;
        World world = player.world;
        ensureTag(stack);

        if (!player.isSneaking() && stack.getTagCompound().getBoolean("valid")) {
            if (player.capabilities.isCreativeMode || hasValidationItem(player)) {
                if (!stack.getTagCompound().getBoolean("superuser")) {
                    fireDarkPulseSpray(world, player, hand);
                } else {
                    fireNegativeEnergyBursts(world, player, hand);
                }
            } else if (!hasValidationItem(player)) {
                stack.getTagCompound().setBoolean("valid", false);
                if (!world.isRemote) {
                    player.sendMessage(new TextComponentTranslation("[ZOMG] Validation lost!"));
                    player.sendMessage(new TextComponentTranslation("[ZOMG] Request new validation!"));
                }
            }
        }
    }

    private void fireDarkPulseSpray(World world, EntityPlayer player, EnumHand hand) {
        EntityBullet[] bullets = new EntityBullet[6];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper", hand);
            bullets[i].setDamage(35 + this.rand.nextInt(10));
        }

        world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.osiprShoot, SoundCategory.PLAYERS, 1.0F, 0.6F + this.rand.nextFloat() * 0.4F);
        if (!world.isRemote) {
            for (EntityBullet bullet : bullets) {
                world.spawnEntity(bullet);
            }
        }
    }

    private void fireNegativeEnergyBursts(World world, EntityPlayer player, EnumHand hand) {
        EntityRainbow[] bursts = new EntityRainbow[5];
        for (int i = 0; i < bursts.length; i++) {
            bursts[i] = new EntityRainbow(world, player, 1.0F, hand);
            bursts[i].setDamage(10000 + this.rand.nextInt(90000));
        }

        world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.zomgShoot, SoundCategory.PLAYERS, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        if (!world.isRemote) {
            for (EntityRainbow burst : bursts) {
                world.spawnEntity(burst);
            }
        }
    }

    private static boolean hasValidationItem(EntityPlayer player) {
        return Library.hasInventoryItem(player.inventory, ModItems.nugget_euphemium) || Library.hasInventoryItem(player.inventory, ModItems.ingot_euphemium);
    }

    private static void ensureTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("valid", false);
            tag.setBoolean("superuser", false);
            stack.setTagCompound(tag);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getTagCompound() == null) {
            tooltip.add("Gun not validated.");
        } else if (stack.getTagCompound().getBoolean("valid")) {
            if (stack.getTagCompound().getBoolean("superuser")) {
                tooltip.add("Gun set to superuser mode.");
                tooltip.add("Firing mode: Negative energy bursts");
            } else {
                tooltip.add("Gun set to regular user mode.");
                tooltip.add("Firing mode: Dark pulse spray");
            }
        } else {
            tooltip.add("Gun not validated.");
        }

        tooltip.add("");
        tooltip.add("Ammo: \u00a75None (Requires Validation)");
        tooltip.add("Damage: 35 - 45");
        tooltip.add("Energy Damage: 10000 - 100000");
        tooltip.add("Energy projectiles destroy blocks.");
        tooltip.add("");
        tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6.0D, 0));
        }
        return multimap;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
