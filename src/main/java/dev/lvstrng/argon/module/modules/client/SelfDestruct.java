package dev.lvstrng.grade.module.modules.client;

import com.sun.jna.Memory;
import dev.lvstrng.grade.Grade;
import dev.lvstrng.grade.gui.ClickGui;
import dev.lvstrng.grade.module.Category;
import dev.lvstrng.grade.module.Module;
import dev.lvstrng.grade.module.setting.*;
import dev.lvstrng.grade.utils.EncryptedString;
import dev.lvstrng.grade.utils.Utils;

import java.io.File;

@SuppressWarnings("all")
public final class SelfDestruct extends Module {
	public static boolean destruct = false;

	private final BooleanSetting replaceMod = new BooleanSetting(EncryptedString.of("Replace Mod"), true)
			.setDescription(EncryptedString.of("Repalces the mod with the original JAR file of the ImmediatelyFast mod"));

	private final BooleanSetting saveLastModified = new BooleanSetting(EncryptedString.of("Save Last Modified"), true)
			.setDescription(EncryptedString.of("Saves the last modified date after self destruct"));

	private final StringSetting downloadURL = new StringSetting(EncryptedString.of("Replace URL"), "https://cdn.modrinth.com/data/Orvt0mRa/versions/K4hsdO9H/indium-1.0.34%2Bmc1.21.jar");

	public SelfDestruct() {
		super(EncryptedString.of("Self Destruct"),
				EncryptedString.of("Removes the client from your game |Credits to lwes for deletion|"),
				-1,
				Category.CLIENT);
		addSettings(replaceMod, saveLastModified, downloadURL);
	}

	@Override
	public void onEnable() {
		destruct = true;

		Grade.INSTANCE.getModuleManager().getModule(ClickGUI.class).setEnabled(false);
		setEnabled(false);

	    Grade.INSTANCE.getProfileManager().saveProfile();

		if (mc.currentScreen instanceof ClickGui) {
			Grade.INSTANCE.guiInitialized = false;
			mc.currentScreen.close();
		}

		if (replaceMod.getValue()) {
			try {
				String modUrl = downloadURL.getValue();
				File currentJar = Utils.getCurrentJarPath();

				if (currentJar.exists())
                    Utils.replaceModFile(modUrl, Utils.getCurrentJarPath());
			} catch (Exception ignored) {}
		}

		for (Module module : Grade.INSTANCE.getModuleManager().getModules()) {
			module.setEnabled(false);

			module.setName(null);
			module.setDescription(null);

			for (Setting<?> setting : module.getSettings()) {
				setting.setName(null);
				setting.setDescription(null);

				if(setting instanceof StringSetting set)
					set.setValue(null);
			}
			module.getSettings().clear();
		}

		Runtime runtime = Runtime.getRuntime();

		if (saveLastModified.getValue())
			Grade.INSTANCE.resetModifiedDate();

		for (int i = 0; i <= 10; i++) {
			runtime.gc();
			runtime.runFinalization();

			try {
				Thread.sleep(100 * i);
				Memory.purge();
				Memory.disposeAll();
			} catch (InterruptedException ignored) {}
		}
	}
}
