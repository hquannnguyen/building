package com.javaweb.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class để xử lý String operations
 * Cung cấp các phương thức tiện ích cho xử lý chuỗi
 */
public class StringUtil {

    private StringUtil() {
        // Prevent instantiation
    }

    /**
     * Kiểm tra String null hoặc rỗng
     *
     * @param str String cần kiểm tra
     * @return true nếu null hoặc rỗng, false nếu có dữ liệu
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Kiểm tra String null, rỗng hoặc chỉ chứa whitespace
     *
     * @param str String cần kiểm tra
     * @return true nếu null/rỗng/whitespace, false nếu có dữ liệu
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Kiểm tra String không null và không rỗng
     *
     * @param str String cần kiểm tra
     * @return true nếu có dữ liệu, false nếu null hoặc rỗng
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Kiểm tra String không null, không rỗng, không whitespace
     *
     * @param str String cần kiểm tra
     * @return true nếu có dữ liệu, false nếu blank
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Trim whitespace từ String
     *
     * @param str String cần trim
     * @return Trimmed string hoặc empty string nếu null
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * Get default value nếu String empty/null
     *
     * @param str String cần check
     * @param defaultValue Default value
     * @return String hoặc defaultValue
     */
    public static String getOrDefault(String str, String defaultValue) {
        return isNotEmpty(str) ? str : defaultValue;
    }

