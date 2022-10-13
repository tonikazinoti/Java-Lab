import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.*;

class ColorTest {
    @Test
    void whenNoArgsConstructor_ThenColorIsWhite() {
        final var maxRGBValueDecimal = 255;

        Color color = new Color();
        var actualRed = color.getRed();
        var actualGreen = color.getGreen();
        var actualBlue = color.getBlue();

        assertThat(actualRed).isEqualTo(maxRGBValueDecimal);
        assertThat(actualGreen).isEqualTo(maxRGBValueDecimal);
        assertThat(actualBlue).isEqualTo(maxRGBValueDecimal);
    }

    @ParameterizedTest
    @CsvSource({
            "25, 56, 300",
            "300, 98, 12",
            "45, -300, 34"
    })
    void givenInvalidRGBFormat_WhenConstructor_ThenThrows(
            int redColorDecimal, int greenColorDecimal, int blueColorDecimal
    ) {
        assertThatIllegalArgumentException().isThrownBy(() ->
                new Color(redColorDecimal, greenColorDecimal, blueColorDecimal)
        ).withMessage("RGB Format Not Valid");
    }

    @ParameterizedTest
    @CsvSource({
            "31, 240, 255, -14683905",
            "0, 0, 0, -16777216",
            "255, 255, 255, -1",
            "15, 90, 87, -15771049"
    })
    public void givenValidRGBFormat_WhenGetRBG_ThenCorrectBinaryRepresentation(
            int validRedColor, int validGreenColor, int validBlueColor,
            int expectedBinaryRepresentation
    ) {
        var color = new Color(validRedColor, validGreenColor, validBlueColor);
        var actualBinaryRepresentation = color.getRGB();

        assertThat(actualBinaryRepresentation).isEqualTo(expectedBinaryRepresentation);
    }

    @Test
    void givenValidHexFormat_WhenDecode_ThenCorrectDecodedRGBFormat() {
        var validHexColor = "0x1FF0FF";

        var expectedRedColor = 31;
        var expectedGreenColor = 240;
        var expectedBlueColor = 255;
        var expectedAlphaValue = 255;

        Color actualColor = Color.decode(validHexColor);

        assertThat(actualColor.getRed()).isEqualTo(expectedRedColor);
        assertThat(actualColor.getGreen()).isEqualTo(expectedGreenColor);
        assertThat(actualColor.getBlue()).isEqualTo(expectedBlueColor);
        assertThat(actualColor.getAlpha()).isEqualTo(expectedAlphaValue);
    }

