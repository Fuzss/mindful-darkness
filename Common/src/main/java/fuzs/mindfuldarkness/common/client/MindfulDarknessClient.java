package fuzs.mindfuldarkness.common.client;

import fuzs.mindfuldarkness.common.client.handler.DaytimeButtonHandler;
import fuzs.mindfuldarkness.common.client.handler.DaytimeSwitcherHandler;
import fuzs.mindfuldarkness.common.client.handler.FontColorHandler;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.common.api.client.event.v1.gui.ScreenEvents;
import fuzs.puzzleslib.common.api.client.event.v1.gui.ScreenMouseEvents;
import fuzs.puzzleslib.common.api.client.event.v1.gui.ScreenOpeningCallback;
import fuzs.puzzleslib.common.api.event.v1.core.EventPhase;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class MindfulDarknessClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ScreenEvents.afterBackground(AbstractContainerScreen.class).register(DaytimeSwitcherHandler::onAfterBackground);
        ScreenEvents.afterInit(AbstractContainerScreen.class).register(DaytimeSwitcherHandler::onAfterInit);
        ScreenEvents.afterInit(Screen.class).register(EventPhase.AFTER, DaytimeButtonHandler::onAfterInit);
        ScreenOpeningCallback.EVENT.register(DaytimeSwitcherHandler::onScreenOpening);
        ClientTickEvents.END.register(DaytimeSwitcherHandler::onEndTick);
        ScreenMouseEvents.afterMouseClick(AbstractContainerScreen.class)
                .register(DaytimeSwitcherHandler::onAfterMouseClick);
        ScreenEvents.beforeExtract(Screen.class).register(FontColorHandler::onBeforeExtract);
        ScreenEvents.afterExtract(Screen.class).register(FontColorHandler::onAfterExtract);
    }
}
