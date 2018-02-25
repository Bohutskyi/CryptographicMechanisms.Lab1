package additional;

public class ColorText {

    public enum ColorBase {
        RED("RED"),
        GREEN("GREEN"),
        YELLOW("YELLOW"),
        BLUE("BLUE"),
        MAGENTA("MAGENTA"),
        CYAN("CYAN"),
        WHITE("WHITE"),
        BLACK("BLACK"),
        BRIGHT("BRIGHT");

        private final String text;

        private ColorBase(String text) {
            this.text = text;
        }
    }

    public static String colorText(String text, ColorBase color) {
        int x = color.equals(ColorBase.RED) ? 31 :
                color.equals(ColorBase.GREEN) ? 32 :
                        color.equals(ColorBase.YELLOW) ? 33 :
                                color.equals(ColorBase.BLUE) ? 34 :
                                        color.equals(ColorBase.MAGENTA) ? 35 :
                                                color.equals(ColorBase.CYAN) ? 36 :
                                                        color.equals(ColorBase.WHITE) ? 37 :
                                                                color.equals(ColorBase.BLACK) ? 30 :
                                                                        color.equals(ColorBase.BRIGHT) ? 1 : 0;

        return (char) 27 + "[" + x + "m" + text + (char) 27 + "[0m";
    }

}
