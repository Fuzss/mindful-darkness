package fuzs.mindfuldarkness.common.data.client;

import fuzs.mindfuldarkness.common.client.gui.screens.PixelConfigScreen;
import fuzs.mindfuldarkness.common.client.handler.DaytimeSwitcherHandler;
import fuzs.mindfuldarkness.common.client.util.DarkeningAlgorithm;
import fuzs.puzzleslib.common.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.add(DaytimeSwitcherHandler.KEY_DEBUG_IDENTIFIER, "Screen Identifier: %s");
        builder.add(DaytimeSwitcherHandler.KEY_DEBUG_MENU_TYPE, "Menu Type: %s");
        builder.add(DarkeningAlgorithm.LINEAR.getComponent(), "Linear");
        builder.add(DarkeningAlgorithm.GRAYSCALE_AND_LINEAR.getComponent(), "Grayscale And Linear");
        builder.add(DarkeningAlgorithm.HSP.getComponent(), "HSP");
        builder.add(DarkeningAlgorithm.GRAYSCALE_AND_HSP.getComponent(), "Grayscale And HSP");
        builder.add(DarkeningAlgorithm.HSL.getComponent(), "HSL");
        builder.add(DarkeningAlgorithm.GRAYSCALE_AND_HSL.getComponent(), "Grayscale And HSL");
        builder.add(PixelConfigScreen.ALGORITHM_COMPONENT, "Algorithm");
        builder.add(PixelConfigScreen.INTERFACE_DARKNESS_COMPONENT, "Interface Darkness");
        builder.add(PixelConfigScreen.FONT_BRIGHTNESS_COMPONENT, "Font Brightness");
    }
}
