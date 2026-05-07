package fuzs.mindfuldarkness.common.mixin.client;

import fuzs.mindfuldarkness.common.client.packs.resources.ColorChangingResourceHandler;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(MultiPackResourceManager.class)
abstract class MultiPackResourceManagerMixin {
    @Nullable
    @Unique
    private ColorChangingResourceHandler mindfuldarkness$colorChangingHandler;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(PackType type, List<PackResources> packs, CallbackInfo callback) {
        if (type == PackType.CLIENT_RESOURCES) {
            this.mindfuldarkness$colorChangingHandler = ColorChangingResourceHandler.INSTANCE;
            this.mindfuldarkness$colorChangingHandler.clear();
        }
    }

    @Inject(method = "getResource", at = @At("RETURN"), cancellable = true)
    public void getResource(Identifier location, CallbackInfoReturnable<Optional<Resource>> callback) {
        if (this.mindfuldarkness$colorChangingHandler != null) {
            Optional<Resource> resource = this.mindfuldarkness$colorChangingHandler.getResource(location,
                    callback.getReturnValue());
            if (resource.isPresent()) callback.setReturnValue(resource);
        }
    }
}
