package fuzs.mindfuldarkness.common.client.util;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum DarkeningAlgorithm implements StringRepresentable {
    GRAYSCALE_AND_HSP {
        @Override
        public int processPixel(int pixel, double multiplier) {
            int updatedPixel = RGBBrightnessUtil.multiplyColorComponentsBy(pixel, multiplier * multiplier, true);
            if (updatedPixel == pixel) {
                return RGBBrightnessUtil.darkenColorHSP(pixel, multiplier);
            } else {
                return updatedPixel;
            }
        }
    },
    HSP {
        @Override
        public int processPixel(int pixel, double multiplier) {
            return RGBBrightnessUtil.darkenColorHSP(pixel, multiplier);
        }
    },
    GRAYSCALE_AND_HSL {
        @Override
        public int processPixel(int pixel, double multiplier) {
            int updatedPixel = RGBBrightnessUtil.multiplyColorComponentsBy(pixel, multiplier * multiplier, true);
            if (updatedPixel == pixel) {
                return RGBBrightnessUtil.darkenColorHSL(pixel, multiplier);
            } else {
                return updatedPixel;
            }
        }
    },
    HSL {
        @Override
        public int processPixel(int pixel, double multiplier) {
            return RGBBrightnessUtil.darkenColorHSL(pixel, multiplier);
        }
    },
    GRAYSCALE_AND_LINEAR {
        @Override
        public int processPixel(int pixel, double multiplier) {
            int updatedPixel = RGBBrightnessUtil.multiplyColorComponentsBy(pixel, multiplier * multiplier, true);
            if (updatedPixel == pixel) {
                return RGBBrightnessUtil.darkenColor(pixel, 1.0 - multiplier);
            } else {
                return updatedPixel;
            }
        }
    },
    LINEAR {
        @Override
        public int processPixel(int pixel, double multiplier) {
            return RGBBrightnessUtil.darkenColor(pixel, 1.0 - multiplier);
        }
    };

    public abstract int processPixel(int pixel, double multiplier);

    public Component getComponent() {
        return Component.translatable("screen.daytime_switcher.algorithm." + this.getSerializedName());
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
