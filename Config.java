package modderspolaroid;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

public class Config {

	public static int x = 306;
	public static int y = 85;
	public static int width = 235;
	public static int height = 131;
	public void load(File file) {
		Configuration cfg = new Configuration(file);
		try {
			cfg.load();
			x = cfg.get(Configuration.CATEGORY_GENERAL, "x", x).getInt();
			y = cfg.get(Configuration.CATEGORY_GENERAL, "y", y).getInt();
			width = cfg.get(Configuration.CATEGORY_GENERAL, "width", width).getInt();
			height = cfg.get(Configuration.CATEGORY_GENERAL, "height", height).getInt();
			cfg.save();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Modder's Polaroid load config exception");
		} finally {
			cfg.save();
		}
	}

}