    @Test
    void givenInvalidHexColor_WhenDecode_ThenThrows() {
        var invalidHexColor = "0xXXXXXX";

        assertThatThrownBy(() ->
                Color.decode(invalidHexColor)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Hex Color Invalid");
    }

    @ParameterizedTest
    @CsvSource({
            "31, 240, 255, 0.51, 0.88, 1",
            "0, 0, 0, 0, 0, 0",
            "255, 255, 255, 0, 0, 1",
            "15, 90, 87, 0.49, 0.83, 0.35",
            "127, 98, 13, 0.13, 0.9, 0.5"
    })
    void givenValidRGBFormat_WhenRGBToHSB_ThenCorrectDecodedHSBFormat(
            int validRedColor, int validGreenColor, int validBlueColor,
            float expectedHue, float expectedSaturation, float expectedBrightness
    ) {
        var HSBValues = new float[3];
        Color.RGBtoHSB(validRedColor, validGreenColor, validBlueColor, HSBValues);

        var actualHue = HSBValues[0];
        var actualSaturation = HSBValues[1];
        var actualBrightness = HSBValues[2];

        assertThat(actualHue).isEqualTo(expectedHue, within(0.01f));
        assertThat(actualSaturation).isEqualTo(expectedSaturation, within(0.01f));
        assertThat(actualBrightness).isEqualTo(expectedBrightness, within(0.01f));
    }

    @ParameterizedTest
    @CsvSource({
            "25, 56, 300",
            "300, 98, 12",
            "45, -300, 34"
    })
    void givenInvalidRGBFormat_WhenRBGToHSB_ThenThrows(
            int redColorDecimal, int greenColorDecimal, int blueColorDecimal
    ) {
        assertThatIllegalArgumentException().isThrownBy(() ->
                Color.RGBtoHSB(redColorDecimal, greenColorDecimal, blueColorDecimal, new float[3])
        ).withMessage("RGB Format Not Valid");
    }

    @ParameterizedTest
    @CsvSource({
            "31, 240, 255, 0.51, 1, 0.56",
            "0, 0, 0, 0, 0, 0",
            "255, 255, 255, 0, 0, 1",
            "15, 90, 87, 0.49, 0.71, 0.21"
    })
    void givenValidRGBFormat_WhenRGBToHSL_ThenCorrectDecodedHSLFormat(
            int validRedColor, int validGreenColor, int validBlueColor,
            float expectedHue, float expectedSaturation, float expectedLightness
    ) {
        var HSLValues = new float[3];
        Color.RGBtoHSL(validRedColor, validGreenColor, validBlueColor, HSLValues);

        var actualHue = HSLValues[0];
        var actualSaturation = HSLValues[1];
        var actualLightness = HSLValues[2];

        assertThat(actualHue).isEqualTo(expectedHue, within(0.01f));
        assertThat(actualSaturation).isEqualTo(expectedSaturation, within(0.01f));
        assertThat(actualLightness).isEqualTo(expectedLightness, within(0.01f));
    }

    @ParameterizedTest
    @CsvSource({
            "25, 56, 300",
            "300, 98, 12",
            "45, -300, 34"
    })
    void givenInvalidRGBFormat_WhenRBGToHSL_ThenThrows(
            int redColorDecimal, int greenColorDecimal, int blueColorDecimal
    ) {
        assertThatIllegalArgumentException().isThrownBy(() ->
                Color.RGBtoHSL(redColorDecimal, greenColorDecimal, blueColorDecimal, new float[3])
        ).withMessage("RGB Format Not Valid");
    }

    @ParameterizedTest
    @CsvSource({
            "31, 240, 255, 0.88, 0.06, 0, 0",
            "0, 0, 0, 0, 0, 0, 1",
            "255, 255, 255, 0, 0, 0, 0",
            "15, 90, 87, 0.83, 0, 0.03, 0.65"
    })
    void givenValidRGBFormat_WhenRGBToCMYK_ThenCorrectDecodedCMYKFormat(
            int validRedColor, int validGreenColor, int validBlueColor,
            float expectedCyan, float expectedMagenta, float expectedYellow, float expectedBlackKey
    ) {
        var CMYKValues = new float[4];
        Color.RGBtoCMYK(validRedColor, validGreenColor, validBlueColor, CMYKValues);

        var actualCyan = CMYKValues[0];
        var actualMagenta = CMYKValues[1];
        var actualYellow = CMYKValues[2];
        var actualBlackKey = CMYKValues[3];

        assertThat(actualCyan).isEqualTo(expectedCyan, within(0.01f));
        assertThat(actualMagenta).isEqualTo(expectedMagenta, within(0.01f));
        assertThat(actualYellow).isEqualTo(expectedYellow, within(0.01f));
        assertThat(actualBlackKey).isEqualTo(expectedBlackKey, within(0.01f));
    }

    @ParameterizedTest
    @CsvSource({
            "25, 56, 300",
            "300, 98, 12",
            "45, -300, 34"
    })
    void givenInvalidRGBFormat_WhenRBGToCMYK_ThenThrows(
            int redColorDecimal, int greenColorDecimal, int blueColorDecimal
    ) {
        assertThatIllegalArgumentException().isThrownBy(() ->
                Color.RGBtoCMYK(redColorDecimal, greenColorDecimal, blueColorDecimal, new float[3])
        ).withMessage("RGB Format Not Valid");
    }
}