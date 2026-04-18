package com.javaweb.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class để xử lý Map
 * Cung cấp các phương thức tiện ích cho Map operations
 */
public class MapUtil {

    private MapUtil() {
        // Prevent instantiation
    }

    /**
     * Kiểm tra Map có null hoặc rỗng không
     *
     * @param map Map cần kiểm tra
     * @return true nếu null hoặc rỗng, false nếu không
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Kiểm tra Map không null và không rỗng
     *
     * @param map Map cần kiểm tra
     * @return true nếu có dữ liệu, false nếu null hoặc rỗng
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Get value từ Map với default value nếu key không tồn tại hoặc value là null
     *
     * @param map Map source
     * @param key Key cần lấy
     * @param defaultValue Giá trị mặc định
     * @return Value hoặc defaultValue
     */
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        V value = map.get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Get value từ Map và convert sang String
     *
     * @param map Map source
     * @param key Key cần lấy
     * @return String value hoặc empty string nếu null
     */
    public static String getString(Map<?, ?> map, Object key) {
        if (isEmpty(map)) {
            return "";
        }
        Object value = map.get(key);
        return value != null ? String.valueOf(value) : "";
    }

    /**
     * Get value từ Map và convert sang Integer
     *
     * @param map Map source
     * @param key Key cần lấy
     * @return Integer value hoặc null nếu không thể convert
     */
    public static Integer getInteger(Map<?, ?> map, Object key) {
        if (isEmpty(map)) {
            return null;
        }
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        try {
            if (value instanceof Integer) {
                return (Integer) value;
            }
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Get value từ Map và convert sang Long
     *
     * @param map Map source
     * @param key Key cần lấy
     * @return Long value hoặc null nếu không thể convert
     */
    public static Long getLong(Map<?, ?> map, Object key) {
        if (isEmpty(map)) {
            return null;
        }
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        try {
            if (value instanceof Long) {
                return (Long) value;
            }
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            return Long.parseLong(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Get value từ Map và convert sang Double
     *
     * @param map Map source
     * @param key Key cần lấy
     * @return Double value hoặc null nếu không thể convert
     */
    public static Double getDouble(Map<?, ?> map, Object key) {
        if (isEmpty(map)) {
            return null;
        }
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        try {
            if (value instanceof Double) {
                return (Double) value;
            }
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            return Double.parseDouble(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Get value từ Map và convert sang Boolean
     *
     * @param map Map source
     * @param key Key cần lấy
     * @return Boolean value hoặc false nếu null
     */
    public static boolean getBoolean(Map<?, ?> map, Object key) {
        if (isEmpty(map)) {
            return false;
        }
        Object value = map.get(key);
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        String str = String.valueOf(value).toLowerCase();
        return "true".equals(str) || "1".equals(str) || "yes".equals(str);
    }

    /**
     * Filter Map theo predicate function
     *
     * @param map Map cần filter
     * @param predicate Điều kiện filter
     * @return Map mới chứa các entry thỏa điều kiện
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, java.util.function.BiPredicate<K, V> predicate) {
        if (isEmpty(map)) {
            return new HashMap<>();
        }
        return map.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Transform Map values bằng function
     *
     * @param map Map cần transform
     * @param function Function transform value
     * @return Map mới với values đã được transform
     */
    public static <K, V, R> Map<K, R> mapValues(Map<K, V> map, java.util.function.Function<V, R> function) {
        if (isEmpty(map)) {
            return new HashMap<>();
        }
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> function.apply(entry.getValue())
                ));
    }

    /**
     * Merge hai Map
     *
     * @param map1 Map thứ nhất
     * @param map2 Map thứ hai
     * @return Map mới chứa tất cả entries
     */
    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> result = new HashMap<>(map1 != null ? map1 : new HashMap<>());
        if (isNotEmpty(map2)) {
            result.putAll(map2);
        }
        return result;
    }

    /**
     * Invert Map (swap key và value)
     *
     * @param map Map cần invert
     * @return Map mới với key/value đảo ngược
     */
    public static <K, V> Map<V, K> invert(Map<K, V> map) {
        Map<V, K> result = new HashMap<>();
        if (isNotEmpty(map)) {
            map.forEach((key, value) -> result.put(value, key));
        }
        return result;
    }

    /**
     * Convert Map thành URL query string
     * Format: key1=value1&key2=value2
     *
     * @param map Map cần convert
     * @return URL query string
     */
    public static String toQueryString(Map<?, ?> map) {
        if (isEmpty(map)) {
            return "";
        }
        return map.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .map(e -> e.getKey() + "=" + urlEncode(String.valueOf(e.getValue())))
                .collect(Collectors.joining("&"));
    }

    /**
     * Remove null values từ Map
     *
     * @param map Map cần clean
     * @return Map mới không chứa null values
     */
    public static <K, V> Map<K, V> removeNullValues(Map<K, V> map) {
        if (isEmpty(map)) {
            return new HashMap<>();
        }
        return map.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Flatten nested Map (chỉ flatten 1 level)
     *
     * @param nestedMap Map lồng nhau
     * @return Map phẳng
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> flatten(Map<K, ?> nestedMap) {
        Map<K, V> result = new HashMap<>();
        if (isEmpty(nestedMap)) {
            return result;
        }

        nestedMap.forEach((key, value) -> {
            if (value instanceof Map) {
                ((Map<K, V>) value).forEach((nestedKey, nestedValue) -> {
                    result.put(nestedKey, nestedValue);
                });
            } else {
                result.put(key, (V) value);
            }
        });

        return result;
    }

    /**
     * Get keys từ Map as List
     *
     * @param map Map source
     * @return List của keys
     */
    public static <K> List<K> keys(Map<K, ?> map) {
        if (isEmpty(map)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(map.keySet());
    }

    /**
     * Get values từ Map as List
     *
     * @param map Map source
     * @return List của values
     */
    public static <V> List<V> values(Map<?, V> map) {
        if (isEmpty(map)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(map.values());
    }

    /**
     * Kiểm tra Map có chứa tất cả keys không
     *
     * @param map Map cần check
     * @param keys Keys cần kiểm tra
     * @return true nếu chứa tất cả, false nếu thiếu
     */
    @SafeVarargs
    public static boolean containsAllKeys(Map<?, ?> map, Object... keys) {
        if (isEmpty(map) || keys == null || keys.length == 0) {
            return isEmpty(map) && (keys == null || keys.length == 0);
        }
        for (Object key : keys) {
            if (!map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sort Map theo keys
     *
     * @param map Map cần sort
     * @return Sorted Map (LinkedHashMap)
     */
    public static <K extends Comparable<K>, V> Map<K, V> sortByKeys(Map<K, V> map) {
        if (isEmpty(map)) {
            return new LinkedHashMap<>();
        }
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Get Object từ Map với type casting
     * Hỗ trợ conversion tự động cho Long, Integer, Double, String, Boolean
     *
     * @param params Map chứa dữ liệu
     * @param key Key cần lấy
     * @param tClass Class type cần convert
     * @return Object đã convert hoặc null nếu không thể convert
     */
    public static <T> T getObject(Map<String, Object> params, String key, Class<T> tClass) {
        if (isEmpty(params) || key == null) {
            return null;
        }

        Object obj = params.get(key);
        if (obj == null || (obj instanceof String && ((String) obj).trim().isEmpty())) {
            return null;
        }

        try {
            // Nếu object đã là type cần thiết, return langsung
            if (tClass.isInstance(obj)) {
                return tClass.cast(obj);
            }

            // Convert sang type cần thiết
            String value = obj.toString().trim();

            if (Long.class.isAssignableFrom(tClass)) {
                return tClass.cast(Long.valueOf(value));
            } else if (Integer.class.isAssignableFrom(tClass)) {
                return tClass.cast(Integer.valueOf(value));
            } else if (Double.class.isAssignableFrom(tClass)) {
                return tClass.cast(Double.valueOf(value));
            } else if (Float.class.isAssignableFrom(tClass)) {
                return tClass.cast(Float.valueOf(value));
            } else if (Boolean.class.isAssignableFrom(tClass)) {
                boolean boolValue = "true".equalsIgnoreCase(value) || 
                                   "1".equals(value) || 
                                   "yes".equalsIgnoreCase(value);
                return tClass.cast(boolValue);
            } else if (String.class.isAssignableFrom(tClass)) {
                return tClass.cast(value);
            } else {
                // Nếu không match bất kỳ type nào, cố gắng cast trực tiếp
                return tClass.cast(obj);
            }
        } catch (NumberFormatException e) {
            // Log warning nếu muốn
            return null;
        } catch (ClassCastException e) {
            // Log warning nếu muốn
            return null;
        } catch (Exception e) {
            // Log warning nếu muốn
            return null;
        }
    }

    /**
     * Get Object từ Map với type casting và default value
     *
     * @param params Map chứa dữ liệu
     * @param key Key cần lấy
     * @param tClass Class type cần convert
     * @param defaultValue Giá trị mặc định nếu không thể get hoặc convert
     * @return Object đã convert hoặc defaultValue
     */
    public static <T> T getObject(Map<String, Object> params, String key, Class<T> tClass, T defaultValue) {
        T result = getObject(params, key, tClass);
        return result != null ? result : defaultValue;
    }

    /**
     * URL encode string
     *
     * @param value String cần encode
     * @return Encoded string
     */
    private static String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }
}

