package com.javaweb.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class để xử lý Number operations
 * Cung cấp các phương thức tiện ích cho số
 */
public class NumberUtil {

    private NumberUtil() {
        // Prevent instantiation
    }

    /**
     * Kiểm tra Object có phải là number không
     *
     * @param obj Object cần kiểm tra
     * @return true nếu là number, false nếu không
     */
    public static boolean isNumber(Object obj) {
        return obj instanceof Number;
    }

    /**
     * Convert Object thành Integer
     *
     * @param obj Object cần convert
     * @return Integer value hoặc null
     */
    public static Integer toInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Convert Object thành Integer với default value
     *
     * @param obj Object cần convert
     * @param defaultValue Giá trị mặc định
     * @return Integer value hoặc defaultValue
     */
    public static Integer toInteger(Object obj, Integer defaultValue) {
        Integer result = toInteger(obj);
        return result != null ? result : defaultValue;
    }

    /**
     * Convert Object thành Long
     *
     * @param obj Object cần convert
     * @return Long value hoặc null
     */
    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Convert Object thành Long với default value
     *
     * @param obj Object cần convert
     * @param defaultValue Giá trị mặc định
     * @return Long value hoặc defaultValue
     */
    public static Long toLong(Object obj, Long defaultValue) {
        Long result = toLong(obj);
        return result != null ? result : defaultValue;
    }

    /**
     * Convert Object thành Double
     *
     * @param obj Object cần convert
     * @return Double value hoặc null
     */
    public static Double toDouble(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Convert Object thành Double với default value
     *
     * @param obj Object cần convert
     * @param defaultValue Giá trị mặc định
     * @return Double value hoặc defaultValue
     */
    public static Double toDouble(Object obj, Double defaultValue) {
        Double result = toDouble(obj);
        return result != null ? result : defaultValue;
    }

    /**
     * Convert Object thành BigDecimal
     *
     * @param obj Object cần convert
     * @return BigDecimal value hoặc null
     */
    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof Double) {
            return BigDecimal.valueOf((Double) obj);
        }
        if (obj instanceof Long) {
            return BigDecimal.valueOf((Long) obj);
        }
        try {
            return new BigDecimal(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Kiểm tra number có phải positive không (> 0)
     *
     * @param number Number cần kiểm tra
     * @return true nếu > 0, false nếu <= 0 hoặc null
     */
    public static boolean isPositive(Number number) {
        if (number == null) {
            return false;
        }
        return number.doubleValue() > 0;
    }

    /**
     * Kiểm tra number có phải negative không (< 0)
     *
     * @param number Number cần kiểm tra
     * @return true nếu < 0, false nếu >= 0 hoặc null
     */
    public static boolean isNegative(Number number) {
        if (number == null) {
            return false;
        }
        return number.doubleValue() < 0;
    }

    /**
     * Kiểm tra number có phải zero không
     *
     * @param number Number cần kiểm tra
     * @return true nếu == 0, false nếu != 0 hoặc null
     */
    public static boolean isZero(Number number) {
        if (number == null) {
            return false;
        }
        return number.doubleValue() == 0;
    }

    /**
     * Kiểm tra number có phải even không
     *
     * @param number Number cần kiểm tra
     * @return true nếu even, false nếu odd hoặc null
     */
    public static boolean isEven(Number number) {
        if (number == null) {
            return false;
        }
        return number.longValue() % 2 == 0;
    }

    /**
     * Kiểm tra number có phải odd không
     *
     * @param number Number cần kiểm tra
     * @return true nếu odd, false nếu even hoặc null
     */
    public static boolean isOdd(Number number) {
        if (number == null) {
            return false;
        }
        return number.longValue() % 2 != 0;
    }

    /**
     * Lấy absolute value
     *
     * @param number Number cần abs
     * @return Absolute value, hoặc 0 nếu null
     */
    public static double abs(Number number) {
        if (number == null) {
            return 0;
        }
        return Math.abs(number.doubleValue());
    }

    /**
     * Lấy min giữa hai numbers
     *
     * @param a Number thứ nhất
     * @param b Number thứ hai
     * @return Min value
     */
    public static double min(Number a, Number b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return b.doubleValue();
        }
        if (b == null) {
            return a.doubleValue();
        }
        return Math.min(a.doubleValue(), b.doubleValue());
    }

    /**
     * Lấy max giữa hai numbers
     *
     * @param a Number thứ nhất
     * @param b Number thứ hai
     * @return Max value
     */
    public static double max(Number a, Number b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return b.doubleValue();
        }
        if (b == null) {
            return a.doubleValue();
        }
        return Math.max(a.doubleValue(), b.doubleValue());
    }

