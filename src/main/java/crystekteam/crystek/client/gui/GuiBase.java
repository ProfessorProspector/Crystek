package crystekteam.crystek.client.gui;

import crystekteam.crystek.container.ContainerBase;
import crystekteam.crystek.lib.ModInfo;
import crystekteam.crystek.tiles.prefab.TileBase;
import crystekteam.crystek.util.RenderUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import reborncore.client.guibuilder.GuiBuilder;

/**
 * Created by Gigabit101 on 01/06/2016.
 */
public class GuiBase extends GuiContainer
{
    public static final ResourceLocation overlays = new ResourceLocation(ModInfo.MOD_NAME.toLowerCase() + ":" + "textures/gui/builder.png");

    public String name;
    public TileBase tile;
    public ContainerBase container;
	GuiBuilder builder = new GuiBuilder(overlays);

	public GuiBase(EntityPlayer player, TileBase tile, ContainerBase container, String name)
	{
		super(container);
		this.container = container;
		this.name = name;
		this.tile = tile;
	}

	@Override
    public void initGui()
	{
		super.initGui();
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawString(this, I18n.translateToLocal("tile." + name + ".name"), guiLeft + 65, guiTop + 10);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 83, true);
    }

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
        builder.drawEnergyBar(this, 5, 5, 70, (int) container.power, (int) tile.getMaxCapacity(), mouseX - guiLeft, mouseY - guiTop, "Tesla");
	}


    @Deprecated
	public void drawProgressbar(TileBase tile, int x, int y)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		if (this.tile != null)
		{
			int j = this.container.progress / 4;
			this.mc.getTextureManager().bindTexture(overlays);
			if (j > 0)
				this.drawTexturedModalRect(k + x, l + y, 14, 1, j + 1, 4);
		}
	}

    @Deprecated
	public void drawPowerBar(TileBase tile)
	{
		drawPowerBar(tile, 9, 44);
	}

    @Deprecated
	public void drawPowerBar(TileBase tile, int x, int y)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		if (this.tile != null)
		{
			int j = (int) this.container.power;
			this.mc.getTextureManager().bindTexture(overlays);
			if (j > 0)
				this.drawTexturedModalRect(k + x, l + y + 12 - j, 0, 48 - j, 14, j + 2);
		}
	}

    @Deprecated
	public void drawBurn(TileBase tile, int x, int y)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		if (tile != null)
		{
			int j = this.container.burnTime;
			this.mc.getTextureManager().bindTexture(overlays);

			if (j > 0)
				this.drawTexturedModalRect(k + x, l + y + 12 - j, 0, 62 - j, 14, j + 2);
		}
	}

    @Deprecated
	public void drawTank(TileBase tile, double x, double y, double zLevel, double width, double height)
	{
		RenderUtil.renderGuiTank(tile.tank, x, y, zLevel, width, height);
		drawTankOverlay(tile, (int) x - guiLeft - 1, (int) y - guiTop + 4);
	}

    @Deprecated
	public void drawTank(TileBase tile)
	{
		RenderUtil.renderGuiTank(tile.tank, guiLeft + 39, guiTop + 11, zLevel, 14, 64);
		drawTankOverlay(tile, 38, 15);
	}

    @Deprecated
	public void drawTankOverlay(TileBase tile, int x, int y)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.mc.getTextureManager().bindTexture(overlays);
		this.drawTexturedModalRect(k + x, l + y, 14, 10, 16, 64);
	}
}
