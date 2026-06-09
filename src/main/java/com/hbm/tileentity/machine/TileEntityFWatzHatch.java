package com.hbm.tileentity.machine;

import com.hbm.interfaces.AutoRegister;
import com.hbm.tileentity.TileEntityLoadedBase;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

@AutoRegister
public class TileEntityFWatzHatch extends TileEntityLoadedBase {

    TileEntityFWatzCore fwatz;

    private TileEntityFWatzCore getReactorTE(World world, BlockPos pos) {
        if(fwatz != null && fwatz.isOk){
            return fwatz;
        }
		EnumFacing e = world.getBlockState(pos).getValue(BlockHorizontal.FACING);
        TileEntity te = world.getTileEntity(pos.add(e.getXOffset() * -6, -1, e.getZOffset() * -6));
        if(te instanceof TileEntityFWatzCore core) {
            if(core.isOk) {
                fwatz = core;
                return core;
            } else {
                return null;
            }
        }
        te = world.getTileEntity(pos.add(e.getXOffset() * -6, 3, e.getZOffset() * -6));
        if(te instanceof TileEntityFWatzCore core) {
            if(core.isOk) {
                fwatz = core;
                return core;
            } else {
                return null;
            }
        }
        return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        TileEntityFWatzCore core = this.getReactorTE(world, pos);
        if(core != null && core.isOk)
            return core.hasCapability(capability, facing);
        return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        TileEntityFWatzCore core = this.getReactorTE(world, pos);
        if(core != null && core.isOk)
            return core.getCapability(capability, facing);
        return super.getCapability(capability, facing);
	}
}