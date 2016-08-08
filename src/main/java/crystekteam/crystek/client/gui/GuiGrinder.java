package crystekteam.crystek.client.gui;

import crystekteam.crystek.container.ContainerGrinder;
import crystekteam.crystek.lib.ModInfo;
import crystekteam.crystek.tiles.machines.TileGrinder;
import crystekteam.crystek.tiles.prefab.TileBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Travis on 16/06/2016.
 */
public class GuiGrinder extends GuiBase
{
    TileGrinder tile;

    public GuiGrinder(EntityPlayer player, TileBase tile)
    {
        super(player, tile, new ContainerGrinder(tile, player), "crystek.grinder");
        this.tile = (TileGrinder) tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
        builder.drawSlot(this, guiLeft + 47, guiTop + 34);
        builder.drawSlot(this, guiLeft + 107, guiTop + 34);
        builder.drawProgressBar(this, container.progress, guiLeft + 76, guiTop + 34);
    }
}
