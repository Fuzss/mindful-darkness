package fuzs.mindfuldarkness.common.mixin.client;

import com.mojang.blaze3d.platform.NativeImage;
import fuzs.mindfuldarkness.common.client.packs.resources.ColorChangingResourceHandler;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(SpriteContents.class)
abstract class SpriteContentsMixin {

    @Inject(method = "<init>(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/resources/metadata/animation/FrameSize;Lcom/mojang/blaze3d/platform/NativeImage;Ljava/util/Optional;Ljava/util/List;Ljava/util/Optional;)V",
            at = @At("TAIL"))
    public void init(Identifier name, FrameSize frameSize, NativeImage image, Optional<AnimationMetadataSection> animationInfo, List<MetadataSectionType.WithValue<?>> additionalMetadata, Optional<TextureMetadataSection> textureInfo, CallbackInfo callback) {
        ColorChangingResourceHandler.INSTANCE.processResource(name, image);
    }
}
