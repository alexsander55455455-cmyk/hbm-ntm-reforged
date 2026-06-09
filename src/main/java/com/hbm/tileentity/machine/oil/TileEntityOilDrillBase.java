package com.hbm.tileentity.machine.oil;

import com.hbm.api.energymk2.IEnergyReceiverMK2;
import com.hbm.api.fluid.IFluidStandardTransceiver;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.tileentity.*;
import com.hbm.util.BobMathUtil;
import com.hbm.util.SoundUtil;
import com.hbmspace.blocks.generic.BlockOreFluid;
import com.hbmspace.dim.CelestialBody;
import com.hbmspace.dim.SolarSystem;
import com.hbmspace.dim.WorldProviderCelestial;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;

public abstract class TileEntityOilDrillBase extends TileEntityMachineBase implements ITickable, IEnergyReceiverMK2, IFluidStandardTransceiver, IConfigurableMachine, IPersistentNBT, IGUIProvider, IFluidCopiable, IUpgradeInfoProvider, IConnectionAnchors {
    private final UpgradeManagerNT upgradeManager = new UpgradeManagerNT(this);
    public long power;
    public int indicator = 0;
    public FluidTankNTM[] tanks;
    public int speedLevel;
    public int energyLevel;
    public int overLevel;
    protected HashSet<BlockPos> trace = new HashSet<>();

    public TileEntityOilDrillBase() {
        super(0, true, true);

        inventory = new ItemStackHandler(8) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                markDirty();
            }

