package modderspolaroid;

import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class ClientProxy extends CommonProxy {

	public void init() {
		KeyBindingRegistry.registerKeyBinding(new ShotKeyHandler());
	}

}
