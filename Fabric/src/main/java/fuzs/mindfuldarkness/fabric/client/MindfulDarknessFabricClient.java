package fuzs.mindfuldarkness.fabric.client;

import fuzs.mindfuldarkness.common.MindfulDarkness;
import fuzs.mindfuldarkness.common.client.MindfulDarknessClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class MindfulDarknessFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(MindfulDarkness.MOD_ID, MindfulDarknessClient::new);
    }
}
