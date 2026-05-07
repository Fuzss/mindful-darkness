package fuzs.mindfuldarkness.neoforge.client;

import fuzs.mindfuldarkness.common.MindfulDarkness;
import fuzs.mindfuldarkness.common.client.MindfulDarknessClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = MindfulDarkness.MOD_ID, dist = Dist.CLIENT)
public class MindfulDarknessNeoForgeClient {

    public MindfulDarknessNeoForgeClient() {
        ClientModConstructor.construct(MindfulDarkness.MOD_ID, MindfulDarknessClient::new);
    }
}
