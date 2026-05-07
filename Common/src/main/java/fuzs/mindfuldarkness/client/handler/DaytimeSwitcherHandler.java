package fuzs.mindfuldarkness.client.handler;

import fuzs.mindfuldarkness.MindfulDarkness;
import fuzs.mindfuldarkness.client.gui.screens.PixelConfigScreen;
import fuzs.mindfuldarkness.config.ClientConfig;
import fuzs.puzzleslib.common.api.event.v1.core.EventResultHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class DaytimeSwitcherHandler {
    private static final WidgetSprites CROSS_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "switcher/cross_button"), MindfulDarkness.id("switcher/cross_button_highlighted"));
    private static final WidgetSprites EDIT_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id("switcher/edit_button"),
            MindfulDarkness.id("switcher/edit_button_highlighted"));
    private static final WidgetSprites DAYTIME_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "switcher/daytime_button"),
            MindfulDarkness.id("switcher/daytime_button_disabled"),
            MindfulDarkness.id("switcher/daytime_button_highlighted"));
    private static final WidgetSprites NIGHTTIME_BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "switcher/nighttime_button"),
            MindfulDarkness.id("switcher/nighttime_button_disabled"),
            MindfulDarkness.id("switcher/nighttime_button_highlighted"));
    public static final Identifier TEXTURE_LOCATION = MindfulDarkness.id("textures/gui/daytime_switcher.png");
    public static final String KEY_DEBUG_IDENTIFIER = "screen.debug.identifier";
    public static final String KEY_DEBUG_MENU_TYPE = "screen.debug.menuType";

    private static AbstractWidget[] buttons;

    public static void onEndTick(Minecraft minecraft) {
        setHorizontalButtonPosition(minecraft.screen);
    }

    public static void onAfterMouseClick(Screen screen, MouseButtonEvent mouseButtonEvent) {
        setHorizontalButtonPosition(screen);
    }

    private static void setHorizontalButtonPosition(Screen screen) {
        if (buttons == null) return;
        if (screen instanceof AbstractContainerScreen<?> containerScreen && screen instanceof RecipeUpdateListener) {
            int leftPos = containerScreen.leftPos;
            int imageWidth = containerScreen.imageWidth;
            buttons[0].setX(leftPos + imageWidth - 3 - 21);
            buttons[1].setX(leftPos + imageWidth - 3 - 40);
            buttons[2].setX(leftPos + imageWidth - 3 - 68);
            buttons[3].setX(leftPos + imageWidth - 3 - 95);
        }
    }

    public static EventResultHolder<@Nullable Screen> onScreenOpening(@Nullable Screen oldScreen, @Nullable Screen newScreen) {
        if (newScreen == null) {
            buttons = null;
        } else if (!(oldScreen instanceof PixelConfigScreen)) {
            // don't get minecraft from screen, it is still null
            Minecraft minecraft = Minecraft.getInstance();

            if (MindfulDarkness.CONFIG.get(ClientConfig.class).debugAllScreens) {
                String identifier = FontColorHandler.getScreenId(newScreen);

                if (identifier != null) {
                    Component message = Component.translatable(KEY_DEBUG_IDENTIFIER,
                            ComponentUtils.wrapInSquareBrackets(Component.literal(identifier)));

                    // we don't need both as chat messages are logged automatically
                    if (minecraft.level != null) {
                        minecraft.gui.getChat().addClientSystemMessage(message);
                    } else {
                        MindfulDarkness.LOGGER.info(message.getString());
                    }
                }
            }

            if (newScreen instanceof AbstractContainerScreen<?> containerScreen && MindfulDarkness.CONFIG.get(
                    ClientConfig.class).debugContainerTypes) {
                // don't use vanilla getter as it throws an UnsupportedOperationException for the player inventory
                MenuType<?> menuType = containerScreen.getMenu().menuType;

                if (menuType != null) {
                    Component component = Component.literal(BuiltInRegistries.MENU.getKey(menuType).toString());
                    Component message = Component.translatable(KEY_DEBUG_MENU_TYPE,
                            ComponentUtils.wrapInSquareBrackets(component));
                    minecraft.gui.getChat().addClientSystemMessage(message);
                }
            }
        }

        return EventResultHolder.pass();
    }

    public static void onAfterBackground(AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (supportsDaytimeSwitcher(screen)) {
            extractThemeBackground(guiGraphics, screen.leftPos, screen.topPos, screen.imageWidth);
        }
    }

    public static void extractThemeBackground(GuiGraphicsExtractor guiGraphics, int leftPos, int topPos, int imageWidth) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                TEXTURE_LOCATION,
                leftPos + imageWidth - 3 - 101,
                topPos - 24,
                0,
                226,
                101,
                24,
                256,
                256);
    }

    private static boolean supportsDaytimeSwitcher(AbstractContainerScreen<?> containerScreen) {
        if (MindfulDarkness.CONFIG.get(ClientConfig.class).hideInGameSwitcher) return false;
        if (containerScreen.height >= containerScreen.imageHeight + 2 * 24) {
            if (containerScreen instanceof CreativeModeInventoryScreen) return false;
            MenuType<?> menuType = containerScreen.getMenu().menuType;
            return menuType == null || !MindfulDarkness.CONFIG.get(ClientConfig.class).menuBlacklist.contains(menuType);
        }
        return false;
    }

    public static void onAfterInit(AbstractContainerScreen<?> screen, int screenWidth, int screenHeight, List<AbstractWidget> widgets, UnaryOperator<AbstractWidget> addWidget, Consumer<AbstractWidget> removeWidget) {
        if (supportsDaytimeSwitcher(screen)) {
            buttons = makeButtons(screen, screen.leftPos, screen.topPos, screen.imageWidth);
            for (AbstractWidget button : buttons) {
                addWidget.apply(button);
            }
        }
    }

    public static AbstractWidget[] makeButtons(Screen screen, int leftPos, int topPos, int imageWidth) {
        AbstractWidget[] abstractWidgets = new AbstractWidget[4];
        abstractWidgets[0] = new ImageButton(leftPos + imageWidth - 3 - 21,
                topPos - 18,
                15,
                15,
                CROSS_BUTTON_SPRITES,
                (Button button) -> {
                    screen.onClose();
                });
        abstractWidgets[1] = new ImageButton(leftPos + imageWidth - 3 - 40,
                topPos - 18,
                15,
                15,
                EDIT_BUTTON_SPRITES,
                (Button button) -> {
                    if (screen instanceof PixelConfigScreen pixelConfigScreen) {
                        pixelConfigScreen.closeToLastScreen();
                    } else {
                        screen.minecraft.setScreen(new PixelConfigScreen(screen));
                    }
                });
        abstractWidgets[2] = new ImageButton(leftPos + imageWidth - 3 - 68,
                topPos - 20,
                24,
                19,
                DAYTIME_BUTTON_SPRITES,
                (Button button) -> {
                    toggleThemeButtons(abstractWidgets[3], abstractWidgets[2], true);
                });
        abstractWidgets[3] = new ImageButton(leftPos + imageWidth - 3 - 95,
                topPos - 20,
                24,
                19,
                NIGHTTIME_BUTTON_SPRITES,
                (Button button) -> {
                    toggleThemeButtons(abstractWidgets[3], abstractWidgets[2], true);
                });
        toggleThemeButtons(abstractWidgets[3], abstractWidgets[2], false);
        return abstractWidgets;
    }

    private static void toggleThemeButtons(AbstractWidget lightThemeWidget, AbstractWidget darkThemeWidget, boolean toggleSwitch) {
        if (toggleSwitch) {
            toggleSwitch();
        }

        boolean darkTheme = MindfulDarkness.CONFIG.get(ClientConfig.class).darkTheme.get();
        lightThemeWidget.active = darkTheme;
        darkThemeWidget.active = !darkTheme;
    }

    public static void toggleSwitch() {
        ModConfigSpec.BooleanValue configValue = MindfulDarkness.CONFIG.get(ClientConfig.class).darkTheme;
        configValue.set(!configValue.get());
        configValue.save();
    }
}
