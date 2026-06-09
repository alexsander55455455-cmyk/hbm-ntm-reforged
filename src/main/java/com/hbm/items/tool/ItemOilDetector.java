package com.hbm.items.tool;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbmspace.blocks.generic.BlockOreFluid;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemOilDetector extends Item {

	public ItemOilDetector(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format(this.getTranslationKey() + ".desc1"));
		tooltip.add(I18n.format(this.getTranslationKey() + ".desc2"));
	}

	public static boolean isOil(World world, BlockPos pos) {
		return isRegistryBlock(world, pos, "ore_oil");
	}

	public static boolean isBedrockOil(World world, BlockPos pos) {
		return isRegistryBlock(world, pos, "ore_bedrock_oil");
	}

	private static boolean isRegistryBlock(World world, BlockPos pos, String path) {
		Block block = world.getBlockState(pos).getBlock();
		if (block == ModBlocks.ore_oil && "ore_oil".equals(path)) {
			return true;
		}
		if (block == ModBlocks.ore_bedrock_oil && "ore_bedrock_oil".equals(path)) {
			return true;
		}

		ResourceLocation id = block.getRegistryName();
		return id != null && "hbm".equals(id.getNamespace()) && path.equals(id.getPath());
	}

	private static String getReserveSuffix(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof BlockOreFluid fluid) {
			return fluid.getUnlocalizedReserveType();
		}
		return "";
	}

	private static BlockPos findOilColumn(World world, int x, int y, int z) {
		MutableBlockPos scan = new BlockPos.MutableBlockPos();
		for (int ly = y + 15; ly >= 0; ly--) {
			scan.setPos(x, ly, z);
			if (isOil(world, scan)) {
				return scan.toImmutable();
			}
		}
		return null;
	}

	private static BlockPos findBedrockOil(World world, int x, int z) {
		MutableBlockPos scan = new BlockPos.MutableBlockPos();
		for (int by = 0; by <= 4; by++) {
			scan.setPos(x, by, z);
			if (isBedrockOil(world, scan)) {
				return scan.toImmutable();
			}
		}
		return null;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		int x = MathHelper.floor(player.posX);
		int y = MathHelper.floor(player.posY);
		int z = MathHelper.floor(player.posZ);

		BlockPos directOilPos = findOilColumn(world, x, y, z);
		BlockPos directBedrockPos = findBedrockOil(world, x, z);

		boolean oil = false;
		boolean bedrockOil = false;
		BlockPos foundOilPos = null;
		BlockPos foundBedrockPos = null;

		int range = 25;
		int samples = 50;

		for (int i = 0; i < samples && !(oil && bedrockOil); i++) {
			int lx = (int) MathHelper.clamp(world.rand.nextGaussian() * range / 2F, -range, range);
			int lz = (int) MathHelper.clamp(world.rand.nextGaussian() * range / 2F, -range, range);

			if (!oil) {
				BlockPos hit = findOilColumn(world, x + lx, y, z + lz);
				if (hit != null) {
					oil = true;
					foundOilPos = hit;
				}
			}

			if (!bedrockOil) {
				BlockPos hit = findBedrockOil(world, x + lx, z + lz);
				if (hit != null) {
					bedrockOil = true;
					foundBedrockPos = hit;
				}
			}
		}

		if (!world.isRemote) {
			if (directBedrockPos != null) {
				player.sendMessage(new TextComponentTranslation(this.getTranslationKey() + ".bullseyeBedrock")
						.setStyle(new Style().setColor(TextFormatting.DARK_GREEN)));
			} else if (directOilPos != null) {
				String suffix = getReserveSuffix(world, directOilPos);
				player.sendMessage(new TextComponentTranslation(this.getTranslationKey() + ".bullseye" + suffix)
						.setStyle(new Style().setColor(TextFormatting.GREEN)));
			} else if (bedrockOil) {
				player.sendMessage(new TextComponentTranslation(this.getTranslationKey() + ".detectedBedrock")
						.setStyle(new Style().setColor(TextFormatting.GOLD)));
			} else if (oil) {
				String suffix = foundOilPos != null ? getReserveSuffix(world, foundOilPos) : "";
				player.sendMessage(new TextComponentTranslation(this.getTranslationKey() + ".detected" + suffix)
						.setStyle(new Style().setColor(TextFormatting.YELLOW)));
			} else {
				player.sendMessage(new TextComponentTranslation(this.getTranslationKey() + ".noOil")
						.setStyle(new Style().setColor(TextFormatting.RED)));
			}
		}

		world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F);

		player.swingArm(hand);

		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}