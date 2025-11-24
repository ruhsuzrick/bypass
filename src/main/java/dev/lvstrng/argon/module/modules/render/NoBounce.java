package dev.lvstrng.grade.module.modules.render;

import dev.lvstrng.grade.module.Category;
import dev.lvstrng.grade.module.Module;
import dev.lvstrng.grade.utils.EncryptedString;

public final class NoBounce extends Module {
	public NoBounce() {
		super(EncryptedString.of("No Bounce"),
				EncryptedString.of("Removes the crystal bounce"),
				-1,
				Category.RENDER);
	}
}