    /**
     * Round Double đến số decimal places
     *
     * @param value Value cần round
     * @param places Số decimal places
     * @return Rounded value
     */
    public static double round(double value, int places) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return 0;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Ceil number (làm tròn lên)
     *
     * @param value Value cần ceil
     * @return Ceiling value
     */
    public static long ceil(Number value) {
        if (value == null) {
            return 0;
        }
        return Math.round(Math.ceil(value.doubleValue()));
    }

    /**
     * Floor number (làm tròn xuống)
     *
     * @param value Value cần floor
     * @return Floor value
     */
    public static long floor(Number value) {
        if (value == null) {
            return 0;
        }
        return Math.round(Math.floor(value.doubleValue()));
    }

    /**
     * Check value trong range
     *
     * @param value Value cần check
     * @param min Min value (inclusive)
     * @param max Max value (inclusive)
     * @return true nếu min <= value <= max
     */
    public static boolean inRange(Number value, Number min, Number max) {
        if (value == null || min == null || max == null) {
            return false;
        }
        double dValue = value.doubleValue();
        double dMin = min.doubleValue();
        double dMax = max.doubleValue();
        return dValue >= dMin && dValue <= dMax;
    }

    /**
     * Check value >= min
     *
     * @param value Value cần check
     * @param min Min value
     * @return true nếu value >= min
     */
    public static boolean isGreaterOrEqual(Number value, Number min) {
        if (value == null || min == null) {
            return false;
        }
        return value.doubleValue() >= min.doubleValue();
    }

    /**
     * Check value > min
     *
     * @param value Value cần check
     * @param min Min value
     * @return true nếu value > min
     */
    public static boolean isGreater(Number value, Number min) {
        if (value == null || min == null) {
            return false;
        }
        return value.doubleValue() > min.doubleValue();
    }

    /**
     * Check value <= max
     *
     * @param value Value cần check
     * @param max Max value
     * @return true nếu value <= max
     */
    public static boolean isLessOrEqual(Number value, Number max) {
        if (value == null || max == null) {
            return false;
        }
        return value.doubleValue() <= max.doubleValue();
    }

    /**
     * Check value < max
     *
     * @param value Value cần check
     * @param max Max value
     * @return true nếu value < max
     */
    public static boolean isLess(Number value, Number max) {
        if (value == null || max == null) {
            return false;
        }
        return value.doubleValue() < max.doubleValue();
    }

    /**
     * Format number with thousand separator
     *
     * @param number Number cần format
     * @return Formatted string (e.g., 1,000,000)
     */
    public static String formatWithComma(Number number) {
        if (number == null) {
            return "0";
        }
        return String.format("%,.0f", number.doubleValue());
    }

    /**
     * Format number với decimal places
     *
     * @param number Number cần format
     * @param places Số decimal places
     * @return Formatted string
     */
    public static String format(Number number, int places) {
        if (number == null) {
            return "0";
        }
        String format = "%,." + places + "f";
        return String.format(format, number.doubleValue());
    }

    /**
     * Convert number to percentage string
     *
     * @param number Number cần convert
     * @param places Số decimal places
     * @return Percentage string (e.g., "50.00%")
     */
    public static String toPercentage(Number number, int places) {
        if (number == null) {
            return "0%";
        }
        return String.format("%." + places + "f%%", number.doubleValue() * 100);
    }

    /**
     * Calculate percentage
     *
     * @param part Part value
     * @param total Total value
     * @return Percentage (0-100)
     */
    public static double calculatePercentage(Number part, Number total) {
        if (part == null || total == null || total.doubleValue() == 0) {
            return 0;
        }
        return (part.doubleValue() / total.doubleValue()) * 100;
    }

    /**
     * Sum array of numbers
     *
     * @param numbers Numbers cần sum
     * @return Total sum
     */
    public static double sum(Number... numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        double sum = 0;
        for (Number num : numbers) {
            if (num != null) {
                sum += num.doubleValue();
            }
        }
        return sum;
    }

    /**
     * Calculate average
     *
     * @param numbers Numbers cần average
     * @return Average value
     */
    public static double average(Number... numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        double sum = 0;
        int count = 0;
        for (Number num : numbers) {
            if (num != null) {
                sum += num.doubleValue();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    /**
     * Power calculation
     *
     * @param base Base number
     * @param exponent Exponent
     * @return Base ^ exponent
     */
    public static double power(Number base, Number exponent) {
        if (base == null || exponent == null) {
            return 0;
        }
        return Math.pow(base.doubleValue(), exponent.doubleValue());
    }

    /**
     * Square root
     *
     * @param number Number cần sqrt
     * @return Square root result
     */
    public static double sqrt(Number number) {
        if (number == null || number.doubleValue() < 0) {
            return 0;
        }
        return Math.sqrt(number.doubleValue());
    }
}

