package us.empiremc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)(s|m|h|d|mo|y)");

    public static long parseTime(String input) {
        Matcher matcher = TIME_PATTERN.matcher(input);
        if (!matcher.matches()) return -1;

        long value = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        return switch (unit) {
            case "s" -> value * 1000;
            case "m" -> value * 60 * 1000;
            case "h" -> value * 60 * 60 * 1000;
            case "d" -> value * 24 * 60 * 60 * 1000;
            case "mo" -> value * 30 * 24 * 60 * 60 * 1000; // 1 mes = 30 días
            case "y" -> value * 365 * 24 * 60 * 60 * 1000; // 1 año = 365 días
            default -> -1;
        };
    }
}
