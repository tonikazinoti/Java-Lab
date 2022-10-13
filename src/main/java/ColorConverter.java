//import java.awt.Color;

public class ColorConverter {
    public static void main(String[] args) {
        var hexColor = "0x1FF0FF";

        var c = Color.decode(hexColor);

        var hsbCode = new float[3];

        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbCode);
        System.out.println("Boja u HEX formatu: 0x" +
                Integer.toHexString(c.getRGB() & 0x00FFFFFF));
        System.out.println("Boja u RGB formatu: " + c.getRed() + ", " +
                c.getGreen() + ", " + c.getBlue());
        System.out.println("Boja u HSB formatu: " + hsbCode[0] * 360 + " degrees, " +
                hsbCode[1] * 100 + "%, " + hsbCode[2] * 100 + "%");

        var hslCode = new float[3];
        Color.RGBtoHSL(c.getRed(), c.getGreen(), c.getBlue(), hslCode);
        System.out.printf("Boja u HSL formatu: %s %s %s\n",
                hslCode[0] * 360 + " degrees,",
                hslCode[1] * 100 + "%,",
                hslCode[2] * 100 + "%");

        var cmykCode = new float[4];
        Color.RGBtoCMYK(c.getRed(), c.getGreen(), c.getBlue(), cmykCode);
        System.out.printf("Boja u CMYK formatu: %s %s %s %s\n",
                cmykCode[0] * 100 + "%,",
                cmykCode[1] * 100 + "%,",
                cmykCode[2] * 100 + "%,",
                cmykCode[3] * 100 + "%");
    }
}
