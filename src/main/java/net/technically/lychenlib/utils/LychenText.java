package net.technically.lychenlib.utils;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class LychenText {
    public static Text applyColor(Text text, int color) {
        Style style = text.getStyle().withColor(color);
        return text.copy().setStyle(style);
    }

    public static Text removeItalics(Text text) {
        Style style = text.getStyle().withItalic(false);
        return text.copy().setStyle(style);
    }

    public static Text applyFormatting(Text text, Formatting... formatting) {
        Style style = text.getStyle().withFormatting(formatting);
        return text.copy().setStyle(style);
    }
}
