package kirypto.grandsignshops;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public enum TextFormatStyle {
    NORMAL(new Style()),
    ERROR(makeErrorStyle()),
    WARNING(makeWarningStyle()),
    SUCCESS(makeSuccessStyle()),
    TEST(makeTestStyle())
    ;

    private final Style style;

    TextFormatStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    private static Style makeErrorStyle() {
        Style style = new Style();
        style.setColor(TextFormatting.RED);
        return style;
    }

    private static Style makeWarningStyle() {
        Style style = new Style();
        style.setColor(TextFormatting.YELLOW);
        return style;
    }

    private static Style makeSuccessStyle() {
        Style style = new Style();
        style.setColor(TextFormatting.GREEN);
        return style;
    }

    private static Style makeTestStyle() {
        Style style = new Style();
        style.setColor(TextFormatting.LIGHT_PURPLE);
        style.setStrikethrough(true);
        style.setItalic(true);
        return style;
    }
}
