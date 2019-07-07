package kirypto.grandsignshops;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public enum TextFormatStyle {
    NORMAL(new Style()),
    RED(makeRedStyle());


    private final Style style;

    TextFormatStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    private static Style makeRedStyle() {
        Style style = new Style();
        style.setColor(TextFormatting.RED);
        return style;
    }
}