    /**
     * Reverse string
     *
     * @param str String cần reverse
     * @return Reversed string
     */
    public static String reverse(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Convert to lowercase
     *
     * @param str String cần convert
     * @return Lowercase string
     */
    public static String toLowerCase(String str) {
        return isEmpty(str) ? "" : str.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Convert to uppercase
     *
     * @param str String cần convert
     * @return Uppercase string
     */
    public static String toUpperCase(String str) {
        return isEmpty(str) ? "" : str.toUpperCase(Locale.ENGLISH);
    }

    /**
     * Capitalize first letter
     *
     * @param str String cần capitalize
     * @return String với uppercase letter đầu tiên
     */
    public static String capitalize(String str) {
        if (isEmpty(str) || str.length() == 1) {
            return isEmpty(str) ? "" : str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Uncapitalize first letter
     *
     * @param str String cần uncapitalize
     * @return String với lowercase letter đầu tiên
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str) || str.length() == 1) {
            return isEmpty(str) ? "" : str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * Check String starts with prefix (case-sensitive)
     *
     * @param str String cần check
     * @param prefix Prefix
     * @return true nếu starts with prefix
     */
    public static boolean startsWith(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return false;
        }
        return str.startsWith(prefix);
    }

    /**
     * Check String starts with prefix (case-insensitive)
     *
     * @param str String cần check
     * @param prefix Prefix
     * @return true nếu starts with prefix (ignore case)
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return false;
        }
        return str.toLowerCase().startsWith(prefix.toLowerCase());
    }

    /**
     * Check String ends with suffix (case-sensitive)
     *
     * @param str String cần check
     * @param suffix Suffix
     * @return true nếu ends with suffix
     */
    public static boolean endsWith(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return false;
        }
        return str.endsWith(suffix);
    }

    /**
     * Check String ends with suffix (case-insensitive)
     *
     * @param str String cần check
     * @param suffix Suffix
     * @return true nếu ends with suffix (ignore case)
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return false;
        }
        return str.toLowerCase().endsWith(suffix.toLowerCase());
    }

    /**
     * Check String contains substring (case-sensitive)
     *
     * @param str String cần check
     * @param substring Substring
     * @return true nếu contains substring
     */
    public static boolean contains(String str, String substring) {
        if (isEmpty(str) || isEmpty(substring)) {
            return false;
        }
        return str.contains(substring);
    }

    /**
     * Check String contains substring (case-insensitive)
     *
     * @param str String cần check
     * @param substring Substring
     * @return true nếu contains substring (ignore case)
     */
    public static boolean containsIgnoreCase(String str, String substring) {
        if (isEmpty(str) || isEmpty(substring)) {
            return false;
        }
        return str.toLowerCase().contains(substring.toLowerCase());
    }

    /**
     * Replace first occurrence
     *
     * @param str Source string
     * @param oldStr String cần replace
     * @param newStr String thay thế
     * @return String mới
     */
    public static String replaceFirst(String str, String oldStr, String newStr) {
        if (isEmpty(str) || isEmpty(oldStr)) {
            return str;
        }
        return str.replaceFirst(Pattern.quote(oldStr), newStr != null ? newStr : "");
    }

    /**
     * Replace all occurrences
     *
     * @param str Source string
     * @param oldStr String cần replace
     * @param newStr String thay thế
     * @return String mới
     */
    public static String replaceAll(String str, String oldStr, String newStr) {
        if (isEmpty(str) || isEmpty(oldStr)) {
            return str;
        }
        return str.replace(oldStr, newStr != null ? newStr : "");
    }

    /**
     * Split string by delimiter
     *
     * @param str String cần split
     * @param delimiter Delimiter
     * @return Array of strings
     */
    public static String[] split(String str, String delimiter) {
        if (isEmpty(str) || isEmpty(delimiter)) {
            return new String[]{};
        }
        return str.split(Pattern.quote(delimiter));
    }

    /**
     * Join array thành string
     *
     * @param array Array cần join
     * @param delimiter Delimiter
     * @return Joined string
     */
    public static String join(Object[] array, String delimiter) {
        if (array == null || array.length == 0) {
            return "";
        }
        if (delimiter == null) {
            delimiter = "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(array[i] != null ? array[i] : "");
        }
        return sb.toString();
    }

    /**
     * Join List thành string
     *
     * @param list List cần join
     * @param delimiter Delimiter
     * @return Joined string
     */
    public static String join(List<?> list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return list.stream()
                .map(item -> item != null ? item.toString() : "")
                .collect(Collectors.joining(delimiter != null ? delimiter : ""));
    }

    /**
     * Substring từ index
     *
     * @param str String source
     * @param start Start index
     * @return Substring
     */
    public static String substring(String str, int start) {
        if (isEmpty(str) || start < 0 || start >= str.length()) {
            return "";
        }
        return str.substring(start);
    }

    /**
     * Substring từ start đến end index
     *
     * @param str String source
     * @param start Start index (inclusive)
     * @param end End index (exclusive)
     * @return Substring
     */
    public static String substring(String str, int start, int end) {
        if (isEmpty(str) || start < 0 || end > str.length() || start > end) {
            return "";
        }
        return str.substring(start, end);
    }

    /**
     * Get substring trước delimiter
     *
     * @param str String source
     * @param delimiter Delimiter
     * @return Substring trước delimiter
     */
    public static String substringBefore(String str, String delimiter) {
        if (isEmpty(str) || isEmpty(delimiter)) {
            return str;
        }
        int index = str.indexOf(delimiter);
        return index >= 0 ? str.substring(0, index) : str;
    }

    /**
     * Get substring sau delimiter
     *
     * @param str String source
     * @param delimiter Delimiter
     * @return Substring sau delimiter
     */
    public static String substringAfter(String str, String delimiter) {
        if (isEmpty(str) || isEmpty(delimiter)) {
            return str;
        }
        int index = str.indexOf(delimiter);
        return index >= 0 ? str.substring(index + delimiter.length()) : "";
    }

    /**
     * Get length của string
     *
     * @param str String
     * @return Length, 0 nếu null
     */
    public static int length(String str) {
        return isEmpty(str) ? 0 : str.length();
    }

    /**
     * Repeat string n lần
     *
     * @param str String cần repeat
     * @param count Số lần repeat
     * @return Repeated string
     */
    public static String repeat(String str, int count) {
        if (isEmpty(str) || count <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Pad left string với character
     *
     * @param str String cần pad
     * @param size Target size
     * @param padChar Character dùng để pad
     * @return Padded string
     */
    public static String padLeft(String str, int size, char padChar) {
        if (isEmpty(str)) {
            return repeat(String.valueOf(padChar), size);
        }
        if (str.length() >= size) {
            return str;
        }
        int padCount = size - str.length();
        return repeat(String.valueOf(padChar), padCount) + str;
    }

    /**
     * Pad right string với character
     *
     * @param str String cần pad
     * @param size Target size
     * @param padChar Character dùng để pad
     * @return Padded string
     */
    public static String padRight(String str, int size, char padChar) {
        if (isEmpty(str)) {
            return repeat(String.valueOf(padChar), size);
        }
        if (str.length() >= size) {
            return str;
        }
        int padCount = size - str.length();
        return str + repeat(String.valueOf(padChar), padCount);
    }

    /**
     * Remove characters từ string
     *
     * @param str String source
     * @param removeChars Characters cần remove
     * @return String đã remove characters
     */
    public static String removeChars(String str, String removeChars) {
        if (isEmpty(str) || isEmpty(removeChars)) {
            return str;
        }
        for (char c : removeChars.toCharArray()) {
            str = str.replace(String.valueOf(c), "");
        }
        return str;
    }

    /**
     * Remove duplicate characters
     *
     * @param str String source
     * @return String không có duplicate characters
     */
    public static String removeDuplicateChars(String str) {
        if (isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        java.util.Set<Character> seen = new java.util.HashSet<>();
        for (char c : str.toCharArray()) {
            if (seen.add(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Check String có chứa số không
     *
     * @param str String cần check
     * @return true nếu contains digit
     */
    public static boolean containsDigit(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return Pattern.compile("\\d").matcher(str).find();
    }

    /**
     * Check String có chứa chữ cái không
     *
     * @param str String cần check
     * @return true nếu contains letter
     */
    public static boolean containsLetter(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return Pattern.compile("[a-zA-Z]").matcher(str).find();
    }

    /**
     * Check String có chứa special character không
     *
     * @param str String cần check
     * @return true nếu contains special char
     */
    public static boolean containsSpecialChar(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return !str.matches("[a-zA-Z0-9\\s]*");
    }

    /**
     * Check String có phải numeric không
     *
     * @param str String cần check
     * @return true nếu toàn số
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check String có phải alpha-numeric không
     *
     * @param str String cần check
     * @return true nếu chỉ chứa chữ cái và số
     */
    public static boolean isAlphanumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches("[a-zA-Z0-9]+");
    }

    /**
     * Check String có phải email format không
     *
     * @param str String cần check
     * @return true nếu là email format
     */
    public static boolean isEmail(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(str).find();
    }

    /**
     * Check String có phải phone number không
     *
     * @param str String cần check
     * @return true nếu là phone format
     */
    public static boolean isPhoneNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches("^[\\d\\-\\+\\s\\(\\)]+$") && str.replaceAll("\\D", "").length() >= 10;
    }

    /**
     * Count occurrences của substring
     *
     * @param str String source
     * @param substring Substring
     * @return Count
     */
    public static int countOccurrences(String str, String substring) {
        if (isEmpty(str) || isEmpty(substring)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        return count;
    }

    /**
     * Remove leading zeros
     *
     * @param str String cần remove zeros
     * @return String không có leading zeros
     */
    public static String removeLeadingZeros(String str) {
        if (isEmpty(str)) {
            return "0";
        }
        return str.replaceAll("^0+", "") .isEmpty() ? "0" : str.replaceAll("^0+", "");
    }

    /**
     * Truncate string nếu vượt quá maxLength
     *
     * @param str String cần truncate
     * @param maxLength Max length
     * @return Truncated string
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || maxLength < 0) {
            return "";
        }
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }

    /**
     * Truncate string với suffix nếu vượt quá maxLength
     *
     * @param str String cần truncate
     * @param maxLength Max length (include suffix)
     * @param suffix Suffix (e.g., "...")
     * @return Truncated string
     */
    public static String truncateWithSuffix(String str, int maxLength, String suffix) {
        if (isEmpty(str) || maxLength < 0) {
            return "";
        }
        if (suffix == null) {
            suffix = "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - suffix.length()) + suffix;
    }

    /**
     * Convert to camelCase
     *
     * @param str String cần convert
     * @return camelCase string
     */
    public static String toCamelCase(String str) {
        if (isEmpty(str)) {
            return "";
        }
        String[] words = str.split("[\\s_-]+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                sb.append(words[i].toLowerCase());
            } else {
                sb.append(capitalize(words[i]));
            }
        }
        return sb.toString();
    }

    /**
     * Convert to snake_case
     *
     * @param str String cần convert
     * @return snake_case string
     */
    public static String toSnakeCase(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Convert to kebab-case
     *
     * @param str String cần convert
     * @return kebab-case string
     */
    public static String toKebabCase(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("([a-z0-9])([A-Z])", "$1-$2").toLowerCase();
    }
}

