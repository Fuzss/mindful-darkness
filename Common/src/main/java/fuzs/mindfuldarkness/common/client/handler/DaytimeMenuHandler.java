package fuzs.mindfuldarkness.common.client.handler;

import fuzs.mindfuldarkness.common.MindfulDarkness;
import fuzs.mindfuldarkness.common.config.ClientConfig;
import fuzs.mindfuldarkness.common.config.DaytimeButtonScreens;
import fuzs.puzzleslib.common.api.client.gui.v2.components.ScreenElementPositioner;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class DaytimeMenuHandler {
    private static final WidgetSprites DAYTIME_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "widget/daytime_button"),
            MindfulDarkness.id("widget/daytime_button_disabled"),
            MindfulDarkness.id("widget/daytime_button_highlighted"));
    private static final WidgetSprites NIGHTTIME_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "widget/nighttime_button"),
            MindfulDarkness.id("widget/nighttime_button_disabled"),
            MindfulDarkness.id("widget/nighttime_button_highlighted"));

    public static void onAfterInit(Screen screen, int screenWidth, int screenHeight, List<AbstractWidget> widgets, UnaryOperator<AbstractWidget> addWidget, Consumer<AbstractWidget> removeWidget) {
        DaytimeButtonScreens darkModeToggleScreens = MindfulDarkness.CONFIG.get(ClientConfig.class).darkModeToggleScreens;
        if (darkModeToggleScreens.matches(screen)) {
            ImageButton imageButton = new ImageButton(20, 20, DAYTIME_BUTTON_SPRITES, (Button button) -> {
                DaytimeSwitcherHandler.toggleSwitch();
                updateButtonSprites((ImageButton) button);
            }, CommonComponents.EMPTY);
            if (ScreenElementPositioner.tryPositionElement(imageButton, widgets, darkModeToggleScreens.buttonKeys)) {
                addWidget.apply(imageButton);
                updateButtonSprites(imageButton);
            }
        }
    }

    private static void updateButtonSprites(ImageButton imageButton) {
        if (MindfulDarkness.CONFIG.get(ClientConfig.class).darkTheme.get()) {
            imageButton.sprites = DAYTIME_BUTTON_SPRITES;
        } else {
            imageButton.sprites = NIGHTTIME_BUTTON_SPRITES;
        }
    }
}
