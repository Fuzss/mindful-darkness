package fuzs.mindfuldarkness.common.client.gui.components;

import com.mojang.blaze3d.platform.cursor.CursorTypes;
import fuzs.mindfuldarkness.common.MindfulDarkness;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public abstract class FlatTextureSliderButton extends AbstractSliderButton {
    private static final Identifier SLIDER_BACKGROUND_SPRITE = MindfulDarkness.id("switcher/slider_background");
    private static final WidgetSprites SLIDER_HANDLE_SPRITES = new WidgetSprites(MindfulDarkness.id(
            "switcher/slider_handle"),
            MindfulDarkness.id("switcher/slider_handle_disabled"),
            MindfulDarkness.id("switcher/slider_handle_highlighted"));

    public FlatTextureSliderButton(int x, int y, int width, int height, Component component, double value) {
        super(x, y, width, height, CommonComponents.EMPTY, value);
    }

    @Override
    public Component getMessage() {
        return CommonComponents.EMPTY;
    }

    @Override
    public void setMessage(Component message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                SLIDER_BACKGROUND_SPRITE,
                this.getX() + 2,
                this.getY() + 2,
                this.width - 4,
                this.height - 4,
                ARGB.white(this.alpha));
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                SLIDER_HANDLE_SPRITES.get(this.active, this.isHoveredOrFocused()),
                this.getX() + (int) (this.value * (double) (this.width - 18)),
                this.getY(),
                18,
                18,
                ARGB.white(this.alpha));
        if (this.isHovered()) {
            guiGraphics.requestCursor(this.dragging ? CursorTypes.RESIZE_EW : CursorTypes.POINTING_HAND);
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.isSelection()) {
            this.canChangeValue = !this.canChangeValue;
            return true;
        } else {
            if (this.canChangeValue) {
                boolean isLeft = event.isLeft();
                boolean isRight = event.isRight();
                if (isLeft || isRight) {
                    float directionValue = isLeft ? -1.0F : 1.0F;
                    this.setValue(this.value + directionValue / (this.width - 18));
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public void setValueFromMouse(MouseButtonEvent event) {
        this.setValue((event.x() - (double) (this.getX() + 9)) / (double) (this.width - 18));
    }
}
