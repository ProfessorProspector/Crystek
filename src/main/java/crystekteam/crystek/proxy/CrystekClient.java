package crystekteam.crystek.proxy;

import crystekteam.crystek.Crystek;
import crystekteam.crystek.init.CrystekItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Created by McKeever on 09-Nov-16.
 */
public class CrystekClient extends CrystekServer {

	@Override
	public void registerRenders() {
		int i;
		for (i = 0; i < CrystekItems.MATERIALS.types.size(); ++i) {
			registerItemModel(CrystekItems.MATERIALS, i, CrystekItems.MATERIALS.types.get(i));
		}
	}

	static void registerItemModel(Item i, int meta)
	{
		ResourceLocation loc = i.getRegistryName();
		ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "inventory"));
	}

	static void registerItemModel(Item i, int meta, String variant)
	{
		ResourceLocation loc = i.getRegistryName();
		ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "type=" + variant));
	}

	@Override
	public void registerItemModel(String modid, Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modid + ":" + id, "inventory"));
	}

	@Override
	public void registerCustomBlockStateLocation(Block block, final String resourceLocation, boolean item) {
		super.registerCustomBlockStateLocation(block, resourceLocation, item);
		ModelLoader.setCustomStateMapper(block, new DefaultStateMapper() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				String resourceDomain = Block.REGISTRY.getNameForObject(state.getBlock()).getResourceDomain();
				String propertyString = getPropertyString(state.getProperties());
				return new ModelResourceLocation(resourceDomain + ':' + resourceLocation, propertyString);
			}
		});
		if (item) {
			String resourceDomain = Block.REGISTRY.getNameForObject(block).getResourceDomain();
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(resourceDomain + ':' + resourceLocation, "inventory"));
		}
	}

	@Override
	public void registerCustomBlockStateLocation(Item item, String resourceLocation) {
		String resourceDomain = Item.REGISTRY.getNameForObject(item).getResourceDomain();
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(resourceDomain + ':' + resourceLocation, "inventory"));

	}
}