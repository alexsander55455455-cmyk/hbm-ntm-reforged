package com.hbm.items.weapon;

import com.hbm.config.CompatibilityConfig;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.main.ResourceManager;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.GunAnimationPacket;
import com.hbm.packet.toclient.GunFXPacket;
import com.hbm.packet.toclient.GunFXPacket.FXType;
import com.hbm.particle.tau.ParticleTauBeam;
import com.hbm.particle.tau.ParticleTauHit;
import com.hbm.particle.tau.ParticleTauLightning;
import com.hbm.particle.tau.ParticleTauMuzzleLightning;
import com.hbm.particle.tau.ParticleTauParticleFirstPerson;
import com.hbm.particle.tau.ParticleTauRay;
import com.hbm.render.NTMRenderHelper;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.BobMathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ItemGunGauss extends ItemGunBase {

	private static Field hurtResistantTime;

	private AudioWrapper chargeLoop;
	public static int firstPersonFireCounter = -1;

	public ItemGunGauss(GunConfiguration config, GunConfiguration alt, String s) {
		super(config, alt, s);
	}

	@Override
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(getHasShot(stack)) {
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.sparkShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setHasShot(stack, false);
		}

		if(!main && getStored(stack) > 0) {
			doTauShot(world, player, player.getPositionEyes(MainRegistry.proxy.partialTicks()), player.getLook(MainRegistry.proxy.partialTicks()), Math.min(getStored(stack), 13) * 3.5F);
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.ALT_CYCLE.ordinal(), hand), (EntityPlayerMP) player);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauShoot, SoundCategory.PLAYERS, 1.0F, 0.75F);
			setItemWear(stack, getItemWear(stack) + getCharge(stack) * 2);
			setCharge(stack, 0);
		}
	}

	public void doTauShot(World world, @Nullable Entity shooter, Vec3d prevPos, Vec3d direction, float damage) {
		for(int i = 0; i < 4; i++) {
			RayTraceResult result = Library.rayTraceIncludeEntities(world, prevPos, direction.scale(40).add(prevPos), shooter);
			if(result == null || result.typeOfHit == Type.MISS) {
				break;
			}

			if(result.typeOfHit == Type.ENTITY && CompatibilityConfig.isWarDim(world)) {
				resetHurtResistance(result.entityHit);
				result.entityHit.attackEntityFrom(ModDamageSource.causeTauDamage(shooter, shooter), damage);
				break;
			}

			if(result.sideHit == null) {
				break;
			}

			Vec3d normal = new Vec3d(result.sideHit.getDirectionVec());
			if(Math.acos(normal.dotProduct(direction.scale(-1))) > Math.toRadians(20)) {
				direction = reflect(direction, result.sideHit.getAxis());
				prevPos = result.hitVec.add(direction.scale(0.01));
				continue;
			}

			scatterTauDamage(world, shooter, result.hitVec, direction, damage);
			break;
		}
	}

	private void scatterTauDamage(World world, @Nullable Entity shooter, Vec3d hit, Vec3d direction, float damage) {
		for(int j = 0; j < 3 + world.rand.nextInt(5); j++) {
			Vec3 up = Vec3.createVectorHelper(0, 1, 0);
			up.rotateAroundX((float) Math.toRadians(world.rand.nextFloat() * 75F));
			up.rotateAroundY((float) Math.toRadians(world.rand.nextFloat() * 360F));

			Vec3d angles = BobMathUtil.getEulerAngles(direction);
			Vec3 newDirection = Vec3.createVectorHelper(up.xCoord, up.yCoord, up.zCoord);
			newDirection.rotateAroundX((float) Math.toRadians(angles.y - 90D));
			newDirection.rotateAroundY((float) Math.toRadians(angles.x));
			newDirection = newDirection.mult(3F);

			Vec3d start = hit.add(newDirection.xCoord * 0.01, newDirection.yCoord * 0.01, newDirection.zCoord * 0.01);
			Vec3d end = hit.add(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord);
			RayTraceResult scatter = Library.rayTraceIncludeEntities(world, start, end, shooter);
			if(scatter != null && scatter.typeOfHit == Type.BLOCK) {
				Vec3d vec1 = scatter.hitVec.add(newDirection.xCoord * 0.01, newDirection.yCoord * 0.01, newDirection.zCoord * 0.01);
				Vec3d vec2 = hit.add(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord);
				scatter = Library.rayTraceIncludeEntities(world, vec1, vec2, shooter);
			}

			if(scatter != null && scatter.typeOfHit == Type.ENTITY && CompatibilityConfig.isWarDim(world)) {
				resetHurtResistance(scatter.entityHit);
				scatter.entityHit.attackEntityFrom(ModDamageSource.causeTauDamage(shooter, null), damage * 0.5F);
			}
		}
	}

	private static void resetHurtResistance(Entity entity) {
		try {
			if(hurtResistantTime == null) {
				hurtResistantTime = ReflectionHelper.findField(Entity.class, "hurtResistantTime", "field_70172_ad");
			}
			hurtResistantTime.setInt(entity, 0);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static Vec3d reflect(Vec3d direction, Axis axis) {
		switch(axis) {
		case X:
			return new Vec3d(-direction.x, direction.y, direction.z);
		case Y:
			return new Vec3d(direction.x, -direction.y, direction.z);
		case Z:
			return new Vec3d(direction.x, direction.y, -direction.z);
		default:
			return direction;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(chargeLoop != null) {
			chargeLoop.stopSound();
			chargeLoop = null;
		}

		if(firstPersonFireCounter > 10) {
			for(int i = 0; i < 50; i++) {
				double randX = world.rand.nextGaussian() * 0.01;
				double randY = world.rand.nextGaussian() * 0.01;
				double randZ = world.rand.nextGaussian() * 0.01;
				ParticleTauParticleFirstPerson particle = new ParticleTauParticleFirstPerson(world, -1.25 - world.rand.nextFloat() * 0.28F + randX, 0.25 + randY, 0.2 + randZ, 1.8F);
				particle.color(1.0F, 0.7F, 0.1F, 0.05F).lifetime(40).fadeIn(false);
				ModEventHandlerClient.firstPersonAuxParticles.add(particle);
			}
			doTauBeamFX(player, 1.0F, 0.9F, 0.6F, 1.0F, 12, player);
			Vec3d recoil = player.getLookVec().scale(-((float) firstPersonFireCounter / 300F));
			player.motionX += recoil.x;
			player.motionY += recoil.y;
			player.motionZ += recoil.z;
		}

		firstPersonFireCounter = -1;
	}

	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		setCharge(stack, 1);
	}

	@Override
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		super.startAction(stack, world, player, main, hand);
		if(!main && getItemWear(stack) < mainConfig.durability && Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.SPINUP.ordinal(), hand), (EntityPlayerMP) player);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onFireClient(ItemStack stack, EntityPlayer player, boolean shouldDoThirdPerson) {
		for(int i = 0; i < 50; i++) {
			double randX = player.world.rand.nextGaussian() * 0.01;
			double randY = player.world.rand.nextGaussian() * 0.01;
			double randZ = player.world.rand.nextGaussian() * 0.01;
			ParticleTauParticleFirstPerson particle = new ParticleTauParticleFirstPerson(player.world, -1.25 - player.world.rand.nextFloat() * 0.28F + randX, 0.25 + randY, 0.2 + randZ, 1.8F);
			particle.color(1.0F, 0.7F, 0.1F, 0.05F).lifetime(40).fadeIn(false);
			ModEventHandlerClient.firstPersonAuxParticles.add(particle);
		}
		doTauBeamFX(player, 1.0F, 0.7F, 0.1F, 1.0F, 4, player);
	}

	@SideOnly(Side.CLIENT)
	public static void doTauBeamFX(EntityPlayer player, float r, float g, float b, float a, int life, @Nullable Entity shooter) {
		ArrayList<Vec3d> hitPoints = new ArrayList<>(3);
		doTauBeamHits(player.world, player.getPositionEyes(MainRegistry.proxy.partialTicks()), player.getLook(MainRegistry.proxy.partialTicks()), hitPoints, shooter);
		Vec3d[] points = hitPoints.toArray(new Vec3d[0]);
		points[0] = new Vec3d(-0.38, -0.22, 0.3)
				.rotatePitch(-(float) Math.toRadians(player.rotationPitch))
				.rotateYaw(-(float) Math.toRadians(player.renderYawOffset))
				.add(player.getPositionEyes(MainRegistry.proxy.partialTicks()));
		ParticleTauBeam beam = new ParticleTauBeam(player.world, points, 0.2F);
		beam.color(r, g, b, a).lifetime(life);
		Minecraft.getMinecraft().effectRenderer.addEffect(beam);
	}

	@SideOnly(Side.CLIENT)
	public static void doTauBeamHits(World world, Vec3d prevPos, Vec3d direction, List<Vec3d> hitPoints, @Nullable Entity shooter) {
		hitPoints.add(prevPos);

		for(int i = 0; i < 4; i++) {
			RayTraceResult result = Library.rayTraceIncludeEntities(world, prevPos, direction.scale(40).add(prevPos), shooter);
			if(result == null || result.typeOfHit == Type.MISS) {
				hitPoints.add(direction.scale(40).add(prevPos));
				break;
			}

			hitPoints.add(result.hitVec);
			Vec3d normal = result.sideHit != null ? new Vec3d(result.sideHit.getDirectionVec()) : direction.scale(-1).normalize();
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauHit(world, result.hitVec.x, result.hitVec.y, result.hitVec.z, result.typeOfHit == Type.ENTITY ? 0.5F : 0.75F, normal));

			if(result.typeOfHit == Type.ENTITY) {
				spawnSparkCone(result.hitVec, direction, world, 5 + world.rand.nextInt(3));
				break;
			}

			if(result.sideHit != null && Math.acos(normal.dotProduct(direction.scale(-1))) > Math.toRadians(20)) {
				direction = reflect(direction, result.sideHit.getAxis());
				prevPos = result.hitVec.add(direction.scale(0.01));
				continue;
			}

			spawnClientScatterRays(world, shooter, result.hitVec, direction);
			break;
		}
	}

	@SideOnly(Side.CLIENT)
	private static void spawnClientScatterRays(World world, @Nullable Entity shooter, Vec3d hit, Vec3d direction) {
		for(int j = 0; j < 3 + world.rand.nextInt(5); j++) {
			Vec3 up = Vec3.createVectorHelper(0, 1, 0);
			up.rotateAroundX((float) Math.toRadians(world.rand.nextFloat() * 45F));
			up.rotateAroundY((float) Math.toRadians(world.rand.nextFloat() * 360F));

			Vec3d angles = BobMathUtil.getEulerAngles(direction);
			Vec3 newDirection = Vec3.createVectorHelper(up.xCoord, up.yCoord, up.zCoord);
			newDirection.rotateAroundX((float) Math.toRadians(angles.y - 90D));
			newDirection.rotateAroundY((float) Math.toRadians(angles.x));
			newDirection = newDirection.mult(3F);

			Vec3d start = hit.add(newDirection.xCoord * 0.01, newDirection.yCoord * 0.01, newDirection.zCoord * 0.01);
			Vec3d end = hit.add(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord);
			RayTraceResult scatter = Library.rayTraceIncludeEntities(world, start, end, shooter);
			if(scatter != null && scatter.typeOfHit == Type.BLOCK) {
				Vec3d vec1 = scatter.hitVec.add(newDirection.xCoord * 0.01, newDirection.yCoord * 0.01, newDirection.zCoord * 0.01);
				Vec3d vec2 = hit.add(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord);
				scatter = Library.rayTraceIncludeEntities(world, vec1, vec2, shooter);
			}

			if(scatter == null || scatter.typeOfHit == Type.MISS) {
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauRay(world, new Vec3d[] { hit, end }, 0.25F));
			} else {
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauRay(world, new Vec3d[] { hit, scatter.hitVec }, 0.25F));
				Vec3d normal = scatter.sideHit != null ? new Vec3d(scatter.sideHit.getDirectionVec()) : direction.scale(-1).normalize();
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauHit(world, scatter.hitVec.x, scatter.hitVec.y, scatter.hitVec.z, scatter.typeOfHit == Type.ENTITY ? 0.5F : 0.75F, normal));
				if(scatter.typeOfHit == Type.ENTITY) {
					spawnSparkCone(scatter.hitVec, direction, world, 5 + world.rand.nextInt(3));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private static void spawnSparkCone(Vec3d pos, Vec3d direction, World world, int count) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "spark");
		tag.setString("mode", "coneBurst");
		tag.setDouble("posX", pos.x);
		tag.setDouble("posY", pos.y);
		tag.setDouble("posZ", pos.z);
		tag.setDouble("dirX", direction.x * 0.5D);
		tag.setDouble("dirY", direction.y * 0.5D);
		tag.setDouble("dirZ", direction.z * 0.5D);
		tag.setFloat("r", 1.0F);
		tag.setFloat("g", 0.9F);
		tag.setFloat("b", 0.9F);
		tag.setFloat("a", 1.0F);
		tag.setInteger("lifetime", 5);
		tag.setInteger("randLifetime", 8);
		tag.setFloat("width", 0.01F);
		tag.setFloat("length", 0.5F);
		tag.setFloat("gravity", 0.1F);
		tag.setFloat("angle", 70.0F);
		tag.setInteger("count", count);
		tag.setFloat("randomVelocity", 0.1F);
		MainRegistry.proxy.effectNT(tag);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(!main && getItemWear(stack) < mainConfig.durability && Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
			chargeLoop = MainRegistry.proxy.getLoopedSound(HBMSoundHandler.tauChargeLoop2, SoundCategory.PLAYERS, (float) player.posX, (float) player.posY, (float) player.posZ, 1.0F, 0.75F);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauChargeLoop2, SoundCategory.PLAYERS, 1.0F, 0.75F);
			firstPersonFireCounter = 0;
			if(chargeLoop != null) {
				chargeLoop.startSound();
			}
			ModEventHandlerClient.firstPersonAuxParticles.clear();
		}
	}

	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		super.updateServer(stack, world, player, slot, hand);

		if(getIsAltDown(stack) && getItemWear(stack) < mainConfig.durability) {
			int charge = getCharge(stack);
			if(charge > 200) {
				setCharge(stack, 0);
				setItemWear(stack, mainConfig.durability);
				if(CompatibilityConfig.isWarDim(world)) {
					player.attackEntityFrom(ModDamageSource.tauBlast, 1000.0F);
					world.createExplosion(player, player.posX, player.posY + player.eyeHeight, player.posZ, 5.0F, true);
				}
				return;
			}

			if(charge > 0) {
				setCharge(stack, charge + 1);
				if(charge % 10 == 1 && charge < 140 && charge > 2) {
					if(Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
						Library.consumeInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo);
						setStored(stack, getStored(stack) + 1);
					} else {
						setCharge(stack, 0);
						setStored(stack, 0);
					}
				}
			} else {
				setStored(stack, 0);
			}
		} else {
			setCharge(stack, 0);
			setStored(stack, 0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasCustomHudElement() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHud(ScaledResolution res, GuiIngame gui, ItemStack stack, float partialTicks) {
		float x = res.getScaledWidth() / 2F;
		float y = res.getScaledHeight() / 2F;
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.gluontau_hud);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GlStateManager.color(0.9F, 0.9F, 0.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
		NTMRenderHelper.drawGuiRect(x - 2F, y - 2F, 0, 0, 4, 4, 1, 1);
		NTMRenderHelper.resetColor();
		GlStateManager.disableBlend();
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		super.updateClient(stack, world, player, slot, hand);

		if(getItemWear(stack) >= mainConfig.durability && firstPersonFireCounter >= 0) {
			endActionClient(stack, world, player, false, hand);
		}

		if(firstPersonFireCounter >= 0) {
			ParticleTauLightning lightning = new ParticleTauLightning(world, 0.0, 0.25, 0.2, 5.0F + Math.min(firstPersonFireCounter / 25.0F, 2.0F), 8.0F + world.rand.nextFloat() * 15.0F);
			lightning.lifetime(4);
			lightning.color(1.0F, 0.5F, 0.1F, 0.1F + Math.min(firstPersonFireCounter / 400.0F, 0.1F));
			ModEventHandlerClient.firstPersonAuxParticles.add(lightning);

			if(firstPersonFireCounter % 3 == 0) {
				ParticleTauParticleFirstPerson particle = new ParticleTauParticleFirstPerson(world, -1.25, 0.25, 0.2, 1.0F + Math.min(firstPersonFireCounter / 10.0F, 10.0F));
				ParticleTauParticleFirstPerson particle2 = new ParticleTauParticleFirstPerson(world, 0.0, 0.25, 0.2, 10.0F + Math.min(firstPersonFireCounter / 10.0F, 10.0F));
				particle.color(1.0F, 0.35F, 0.1F, 1.5F).lifetime(5);
				particle2.color(1.0F, 0.35F, 0.1F, 0.8F + Math.min(firstPersonFireCounter / 800.0F, 1.0F)).lifetime(5);
				ModEventHandlerClient.firstPersonAuxParticles.add(particle);
				ModEventHandlerClient.firstPersonAuxParticles.add(particle2);
			}

			if(firstPersonFireCounter > 20) {
				for(int i = 0; i < 3; i++) {
					double randX = world.rand.nextGaussian() * 0.01;
					double randY = world.rand.nextGaussian() * 0.01;
					double randZ = world.rand.nextGaussian() * 0.01;
					ParticleTauParticleFirstPerson particle = new ParticleTauParticleFirstPerson(world, -1.25 - world.rand.nextFloat() * 0.28F + randX, 0.25 + randY, 0.2 + randZ, 0.4F + Math.min(firstPersonFireCounter / 10.0F, 1.5F));
					particle.color(1.0F, 0.7F, 0.1F, 0.05F).lifetime(40);
					ModEventHandlerClient.firstPersonAuxParticles.add(particle);
				}
			}

			if(firstPersonFireCounter == 100) {
				ModEventHandlerClient.firstPersonAuxParticles.add(new ParticleTauMuzzleLightning(world, -1.5, 0.25, 0.2, 0.1F));
			}

			if((firstPersonFireCounter > 40 && (firstPersonFireCounter % 10 == 0 || firstPersonFireCounter % 7 == 0))
					|| (firstPersonFireCounter > 120 && (firstPersonFireCounter % 8 == 0 || firstPersonFireCounter % 5 == 0))) {
				float offset = (1.0F - firstPersonFireCounter / 200.0F) * 0.05F;
				Vec3d pos = new Vec3d(-0.48, -0.15 - offset, 1.3)
						.rotatePitch(-(float) Math.toRadians(player.rotationPitch))
						.rotateYaw(-(float) Math.toRadians(player.renderYawOffset))
						.add(player.getPositionEyes(MainRegistry.proxy.partialTicks()));
				Vec3d pos2 = new Vec3d(-0.47, -0.15 - offset, 1.3)
						.rotatePitch(-(float) Math.toRadians(player.rotationPitch))
						.rotateYaw(-(float) Math.toRadians(player.renderYawOffset))
						.add(player.getPositionEyes(MainRegistry.proxy.partialTicks()));
				Vec3d sparkDir = pos2.subtract(pos).normalize();
				spawnSmallSparkCone(pos, sparkDir, world);
			}

			firstPersonFireCounter++;
		}

		if(chargeLoop != null) {
			chargeLoop.updatePosition((float) player.posX, (float) player.posY, (float) player.posZ);
			chargeLoop.updatePitch(chargeLoop.getPitch() + 0.01F);
		}
	}

	@SideOnly(Side.CLIENT)
	private static void spawnSmallSparkCone(Vec3d pos, Vec3d direction, World world) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "spark");
		tag.setString("mode", "coneBurst");
		tag.setDouble("posX", pos.x);
		tag.setDouble("posY", pos.y);
		tag.setDouble("posZ", pos.z);
		tag.setDouble("dirX", direction.x * 0.1D);
		tag.setDouble("dirY", direction.y * 0.1D - 0.05D);
		tag.setDouble("dirZ", direction.z * 0.1D);
		tag.setFloat("r", 1.0F);
		tag.setFloat("g", 0.7F);
		tag.setFloat("b", 0.1F);
		tag.setFloat("a", 1.0F);
		tag.setInteger("lifetime", 5);
		tag.setInteger("randLifetime", 8);
		tag.setFloat("width", 0.0075F);
		tag.setFloat("length", 0.3F);
		tag.setFloat("gravity", 0.04F);
		tag.setFloat("angle", 30.0F);
		tag.setInteger("count", 3 + world.rand.nextInt(3));
		tag.setFloat("randomVelocity", 0.1F);
		MainRegistry.proxy.effectNT(tag);
	}

	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config, EnumHand hand) {
		if(mainConfig.animations.containsKey(AnimType.CYCLE) && player instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal(), hand), (EntityPlayerMP) player);
		}
		PacketDispatcher.wrapper.sendToAllTracking(new GunFXPacket(player, hand, FXType.FIRE), new TargetPoint(world.provider.getDimension(), player.posX, player.posY, player.posZ, 1.0D));
		doTauShot(world, player, player.getPositionEyes(MainRegistry.proxy.partialTicks()), player.getLook(MainRegistry.proxy.partialTicks()), 8.0F);
		setHasShot(stack, true);
	}

	public static void setHasShot(ItemStack stack, boolean b) {
		writeNBT(stack, "hasShot", b ? 1 : 0);
	}

	public static boolean getHasShot(ItemStack stack) {
		return readNBT(stack, "hasShot") == 1;
	}

	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "gauss_charge", i);
	}

	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "gauss_charge");
	}

	public static void setStored(ItemStack stack, int i) {
		writeNBT(stack, "gauss_stored", i);
	}

	public static int getStored(ItemStack stack) {
		return readNBT(stack, "gauss_stored");
	}
}
