package fuzs.mindfuldarkness.common.config;

import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

public enum DaytimeButtonScreens {
    NONE {
        @Override
        public boolean matches(Screen screen) {
            return false;
        }
    },
    TITLE_SCREEN("modmenu.title", "fml.menu.mods", "menu.online", "menu.multiplayer", "menu.singleplayer") {
        @Override
        public boolean matches(Screen screen) {
            return screen instanceof TitleScreen;
        }
    },
    PAUSE_SCREEN("modmenu.title",
            "fml.menu.mods",
            "menu.reportBugs",
            "menu.shareToLan",
            "menu.playerReporting",
            "menu.options",
            "gui.stats") {
        @Override
        public boolean matches(Screen screen) {
            return screen instanceof PauseScreen;
        }
    },
    BOTH("modmenu.title",
            "fml.menu.mods",
            "menu.online",
            "menu.multiplayer",
            "menu.singleplayer",
            "menu.reportBugs",
            "menu.shareToLan",
            "menu.playerReporting",
            "menu.options",
            "gui.stats") {
        @Override
        public boolean matches(Screen screen) {
            return screen instanceof TitleScreen || screen instanceof PauseScreen;
        }
    };

    public final String[] buttonKeys;

    DaytimeButtonScreens(String... translationKeys) {
        this.buttonKeys = translationKeys;
    }

    public abstract boolean matches(Screen screen);
}
