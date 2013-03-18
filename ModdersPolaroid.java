package modderspolaroid;

import java.util.logging.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "ModdersPolaroid", name = "ModdersPolaroid", version = "1.0")
public class ModdersPolaroid {
	@SidedProxy(clientSide = "modderspolaroid.ClientProxy", serverSide = "modderspolaroid.CommonProxy")
	public static CommonProxy proxy;

	@Instance("ModdersPolaroid")
	public static ModdersPolaroid instance;

	public static Logger logger = Logger.getLogger("Minecraft");

	public static Config config = new Config();

	@Mod.Init
	public void load(FMLInitializationEvent event) {
		proxy.init();
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		config.load(event.getSuggestedConfigurationFile());
	}
}