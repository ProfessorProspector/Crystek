package crystekteam.crystek.init;

import crystekteam.crystek.Crystek;
import crystekteam.crystek.blocks.BlockCrystek;
import crystekteam.crystek.core.Machine;
import crystekteam.crystek.machines.MachineCreativeTeslaCell;
import crystekteam.crystek.machines.MachineFurnace;
import crystekteam.crystek.machines.MachineGenerator;
import crystekteam.crystek.machines.MachineTank;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import reborncore.modcl.ModCL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 14/01/2017.
 */
public class MachinesInit {
	static List<Machine> MACHINE_LIST = new ArrayList<Machine>();

	ModCL mod;

	public static void init() {
		registerMachine(new MachineGenerator());
		registerMachine(new MachineFurnace());
		registerMachine(new MachineTank());
		registerMachine(new MachineCreativeTeslaCell());
		for (Machine m : MACHINE_LIST) {
			registerBlock(new BlockCrystek(m).setUnlocalizedName(Crystek.MOD_ID.toLowerCase() + "." + m.getName()), m.getName());
			GameRegistry.registerTileEntity(m.getClass(), m.getName());
		}
	}

	public static void registerMachine(Machine machine) {
		MACHINE_LIST.add(machine);
	}

	public static List<Machine> getMachineList() {
		return MACHINE_LIST;
	}

	public static void registerBlock(Block block, String name) {
		Crystek.MOD_CL.blockModelsToRegister.add(block);
		block.setRegistryName(name);
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block), block.getRegistryName());

	}

	public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name) {
		block.setRegistryName(name);
		GameRegistry.register(block);
		try {
			ItemBlock itemBlock = itemclass.getConstructor(Block.class).newInstance(block);
			itemBlock.setRegistryName(name);
			GameRegistry.register(itemBlock);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
