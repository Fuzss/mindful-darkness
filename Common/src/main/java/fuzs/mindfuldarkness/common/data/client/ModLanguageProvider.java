package fuzs.mindfuldarkness.common.data.client;

import fuzs.mindfuldarkness.common.client.gui.screens.PixelConfigScreen;
import fuzs.mindfuldarkness.common.client.handler.DaytimeButtonHandler;
import fuzs.mindfuldarkness.common.client.handler.DaytimeSwitcherHandler;
import fuzs.mindfuldarkness.common.client.util.DarkeningAlgorithm;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        add(DaytimeSwitcherHandler.KEY_DEBUG_ID, "Screen Id: %s");
        add(DaytimeSwitcherHandler.KEY_DEBUG_MENU, "Menu Type: %s");
        add(DaytimeButtonHandler.LIGHT_MODE_COMPONENT, "Light Mode");
        add(DaytimeButtonHandler.DARK_MODE_COMPONENT, "Dark Mode");
        add(DarkeningAlgorithm.LINEAR.getComponent(), "Linear");
        add(DarkeningAlgorithm.GRAYSCALE_AND_LINEAR.getComponent(), "Grayscale And Linear");
        add(DarkeningAlgorithm.HSP.getComponent(), "HSP");
        add(DarkeningAlgorithm.GRAYSCALE_AND_HSP.getComponent(), "Grayscale And HSP");
        add(DarkeningAlgorithm.HSL.getComponent(), "HSL");
        add(DarkeningAlgorithm.GRAYSCALE_AND_HSL.getComponent(), "Grayscale And HSL");
        add(PixelConfigScreen.ALGORITHM_COMPONENT, "Algorithm");
        add(PixelConfigScreen.INTERFACE_DARKNESS_COMPONENT, "Interface Darkness");
        add(PixelConfigScreen.FONT_BRIGHTNESS_COMPONENT, "Font Brightness");
    }
}
