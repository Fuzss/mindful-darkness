package fuzs.mindfuldarkness.common.client.handler;

import fuzs.mindfuldarkness.common.MindfulDarkness;
import fuzs.mindfuldarkness.common.config.ClientConfig;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class DaytimeButtonHandler {
    private static final WidgetSprites DAYTIME_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "widget/daytime_button"),
            MindfulDarkness.id("widget/daytime_button_disabled"),
            MindfulDarkness.id("widget/daytime_button_highlighted"));
    private static final WidgetSprites NIGHTTIME_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "widget/nighttime_button"),
            MindfulDarkness.id("widget/nighttime_button_disabled"),
            MindfulDarkness.id("widget/nighttime_button_highlighted"));
    public static final Component LIGHT_MODE_COMPONENT = Component.translatable(MindfulDarkness.id("mode")
            .toLanguageKey("screen", "light"));
    public static final Component DARK_MODE_COMPONENT = Component.translatable(MindfulDarkness.id("mode")
            .toLanguageKey("screen", "dark"));

    public static void onAfterInit(Screen screen, int screenWidth, int screenHeight, List<AbstractWidget> widgets, UnaryOperator<AbstractWidget> addWidget, Consumer<AbstractWidget> removeWidget) {
        if (!MindfulDarkness.CONFIG.get(ClientConfig.class).darkModeToggleScreens.test(screen)) {
            return;
        }

        List<AbstractWidget> iconButtons = getIconButtonsInRow(widgets, 3);
        if (iconButtons.isEmpty()) {
            return;
        }

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int totalWidth = 0;
        AbstractWidget rightmost = iconButtons.getFirst();
        for (AbstractWidget widget : iconButtons) {
            minX = Math.min(minX, widget.getX());
            maxX = Math.max(maxX, widget.getX() + widget.getWidth());
            totalWidth += widget.getWidth();
            if (widget.getX() > rightmost.getX()) {
                rightmost = widget;
            }
        }

        int spacing = (maxX - minX - totalWidth) / Math.max(1, iconButtons.size() - 1);
        if (spacing <= 0) {
            return;
        }

        int posX = rightmost.getX() + rightmost.getWidth() + spacing;
        int posY = rightmost.getY();
        ImageButton imageButton = new ImageButton(posX, posY, 20, 20, DAYTIME_BUTTON_SPRITES, (Button button) -> {
            DaytimeSwitcherHandler.toggleSwitch();
            updateDaytimeButton((ImageButton) button);
        }, CommonComponents.EMPTY);
        addWidget.apply(imageButton);
        updateDaytimeButton(imageButton);

        iconButtons.add(imageButton);
        int moveX = (imageButton.getWidth() + spacing) / 2;
        for (AbstractWidget widget : iconButtons) {
            widget.setX(widget.getX() - moveX);
        }
    }

    private static List<AbstractWidget> getIconButtonsInRow(List<AbstractWidget> widgets, int minIconButtons) {
        Map<Integer, Integer> spriteButtonCountByY = new LinkedHashMap<>();
        for (AbstractWidget widget : widgets) {
            if (widget instanceof SpriteIconButton) {
                spriteButtonCountByY.merge(widget.getY(), 1, Integer::sum);
            }
        }

        int targetY = -1;
        for (Map.Entry<Integer, Integer> entry : spriteButtonCountByY.entrySet()) {
            if (entry.getValue() >= minIconButtons) {
                targetY = entry.getKey();
                break;
            }
        }

        if (targetY == -1) {
            return Collections.emptyList();
        }

        List<AbstractWidget> row = new ArrayList<>();
        for (AbstractWidget widget : widgets) {
            if (widget.getY() == targetY && widget.getWidth() == Button.DEFAULT_HEIGHT) {
                row.add(widget);
            }
        }

        return row;
    }

    private static void updateDaytimeButton(ImageButton imageButton) {
        if (MindfulDarkness.CONFIG.get(ClientConfig.class).darkTheme.get()) {
            imageButton.sprites = DAYTIME_BUTTON_SPRITES;
            imageButton.setTooltip(Tooltip.create(LIGHT_MODE_COMPONENT));
        } else {
            imageButton.sprites = NIGHTTIME_BUTTON_SPRITES;
            imageButton.setTooltip(Tooltip.create(DARK_MODE_COMPONENT));
        }
    }
}