            @Override
            public void setStackInSlot(int slot, @NotNull ItemStack stack) {
                super.setStackInSlot(slot, stack);
                if (Library.isMachineUpgrade(stack) && slot >= 5 && slot <= 7)
                    SoundUtil.playUpgradePlugSound(world, pos);
            }
        };

        tanks = new FluidTankNTM[2];
        tanks[0] = new FluidTankNTM(Fluids.OIL, 64_000).withOwner(this);
        tanks[1] = new FluidTankNTM(Fluids.GAS, 64_000).withOwner(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.power = compound.getLong("power");
        for(int i = 0; i < this.tanks.length; i++)
            this.tanks[i].readFromNBT(compound, "t" + i);
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setLong("power", power);
        for(int i = 0; i < this.tanks.length; i++)
            this.tanks[i].writeToNBT(compound, "t" + i);
        return super.writeToNBT(compound);
    }

    @Override
    public void writeNBT(NBTTagCompound nbt) {

        boolean empty = power == 0;
        for (FluidTankNTM tank : tanks) if (tank.getFill() > 0) empty = false;

        if (!empty) {
            nbt.setLong("power", power);
            for (int i = 0; i < this.tanks.length; i++) {
                this.tanks[i].writeToNBT(nbt, "t" + i);
            }
        }
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {
        this.power = nbt.getLong("power");
        for (int i = 0; i < this.tanks.length; i++)
            this.tanks[i].readFromNBT(nbt, "t" + i);
    }

    @Override
    public void update() {
        if (!world.isRemote) {

            this.updateConnections();

            this.tanks[0].unloadTank(1, 2, inventory);
            this.tanks[1].unloadTank(3, 4, inventory);

            upgradeManager.checkSlots(inventory, 5, 7);
            this.speedLevel = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.SPEED), 3);
            this.energyLevel = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.POWER), 3);
            this.overLevel = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.OVERDRIVE), 3) + 1;
            int abLevel = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.AFTERBURN), 3);

            int toBurn = Math.min(tanks[1].getFill(), abLevel * 10);

            if (toBurn > 0) {
                tanks[1].setFill(tanks[1].getFill() - toBurn);
                this.power += toBurn * 5L;

                if (this.power > this.getMaxPower())
                    this.power = this.getMaxPower();
            }

            power = Library.chargeTEFromItems(inventory, 0, power, this.getMaxPower());

            for (DirPos pos : getConPos()) {
                if (tanks[0].getFill() > 0)
                    this.sendFluid(tanks[0], world, pos.getPos().getX(), pos.getPos().getY(), pos.getPos().getZ(), pos.getDir());
                if (tanks[1].getFill() > 0)
                    this.sendFluid(tanks[1], world, pos.getPos().getX(), pos.getPos().getY(), pos.getPos().getZ(), pos.getDir());
            }

            if (this.power >= this.getPowerReqEff() && this.tanks[0].getFill() < this.tanks[0].getMaxFill() && this.tanks[1].getFill() < this.tanks[1].getMaxFill()) {

                this.power -= this.getPowerReqEff();

                if (world.getTotalWorldTime() % getDelayEff() == 0) {
                    this.indicator = 0;

                    for (int y = pos.getY() - 1; y >= getDrillDepth(); y--) {
                        BlockPos columnPos = new BlockPos(pos.getX(), y, pos.getZ());
                        Block columnBlock = world.getBlockState(columnPos).getBlock();

                        if (columnBlock == ModBlocks.oil_pipe) {
                            if (y == getDrillDepth())
                                this.indicator = 1;
                            continue;
                        }

                        if (isOilDepositBlock(columnBlock)) {
                            if (!trySuck(y))
                                this.indicator = 2;
                            break;
                        }

                        tryDrill(y);
                        break;
                    }
                }

            } else {
                this.indicator = 2;
            }

            this.sendUpdate();
        }
    }

    public void sendUpdate() {
        networkPackNT(25);
    }

    @Override
    public void serialize(ByteBuf buf) {
        super.serialize(buf);
        buf.writeLong(power);
        buf.writeInt(indicator);

        for (FluidTankNTM tank : tanks)
            tank.serialize(buf);
    }

    @Override
    public void deserialize(ByteBuf buf) {
        super.deserialize(buf);
        this.power = buf.readLong();
        this.indicator = buf.readInt();

        for (FluidTankNTM tank : tanks)
            tank.deserialize(buf);
    }

    public boolean canPump() {
        return true;
    }

    public int getPowerReqEff() {
        int req = this.getPowerReq();
        return (req + (req / 4 * this.speedLevel) - (req / 4 * this.energyLevel)) * this.overLevel;
    }

    public int getDelayEff() {
        int delay = getDelay();
        return Math.max((delay - (delay / 4 * this.speedLevel) + (delay / 10 * this.energyLevel)) / this.overLevel, 1);
    }

    public abstract int getPowerReq();

    public abstract int getDelay();

    public static boolean matchesHbmOreRegistry(Block b, String... paths) {
        if (b == null)
            return false;

        ResourceLocation id = b.getRegistryName();
        if (id == null || !"hbm".equals(id.getNamespace()))
            return false;

        for (String path : paths) {
            if (path.equals(id.getPath()))
                return true;
        }

        return false;
    }

    public boolean isOilDepositBlock(Block b) {
        if (b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty || b == ModBlocks.ore_bedrock_oil)
            return true;
        if (b instanceof BlockOreFluid)
            return true;
        if (BlockOreFluid.getFullBlock(b) != null)
            return true;
        return matchesHbmOreRegistry(b, "ore_oil", "ore_oil_empty", "ore_bedrock_oil");
    }

    public void tryDrill(int y) {
        BlockPos posD = new BlockPos(pos.getX(), y, pos.getZ());
        Block b = world.getBlockState(posD).getBlock();

        if (isOilDepositBlock(b)) {
            this.indicator = 2;
            return;
        }

        if (b.getExplosionResistance(null) < 1000) {
            onDrill(y);
            world.setBlockState(posD, ModBlocks.oil_pipe.getDefaultState());
        } else {
            this.indicator = 2;
        }
    }

    public void onDrill(int y) {
    }

    public int getDrillDepth() {
        return 5;
    }

    public boolean trySuck(int y) {
        BlockPos startPos = new BlockPos(pos.getX(), y, pos.getZ());
        Block startBlock = world.getBlockState(startPos).getBlock();

        if (!canSuckBlock(startBlock))
            return false;

        if (!this.canPump())
            return true;

        trace.clear();
        return suckRec(startPos, 0);
    }

    public boolean canSuckBlock(Block b) {
        if (b == ModBlocks.ore_bedrock_oil || matchesHbmOreRegistry(b, "ore_bedrock_oil"))
            return false;
        if (b instanceof BlockOreFluid)
            return true;
        if (BlockOreFluid.getFullBlock(b) != null)
            return true;
        return matchesHbmOreRegistry(b, "ore_oil", "ore_oil_empty");
    }

    public boolean suckRec(BlockPos currentPos, int layer) {
        if (trace.contains(currentPos))
            return false;

        trace.add(currentPos);

        if (layer > 64)
            return false;

        Block b = world.getBlockState(currentPos).getBlock();

        if (b instanceof BlockOreFluid) {
            onSuck((BlockOreFluid) b, currentPos);
            return true;
        }

        if (matchesHbmOreRegistry(b, "ore_oil") || b == ModBlocks.ore_oil) {
            onSuckDeposit(currentPos);
            return true;
        }

        if (BlockOreFluid.getFullBlock(b) != null) {
            for (ForgeDirection dir : BobMathUtil.getShuffledDirs()) {
                BlockPos neighborPos = currentPos.add(dir.offsetX, dir.offsetY, dir.offsetZ);
                if (suckRec(neighborPos, layer + 1))
                    return true;
            }
        }

        return false;
    }

    protected int resolveDepositMeta(BlockOreFluid block, IBlockState state) {
        int blockMeta = block.getMetaFromState(state);

        if (world.provider.getDimension() == 0) {
            return SolarSystem.Body.KERBIN.ordinal();
        }

        if (world.provider instanceof WorldProviderCelestial) {
            return CelestialBody.getMeta(world);
        }

        return blockMeta;
    }

    public void onSuck(BlockOreFluid block, BlockPos targetPos) {
        IBlockState state = world.getBlockState(targetPos);
        int meta = resolveDepositMeta(block, state);

        tanks[0].setTankType(block.getPrimaryFluid(meta));
        tanks[1].setTankType(block.getSecondaryFluid(meta));

        tanks[0].setFill(Math.min(tanks[0].getFill() + getPrimaryFluidAmount(block, meta), tanks[0].getMaxFill()));
        if (tanks[1].getTankType() != Fluids.NONE) {
            tanks[1].setFill(Math.min(tanks[1].getFill() + getSecondaryFluidAmount(block, meta), tanks[1].getMaxFill()));
        }

        attemptDrain(block, targetPos, meta);
    }

    protected void onSuckDeposit(BlockPos targetPos) {
        Block block = world.getBlockState(targetPos).getBlock();

        if (block instanceof BlockOreFluid) {
            onSuck((BlockOreFluid) block, targetPos);
            return;
        }

        tanks[0].setTankType(Fluids.OIL);
        tanks[1].setTankType(Fluids.GAS);
        tanks[0].setFill(Math.min(tanks[0].getFill() + getLegacyOilPerDeposit(), tanks[0].getMaxFill()));
        tanks[1].setFill(Math.min(tanks[1].getFill() + getLegacyGasPerDeposit(), tanks[1].getMaxFill()));

        if (world.rand.nextDouble() < getLegacyDrainChance()) {
            world.setBlockState(targetPos, ModBlocks.ore_oil_empty.getDefaultState(), 3);
        }
    }

    protected int getLegacyOilPerDeposit() {
        return 500;
    }

    protected int getLegacyGasPerDeposit() {
        return 100 + world.rand.nextInt(401);
    }

    protected double getLegacyDrainChance() {
        return 0.05D;
    }

    protected int getPrimaryFluidAmount(BlockOreFluid block, int meta) {
        return block.getPrimaryFluidAmount(meta);
    }

    protected int getSecondaryFluidAmount(BlockOreFluid block, int meta) {
        return block.getSecondaryFluidAmount(meta);
    }

    protected void attemptDrain(BlockOreFluid block, BlockPos targetPos, int meta) {
        block.drain(world, targetPos, meta, 1);
    }

    @Override
    public long getPower() {
        return power;
    }

    @Override
    public void setPower(long i) {
        power = i;

    }


    @Override
    public FluidTankNTM[] getSendingTanks() {
        return tanks;
    }

    @Override
    public FluidTankNTM[] getReceivingTanks() {
        return new FluidTankNTM[0];
    }

    @Override
    public FluidTankNTM[] getAllTanks() {
        return tanks;
    }

    public abstract DirPos[] getConPos();

    protected void updateConnections() {
        for (DirPos pos : getConPos()) {
            this.trySubscribe(world, pos.getPos().getX(), pos.getPos().getY(), pos.getPos().getZ(), pos.getDir());
        }
    }

    @Override
    public FluidTankNTM getTankToPaste() {
        return null;
    }

    @Override
    public boolean canProvideInfo(ItemMachineUpgrade.UpgradeType type, int level, boolean extendedInfo) {
        return type == ItemMachineUpgrade.UpgradeType.SPEED || type == ItemMachineUpgrade.UpgradeType.POWER || type == ItemMachineUpgrade.UpgradeType.OVERDRIVE || type == ItemMachineUpgrade.UpgradeType.AFTERBURN;
    }

    @Override
    public HashMap<ItemMachineUpgrade.UpgradeType, Integer> getValidUpgrades() {
        HashMap<ItemMachineUpgrade.UpgradeType, Integer> upgrades = new HashMap<>();
        upgrades.put(ItemMachineUpgrade.UpgradeType.SPEED, 3);
        upgrades.put(ItemMachineUpgrade.UpgradeType.POWER, 3);
        upgrades.put(ItemMachineUpgrade.UpgradeType.AFTERBURN, 3);
        upgrades.put(ItemMachineUpgrade.UpgradeType.OVERDRIVE, 3);
        return upgrades;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

}