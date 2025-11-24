package dev.lvstrng.grade.module;

import dev.lvstrng.grade.utils.EncryptedString;

public enum Category {
	COMBAT(EncryptedString.of("Combat")),
	MISC(EncryptedString.of("Misc")),
	RENDER(EncryptedString.of("Render")),
	CLIENT(EncryptedString.of("Client"));
	public final CharSequence name;

	Category(CharSequence name) {
		this.name = name;
	}
}
