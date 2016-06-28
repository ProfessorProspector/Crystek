package crystekteam.crystek.tiles.machines;

import crystekteam.crystek.tiles.prefab.TileMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * Created by Gigabit101 on 02/06/2016.
 */
public class TileFurnace extends TileMachine
{
    public int power;
    public int cost = 10;
    int input1 = 0;
    int output = 1;

    public TileFurnace()
    {
        super(6, "furnace", 64, 10000, 50, 50, 0, 100);
        fuelScale = 100;
    }

    @Override
    public void update()
    {
        boolean burning = isBurning();
        boolean updateInventory = false;
        if (isBurning() && canSmelt())
        {
            this.addProgress();
            if(this.getProgress() % 10 == 0)
            {
                this.usePower(cost);
            }
            if (getProgress() >= fuelScale)
            {
                updateState();
                this.resetProgress();
                cookItems();
                updateInventory = true;
            }
        }
        if (burning != isBurning())
        {
            updateInventory = true;
        }
        if (updateInventory)
        {
            markDirty();
        }
        handleChargeSlots(0, false, 5, true);
    }

    public void cookItems()
    {
        if(!worldObj.isRemote)
        {
            if (this.canSmelt())
            {
                ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(input1));

                if (getStackInSlot(output) == null)
                {
                    setInventorySlotContents(output, itemstack.copy());
                }
                else if (getStackInSlot(output).isItemEqual(itemstack))
                {
                    getStackInSlot(output).stackSize += itemstack.stackSize;
                }
                if (getStackInSlot(input1).stackSize > 1)
                {
                    this.decrStackSize(input1, 1);
                }
                else
                {
                    setInventorySlotContents(input1, null);
                }
                syncWithAll();
            }
        }
    }

    public boolean canSmelt()
    {
        if (getStackInSlot(input1) == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(input1));
            if (itemstack == null)
                return false;
            if (getStackInSlot(output) == null)
                return true;
            if (!getStackInSlot(output).isItemEqual(itemstack))
                return false;
            int result = getStackInSlot(output).stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }

    public boolean isBurning()
    {
        return getStoredPower() > cost;
    }
}
