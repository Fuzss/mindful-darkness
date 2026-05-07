package fuzs.mindfuldarkness.common.client.gui.components;

import fuzs.mindfuldarkness.common.MindfulDarkness;
import fuzs.mindfuldarkness.common.client.gui.screens.PixelConfigScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;

public class FlatTextureButton extends Button {
    private static final WidgetSprites BUTTON_SPRITES = new WidgetSprites(MindfulDarkness.id("switcher/button"),
            MindfulDarkness.id("switcher/button_disabled"),
            MindfulDarkness.id("switcher/button_highlighted"));

    public FlatTextureButton(int x, int y, int width, int height, Component component, OnPress onPress) {
        super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
        this.setMessage(component);
    }

    @Override
    public Component getMessage() {
        return this.active && this.isHoveredOrFocused() ? this.message : this.inactiveMessage;
    }

    @Override
    public void setMessage(Component message) {
        this.message = ComponentUtils.mergeStyles(message, Style.EMPTY.withColor(ChatFormatting.YELLOW));
        this.inactiveMessage = ComponentUtils.mergeStyles(message, Style.EMPTY.withColor(0x404040));
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float tickDelta) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                BUTTON_SPRITES.get(this.active, this.isHoveredOrFocused()),
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight(),
                ARGB.white(this.alpha));
        Font font = Minecraft.getInstance().font;
        PixelConfigScreen.centeredText(guiGraphics,
                font,
                this.getMessage(),
                this.getX() + this.width / 2,
                this.getY() + (this.height - 8) / 2,
                ARGB.white(this.alpha),
                false);
    }
}
