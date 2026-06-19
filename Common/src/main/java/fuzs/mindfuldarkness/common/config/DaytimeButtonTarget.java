package fuzs.mindfuldarkness.common.config;

import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.function.Predicate;

public enum DaytimeButtonTarget implements Predicate<Screen> {
    NONE {
        @Override
        public boolean test(Screen screen) {
            return false;
        }
    },
    TITLE_SCREEN {
        @Override
        public boolean test(Screen screen) {
            return screen instanceof TitleScreen;
        }
    },
    PAUSE_SCREEN {
        @Override
        public boolean test(Screen screen) {
            return screen instanceof PauseScreen;
        }
    },
    BOTH {
        @Override
        public boolean test(Screen screen) {
            return screen instanceof TitleScreen || screen instanceof PauseScreen;
        }
    }
}
