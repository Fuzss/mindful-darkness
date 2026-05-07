package fuzs.mindfuldarkness.neoforge;

import fuzs.mindfuldarkness.common.MindfulDarkness;
import fuzs.mindfuldarkness.common.data.client.ModLanguageProvider;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(MindfulDarkness.MOD_ID)
public class MindfulDarknessNeoForge {

    public MindfulDarknessNeoForge() {
        ModConstructor.construct(MindfulDarkness.MOD_ID, MindfulDarkness::new);
        DataProviderHelper.registerDataProviders(MindfulDarkness.MOD_ID, ModLanguageProvider::new);
    }
}
