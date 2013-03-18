package modderspolaroid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.EnumSet;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ShotKeyHandler extends KeyHandler {

	static KeyBinding shotKeyBinding = new KeyBinding("Modder's Polaroid", Keyboard.KEY_P);

	public ShotKeyHandler() {
		super(new KeyBinding[] { shotKeyBinding }, new boolean[] { false });
	}

	@Override
	public String getLabel() {
		return "Modder's Polaroid";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if(tickEnd == false) return;
		saveScreenshot();
		// TODO 自動生成されたメソッド・スタブ

	}

	private void saveScreenshot() {
		Minecraft mc = Minecraft.getMinecraft();
		if(!mc.theWorld.isRemote) return;

		int displayWidth = mc.displayWidth;
		int displayHeight = mc.displayHeight;

		try {
			int size = displayWidth * displayHeight;
			IntBuffer buf = BufferUtils.createIntBuffer(size);
			int[] pixels = new int[size];

			GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			buf.clear();
			GL11.glReadPixels(0, 0, displayWidth, displayHeight, GL12.GL_BGRA,
					GL12.GL_UNSIGNED_INT_8_8_8_8_REV, buf);
			buf.get(pixels);
			arrayCopy(pixels, displayWidth, displayHeight);
			BufferedImage bufferedimage = new BufferedImage(displayWidth, displayHeight, 1);
			bufferedimage.setRGB(0, 0, displayWidth, displayHeight, pixels, 0, displayWidth);

			int x = ModdersPolaroid.config.x;
			int y = ModdersPolaroid.config.y;
			int w = ModdersPolaroid.config.width;
			int h = ModdersPolaroid.config.height;
			BufferedImage trimmingImage = bufferedimage.getSubimage(x, y, w, h);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String t = sdf.format(new java.util.Date());
			File dir = new File(mc.getMinecraftDir(), "screenshots");
			dir.mkdir();

			File file = new File(dir, t+".png");

			ImageIO.write(trimmingImage, "png", file);
			mc.ingameGUI.getChatGUI().printChatMessage("Modder's Polaroid save: " + file.getName());

		} catch (Exception exception) {
			exception.printStackTrace();
			mc.ingameGUI.getChatGUI().printChatMessage("Failed to save: " + exception);
		}
	}

	private void arrayCopy(int[] pixels, int displayWidth, int displayHeight) {
		int[] aint1 = new int[displayWidth];
		int k = displayHeight / 2;

		for (int l = 0; l < k; ++l) {
			System.arraycopy(pixels, l * displayWidth, aint1, 0, displayWidth);
			System.arraycopy(pixels, (displayHeight - 1 - l) * displayWidth,
					pixels, l * displayWidth, displayWidth);
			System.arraycopy(aint1, 0, pixels, (displayHeight - 1 - l)
					* displayWidth, displayWidth);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
