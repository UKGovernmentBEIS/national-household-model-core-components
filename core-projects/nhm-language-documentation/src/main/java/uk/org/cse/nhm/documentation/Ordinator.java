package uk.org.cse.nhm.documentation;

public class Ordinator {

    private static final String[] digits
            = new String[]{
                "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth",
                "ninth", "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth",
                "sixteenth", "seventeenth", "eighteenth", "ninteenth", "twentieth"
            };

    public static String ordinal(final int index) {
        if (index < digits.length) {
            return digits[index];
        } else {
            return String.format("%d%s", index + 1, suffix(index));
        }
    }

    public static String suffix(final int value) {
        final int afterHundreds = value % 100;
        if (afterHundreds >= 10 && afterHundreds <= 20) {
            return "th";
        }
        final int tenRemainder = value % 10;
        switch (tenRemainder) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
