public class Color {
    private static final int MAX_RGB_VALUE_DECIMAL = 255;
    private final int red;
    private final int green;
    private final int blue;
    private final int alpha;

    Color() {
        this.red = MAX_RGB_VALUE_DECIMAL;
        this.green = MAX_RGB_VALUE_DECIMAL;
        this.blue = MAX_RGB_VALUE_DECIMAL;
        this.alpha = MAX_RGB_VALUE_DECIMAL;
    }

    Color(int red, int green, int blue, int alpha) throws IllegalArgumentException {
        if (isRGBFormatNotValid(red, green, blue)) {
            throw new IllegalArgumentException("RGB Format Not Valid");
        }

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    Color(int red, int green, int blue) {
        this(red, green, blue, MAX_RGB_VALUE_DECIMAL);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getRGB() {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static Color decode(String hexColor) throws IllegalArgumentException {
        if (!isHexColorValid(hexColor)) {
            throw new IllegalArgumentException("Hex Color Invalid");
        }

        var redHex = hexColor.substring(2, 4);
        var greenHex = hexColor.substring(4, 6);
        var blueHex = hexColor.substring(6, 8);

        var redDecimal = Integer.parseInt(redHex, 16);
        var greenDecimal = Integer.parseInt(greenHex, 16);
        var blueDecimal = Integer.parseInt(blueHex, 16);

        return new Color(redDecimal, greenDecimal, blueDecimal);
    }

    private static boolean isHexColorValid(String hexColor) {
        String HEX_COLOR_REGEX_PATTERN = "0x[0-9A-F]{6}";
        return hexColor.matches(HEX_COLOR_REGEX_PATTERN);
    }

    public static void RGBtoHSB(int r, int g, int b, float[] hsbValues) throws IllegalArgumentException {
        if (isRGBFormatNotValid(r, g, b)) {
            throw new IllegalArgumentException("RGB Format Not Valid");
        }

        final var hueIndex = 0;
        final var saturationIndex = 1;
        final var brightnessIndex = 2;

        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        var red = r / 255f;
        var green = g / 255f;
        var blue = b / 255f;

        float maxColor = Math.max(red, Math.max(green, blue));
        float minColor = Math.min(red, Math.min(green, blue));
        float difference = maxColor - minColor;

        float hue;
        if (maxColor == minColor) {
            hue = 0;
        } else if (maxColor == red) {
            hue = ((60 * ((green - blue) / difference) + 360) % 360) / 360;
        } else if (maxColor == green) {
            hue = ((60 * ((blue - red) / difference) + 120) % 360) / 360;
        } else { // maxColor == blue
            hue = ((60 * ((red - green) / difference) + 240) % 360) / 360;
        }

        float saturation;
        if (maxColor == 0) {
            saturation = 0;
        } else {
            saturation = (difference / maxColor);
        }

        hsbValues[hueIndex] = hue;
        hsbValues[saturationIndex] = saturation;
        hsbValues[brightnessIndex] = maxColor;
    }

    private static boolean isRGBFormatNotValid(int r, int g, int b) {
        return r < 0 || r > 255 ||
                g < 0 || g > 255 ||
                b < 0 || b > 255;
    }

    public static void RGBtoHSL(int r, int g, int b, float[] hslValues) throws IllegalArgumentException {
        if (isRGBFormatNotValid(r, g, b)) {
            throw new IllegalArgumentException("RGB Format Not Valid");
        }

        final var hueIndex = 0;
        final var saturationIndex = 1;
        final var lightnessIndex = 2;

        var hsbValues = new float[3];
        Color.RGBtoHSB(r, g, b, hsbValues);

        var hue = hsbValues[0];
        var saturationHSB = hsbValues[1];
        var brightnessHSB = hsbValues[2];

        var lightness = (2 - saturationHSB) * brightnessHSB / 2;

        float saturation;
        if (lightness != 0) {
            saturation = calculateSaturationLightnessNotZero(lightness, saturationHSB, brightnessHSB);
        } else {
            saturation = saturationHSB;
        }

        hslValues[hueIndex] = hue;
        hslValues[saturationIndex] = saturation;
        hslValues[lightnessIndex] = lightness;
    }

    private static float calculateSaturationLightnessNotZero(
            float lightness, float saturationHSB, float brightnessHSB
    ) {
        if (lightness == 1) {
            return 0;
        } else if (lightness < 0.5) {
            return saturationHSB * brightnessHSB / (lightness * 2);
        } else {
            return saturationHSB * brightnessHSB / (2 - lightness * 2);
        }
    }

    public static void RGBtoCMYK(int r, int g, int b, float[] cmykValues) throws IllegalArgumentException {
        if (isRGBFormatNotValid(r, g, b)) {
            throw new IllegalArgumentException("RGB Format Not Valid");
        }

        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        var red = r / 255f;
        var green = g / 255f;
        var blue = b / 255f;

        var blackKey = 1 - Math.max(red, Math.max(green, blue));
        float cyan, magenta, yellow;
        if (blackKey != 1f) {
            cyan = (1 - red - blackKey) / (1 - blackKey);
            magenta = (1 - green - blackKey) / (1 - blackKey);
            yellow = (1 - blue - blackKey) / (1 - blackKey);
        } else {
            cyan = magenta = yellow = 0;
        }

        cmykValues[0] = cyan;
        cmykValues[1] = magenta;
        cmykValues[2] = yellow;
        cmykValues[3] = blackKey;
    }
}
