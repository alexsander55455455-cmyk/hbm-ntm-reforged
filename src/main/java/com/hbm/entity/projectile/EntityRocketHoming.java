package com.hbm.entity.projectile;

import com.hbm.interfaces.AutoRegister;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

@AutoRegister(name = "entity_rocket_homing", trackingRange = 1000)
public class EntityRocketHoming extends EntityRocket {

	public int homingRadius = 35;
	public int homingMod = 15;
	public float acceptance = 120.0F;
	private int lockonTicks = 0;
	private boolean hasBeeped = false;

	public EntityRocketHoming(World worldIn) {
		super(worldIn);
	}

	public EntityRocketHoming(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityRocketHoming(World world, EntityLivingBase shooter, EntityLivingBase shootingAt, float velocity, float inaccuracy) {
		super(world, shooter, shootingAt, velocity, inaccuracy);
	}

	public EntityRocketHoming(World world, EntityLivingBase shooter, float velocity, EnumHand hand) {
		super(world, shooter, velocity, hand);
	}

	public EntityRocketHoming(World world, int x, int y, int z, double mx, double my, double mz, double grav) {
		super(world, x, y, z, mx, my, mz, grav);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.isDead) {
			return;
		}

		if(!this.steer()) {
			this.lockonTicks = 0;
		}
	}

	private boolean steer() {
		if(this.homingRadius <= 0) {
			return false;
		}

		Vec3d path = new Vec3d(this.motionX, this.motionY, this.motionZ);
		double speed = path.length();
		if(speed <= 1.0E-7D) {
			return false;
		}

		Vec3d pathNorm = path.normalize();
		AxisAlignedBB searchBox = new AxisAlignedBB(
				this.posX - this.homingRadius, this.posY - this.homingRadius, this.posZ - this.homingRadius,
				this.posX + this.homingRadius, this.posY + this.homingRadius, this.posZ + this.homingRadius);
		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, searchBox);

		Entity bestTarget = null;
		double bestAngle = Double.POSITIVE_INFINITY;

		for(Entity entity : entities) {
			if(entity == this.shootingEntity || entity.isDead) {
				continue;
			}

			if((double)(entity.height * entity.width * entity.width) < 0.5D) {
				continue;
			}

			if(Library.isObstructed(this.world, entity.posX, entity.posY, entity.posZ, this.posX, this.posY, this.posZ)) {
				continue;
			}

			Vec3d relative = new Vec3d(entity.posX - this.posX, entity.posY + entity.getEyeHeight() - this.posY, entity.posZ - this.posZ);
			double relLength = relative.length();
			if(relLength <= 1.0E-7D) {
				continue;
			}

			double cosine = relative.dotProduct(pathNorm) / relLength;
			cosine = Math.max(-1.0D, Math.min(1.0D, cosine));
			double angle = Math.acos(cosine) * 180.0D / Math.PI;

			if(angle < bestAngle) {
				bestAngle = angle;
				bestTarget = entity;
			}
		}

		if(bestTarget == null) {
			return false;
		}

		Vec3d target = new Vec3d(bestTarget.posX - this.posX, bestTarget.posY - this.posY, bestTarget.posZ - this.posZ);
		double targetLength = target.length();
		if(targetLength <= 1.0E-7D) {
			return false;
		}

		Vec3d targetNorm = target.normalize();
		double divisor = bestAngle * (double)this.homingMod;
		Vec3d newDirection;

		if(divisor <= 1.0E-7D) {
			newDirection = targetNorm;
		} else {
			newDirection = new Vec3d(
					(pathNorm.x * (divisor - 1.0D) + targetNorm.x) / divisor,
					(pathNorm.y * (divisor - 1.0D) + targetNorm.y) / divisor,
					(pathNorm.z * (divisor - 1.0D) + targetNorm.z) / divisor);
		}

		if(newDirection.length() <= 1.0E-7D) {
			return false;
		}

		Vec3d newPath = newDirection.normalize().scale(speed);
		this.motionX = newPath.x;
		this.motionY = newPath.y;
		this.motionZ = newPath.z;
		this.shoot(this.motionX, this.motionY, this.motionZ, (float)speed, 0.0F);

		++this.lockonTicks;
		if(this.lockonTicks == 5 && !this.hasBeeped) {
			this.world.playSound(null, this.posX, this.posY, this.posZ, HBMSoundHandler.stingerLockon, SoundCategory.HOSTILE, 10.0F, this.getIsCritical() ? 0.75F : 1.0F);
			this.hasBeeped = true;
		}

		return true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if(compound.hasKey("homingRadius", 99)) {
			this.homingRadius = compound.getInteger("homingRadius");
		}
		if(compound.hasKey("homingMod", 99)) {
			this.homingMod = compound.getInteger("homingMod");
		}
		if(compound.hasKey("acceptance", 99)) {
			this.acceptance = compound.getFloat("acceptance");
		}
		this.hasBeeped = compound.getBoolean("hasBeeped");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("homingRadius", this.homingRadius);
		compound.setInteger("homingMod", this.homingMod);
		compound.setFloat("acceptance", this.acceptance);
		compound.setBoolean("hasBeeped", this.hasBeeped);
	}
}
