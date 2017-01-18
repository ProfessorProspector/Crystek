package crystekteam.crystek.core;

import crystekteam.crystek.Crystek;
import crystekteam.crystek.container.slots.SlotTeslaCharge;
import crystekteam.crystek.guis.CrystekGuiBuilder;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import reborncore.client.guibuilder.GuiBuilder;
import reborncore.common.util.Tank;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 17/01/2017.
 */
public abstract class Machine extends TileEntity implements ITickable
{
    /**
     * Inv
     */
    public ItemStackHandler inv = new ItemStackHandler(invSize());

    public abstract int invSize();

    public abstract int guiID();

    public abstract String getName();

    public boolean hasInv()
    {
        if(invSize() != 0)
        {
            return true;
        }
        return false;
    }

    public ItemStackHandler getInv()
    {
        return inv;
    }

    public void openGui(EntityPlayer player, Machine machine)
    {
        player.openGui(Crystek.MOD_CL, machine.guiID(), machine.world, machine.pos.getX(), machine.pos.getY(), machine.pos.getZ());
    }

    /**
     * Tank
     */
    public Tank tank = new Tank(getName(), getTankSize(), this);

    public abstract int getTankSize();

    public Tank getTank()
    {
        return tank;
    }

    public boolean hasTank()
    {
        if(getTank().getCapacity() != 0)
        {
            return true;
        }
        return false;
    }

    /**
     * NBT
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        if(hasInv())
        {
            compound = super.writeToNBT(compound);
            compound.merge(inv.serializeNBT());
            return compound;
        }
        if(hasTank())
        {
            compound = super.writeToNBT(compound);
            tank.writeToNBT(compound);
            return compound;
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(hasInv())
        {
            inv.deserializeNBT(compound);
        }
        if(hasTank())
        {
            tank.readFromNBT(compound);
        }
    }

    /**
     * GUI
     */
    public CrystekGuiBuilder builder = new CrystekGuiBuilder();

    @SideOnly(Side.CLIENT)
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop, int xSize, int ySize, GuiContainer gui)
    {
        builder.drawDefaultBackground(gui, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(gui, guiLeft + xSize / 2, guiTop + 80, true);
        if(getSlots() != null)
        {
            for(Slot s: getSlots())
            {
                if(s instanceof SlotTeslaCharge)
                {
                    builder.drawChargeSlot(gui, guiLeft + s.xPos - 1, guiTop + s.yPos - 1);
                }
                else
                {
                    builder.drawSlot(gui, guiLeft + s.xPos - 1, guiTop + s.yPos - 1);
                }
            }
        }
    }

    /**
     * Container
     */

    @Nullable
    public abstract List<Slot> getSlots();

    @SideOnly(Side.CLIENT)
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY, GuiContainer gui, int guiLeft, int guiTop) {}

    /**
     * Tile
     */
    @Override
    public void update() {}

    /**
     * Tesla
     */
    public BaseTeslaContainer teslaContainer = new BaseTeslaContainer(maxCapacity(), maxInput(), maxOutput());

    public abstract long maxCapacity();

    public abstract long maxInput();

    public abstract long maxOutput();

    public abstract EnumTeslaType teslaType();

    public BaseTeslaContainer getTeslaContainer()
    {
        return teslaContainer;
    }

    /**
     * Capability
     */
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(hasInv() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        if(hasTank() && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        if(teslaType() == EnumTeslaType.GENERATOR && capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_HOLDER)
        {
            return true;
        }
        if(teslaType() == EnumTeslaType.CONSUMER && capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(hasInv() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getInv());
        }
        if(hasTank() && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getTank());
        }
        if(teslaType() != EnumTeslaType.NULL && capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER)
        {
            return (T) getTeslaContainer();
        }
        return super.getCapability(capability, facing);
    }
}
