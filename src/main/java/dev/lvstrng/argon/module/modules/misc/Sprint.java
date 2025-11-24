package dev.lvstrng.grade.module.modules.misc;

import dev.lvstrng.grade.event.events.TickListener;
import dev.lvstrng.grade.module.Category;
import dev.lvstrng.grade.module.Module;
import dev.lvstrng.grade.utils.EncryptedString;

public final class Sprint extends Module implements TickListener {
    public Sprint() {
        super(EncryptedString.of("Sprint"), EncryptedString.of("Keeps you sprinting at all times"), -1, Category.MISC);
    }

    @Override
    public void onEnable() {
        eventManager.add(TickListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        eventManager.remove(TickListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        mc.player.setSprinting(mc.player.input.pressingForward);
    }
}
