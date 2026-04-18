package com.javaweb.converter;

import com.javaweb.model.search.BuildingSearchBuilder;
import com.javaweb.utils.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converter class để chuyển đổi BuildingSearchBuilder sang các định dạng khác
 * Hỗ trợ chuyển đổi thành Map, Query String, và ngược lại
 */
public class BuildingSearchBuilderConverter {

    /**
     * Convert BuildingSearchBuilder thành Map
     * Hữu ích để lưu trữ hoặc truyền dữ liệu
     * 
     * @param searchBuilder BuildingSearchBuilder instance
     * @return Map chứa các tiêu chí tìm kiếm (chỉ các giá trị không null)
     */
    public static Map<String, Object> toMap(BuildingSearchBuilder searchBuilder) {
        Map<String, Object> map = new HashMap<>();
        
        if (searchBuilder.getName() != null) {
            map.put("name", searchBuilder.getName());
        }
        if (searchBuilder.getDistrictId() != null) {
            map.put("districtId", searchBuilder.getDistrictId());
        }
        if (searchBuilder.getTypeCode() != null) {
            map.put("typeCode", searchBuilder.getTypeCode());
        }
        if (searchBuilder.getAreaFrom() != null) {
            map.put("areaFrom", searchBuilder.getAreaFrom());
        }
        if (searchBuilder.getAreaTo() != null) {
            map.put("areaTo", searchBuilder.getAreaTo());
        }
        if (searchBuilder.getRentPriceFrom() != null) {
            map.put("rentPriceFrom", searchBuilder.getRentPriceFrom());
        }
        if (searchBuilder.getRentPriceTo() != null) {
            map.put("rentPriceTo", searchBuilder.getRentPriceTo());
        }
        
        return map;
    }

    /**
     * Convert Map thành BuildingSearchBuilder
     * 
     * @param map Map chứa các tiêu chí tìm kiếm
     * @return BuildingSearchBuilder instance
     */
    public static BuildingSearchBuilder fromMap(Map<String, Object> map) {
        BuildingSearchBuilder builder = BuildingSearchBuilder.builder();
        
        if (map.containsKey("name") && map.get("name") != null) {
            builder.name((String) map.get("name"));
        }
        if (map.containsKey("districtId") && map.get("districtId") != null) {
            builder.districtId(((Number) map.get("districtId")).longValue());
        }
        if (map.containsKey("typeCode") && map.get("typeCode") != null) {
            builder.typeCode((String) map.get("typeCode"));
        }
        if (map.containsKey("areaFrom") && map.get("areaFrom") != null) {
            builder.areaFrom(((Number) map.get("areaFrom")).intValue());
        }
        if (map.containsKey("areaTo") && map.get("areaTo") != null) {
            builder.areaTo(((Number) map.get("areaTo")).intValue());
        }
        if (map.containsKey("rentPriceFrom") && map.get("rentPriceFrom") != null) {
            builder.rentPriceFrom(((Number) map.get("rentPriceFrom")).intValue());
        }
        if (map.containsKey("rentPriceTo") && map.get("rentPriceTo") != null) {
            builder.rentPriceTo(((Number) map.get("rentPriceTo")).intValue());
        }
        
        return builder.build();
    }

    /**
     * Convert BuildingSearchBuilder thành Query String
     * Format: name=ABC&districtId=1&typeCode=nguyen-can&areaFrom=350&areaTo=500
     * 
     * @param searchBuilder BuildingSearchBuilder instance
     * @return Query String (không có dấu '?' ở đầu)
     */
    public static String toQueryString(BuildingSearchBuilder searchBuilder) {
        StringBuilder queryString = new StringBuilder();
        boolean isFirst = true;
        
        if (searchBuilder.getName() != null && !searchBuilder.getName().isEmpty()) {
            if (!isFirst) queryString.append("&");
            queryString.append("name=").append(urlEncode(searchBuilder.getName()));
            isFirst = false;
        }
        
        if (searchBuilder.getDistrictId() != null) {
            if (!isFirst) queryString.append("&");
            queryString.append("districtid=").append(searchBuilder.getDistrictId());
            isFirst = false;
        }
        
        if (searchBuilder.getTypeCode() != null && !searchBuilder.getTypeCode().isEmpty()) {
            if (!isFirst) queryString.append("&");
            queryString.append("typecode=").append(urlEncode(searchBuilder.getTypeCode()));
            isFirst = false;
        }
        
        if (searchBuilder.getAreaFrom() != null) {
            if (!isFirst) queryString.append("&");
            queryString.append("areaFrom=").append(searchBuilder.getAreaFrom());
            isFirst = false;
        }
        
        if (searchBuilder.getAreaTo() != null) {
            if (!isFirst) queryString.append("&");
            queryString.append("areaTo=").append(searchBuilder.getAreaTo());
            isFirst = false;
        }
        
        if (searchBuilder.getRentPriceFrom() != null) {
            if (!isFirst) queryString.append("&");
            queryString.append("rentPriceFrom=").append(searchBuilder.getRentPriceFrom());
            isFirst = false;
        }
        
        if (searchBuilder.getRentPriceTo() != null) {
            if (!isFirst) queryString.append("&");
            queryString.append("rentPriceTo=").append(searchBuilder.getRentPriceTo());
        }
        
        return queryString.toString();
    }

    /**
     * Convert Query String thành BuildingSearchBuilder
     * Xử lý các trường hợp null hoặc empty
     * 
     * @param queryString Query String (dạng: name=ABC&districtId=1)
     * @return BuildingSearchBuilder instance
     */
    public static BuildingSearchBuilder fromQueryString(String queryString) {
        BuildingSearchBuilder builder = BuildingSearchBuilder.builder();
        
        if (queryString == null || queryString.isEmpty()) {
            return builder.build();
        }
        
        String[] params = queryString.split("&");
        for (String param : params) {
            if (param.isEmpty()) continue;
            
            String[] keyValue = param.split("=", 2);
            if (keyValue.length != 2) continue;
            
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            
            if (value.isEmpty() || "null".equalsIgnoreCase(value)) {
                continue;
            }
            
            try {
                switch (key.toLowerCase()) {
                    case "name":
                        builder.name(urlDecode(value));
                        break;
                    case "districtid":
                        builder.districtId(Long.parseLong(value));
                        break;
                    case "typecode":
                        builder.typeCode(urlDecode(value));
                        break;
                    case "areafrom":
                        builder.areaFrom(Integer.parseInt(value));
                        break;
                    case "areato":
                        builder.areaTo(Integer.parseInt(value));
                        break;
                    case "rentpricefrom":
                        builder.rentPriceFrom(Integer.parseInt(value));
                        break;
                    case "rentpriceto":
                        builder.rentPriceTo(Integer.parseInt(value));
                        break;
                }
            } catch (NumberFormatException e) {
                // Skip invalid number formats
                continue;
            }
        }
        
        return builder.build();
    }

    /**
     * Convert BuildingSearchBuilder thành JSON-like String
     * Format: {name: "ABC", districtId: 1, typeCode: "nguyen-can"}
     * 
     * @param searchBuilder BuildingSearchBuilder instance
     * @return JSON-like String
     */
    public static String toJsonString(BuildingSearchBuilder searchBuilder) {
        StringBuilder json = new StringBuilder("{");
        boolean isFirst = true;
        
        if (searchBuilder.getName() != null) {
            if (!isFirst) json.append(", ");
            json.append("\"name\": \"").append(escapeJson(searchBuilder.getName())).append("\"");
            isFirst = false;
        }
        
        if (searchBuilder.getDistrictId() != null) {
            if (!isFirst) json.append(", ");
            json.append("\"districtId\": ").append(searchBuilder.getDistrictId());
            isFirst = false;
        }
        
        if (searchBuilder.getTypeCode() != null) {
            if (!isFirst) json.append(", ");
            json.append("\"typeCode\": \"").append(escapeJson(searchBuilder.getTypeCode())).append("\"");
            isFirst = false;
        }
        
        if (searchBuilder.getAreaFrom() != null) {
            if (!isFirst) json.append(", ");
            json.append("\"areaFrom\": ").append(searchBuilder.getAreaFrom());
            isFirst = false;
        }
        
        if (searchBuilder.getAreaTo() != null) {
            if (!isFirst) json.append(", ");
            json.append("\"areaTo\": ").append(searchBuilder.getAreaTo());
            isFirst = false;
        }
        
        if (searchBuilder.getRentPriceFrom() != null) {
            if (!isFirst) json.append(", ");
            json.append("\"rentPriceFrom\": ").append(searchBuilder.getRentPriceFrom());
            isFirst = false;
        }
        
        if (searchBuilder.getRentPriceTo() != null) {
            if (!isFirst) json.append(", ");
            json.append("\"rentPriceTo\": ").append(searchBuilder.getRentPriceTo());
        }
        
        json.append("}");
        return json.toString();
    }

    /**
     * Encode URL String
     * Thay thế các ký tự đặc biệt bằng %XX
     * 
     * @param value Chuỗi cần encode
     * @return Chuỗi đã được encode
     */
    private static String urlEncode(String value) {
        if (value == null) return "";
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * Decode URL String
     * Chuyển %XX thành ký tự tương ứng
     * 
     * @param value Chuỗi cần decode
     * @return Chuỗi đã được decode
     */
    private static String urlDecode(String value) {
        if (value == null) return "";
        try {
            return java.net.URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * Escape JSON special characters
     * 
     * @param value Chuỗi cần escape
     * @return Chuỗi đã được escape
     */
    private static String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }

    /**
     * Clone BuildingSearchBuilder
     * Tạo một bản sao độc lập
     * 
     * @param searchBuilder BuildingSearchBuilder instance cần clone
     * @return BuildingSearchBuilder mới với các giá trị giống nhau
     */
    public static BuildingSearchBuilder clone(BuildingSearchBuilder searchBuilder) {
        return BuildingSearchBuilder.builder()
                .name(searchBuilder.getName())
                .districtId(searchBuilder.getDistrictId())
                .typeCode(searchBuilder.getTypeCode())
                .areaFrom(searchBuilder.getAreaFrom())
                .areaTo(searchBuilder.getAreaTo())
                .rentPriceFrom(searchBuilder.getRentPriceFrom())
                .rentPriceTo(searchBuilder.getRentPriceTo())
                .build();
    }

    /**
     * Kiểm tra xem hai BuildingSearchBuilder có giống nhau không
     * 
     * @param search1 BuildingSearchBuilder đầu tiên
     * @param search2 BuildingSearchBuilder thứ hai
     * @return true nếu hai object có cùng các giá trị, false nếu khác
     */
    public static boolean isEqual(BuildingSearchBuilder search1, BuildingSearchBuilder search2) {
        if (search1 == null && search2 == null) return true;
        if (search1 == null || search2 == null) return false;
        
        return compareStrings(search1.getName(), search2.getName()) &&
               compareLongs(search1.getDistrictId(), search2.getDistrictId()) &&
               compareStrings(search1.getTypeCode(), search2.getTypeCode()) &&
               compareIntegers(search1.getAreaFrom(), search2.getAreaFrom()) &&
               compareIntegers(search1.getAreaTo(), search2.getAreaTo()) &&
               compareIntegers(search1.getRentPriceFrom(), search2.getRentPriceFrom()) &&
               compareIntegers(search1.getRentPriceTo(), search2.getRentPriceTo());
    }

    private static boolean compareStrings(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null) return false;
        return s1.equals(s2);
    }

    private static boolean compareLongs(Long l1, Long l2) {
        if (l1 == null && l2 == null) return true;
        if (l1 == null || l2 == null) return false;
        return l1.equals(l2);
    }

    private static boolean compareIntegers(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null || i2 == null) return false;
        return i1.equals(i2);
    }

    /**
     * Convert Map<String, Object> sang BuildingSearchBuilder
     * Sử dụng MapUtil.getObject() để type-safe conversion
     *
     * @param params Map chứa các parameters từ request
     * @param typeCodeList List các type codes (ví dụ: ["nguyen-can", "can-ho"])
     * @return BuildingSearchBuilder với các giá trị đã được set
     * @throws IllegalArgumentException nếu params là null hoặc rỗng
     */
    public static BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCodeList) {
        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("Parameters map không được null hoặc rỗng");
        }

        try {
            BuildingSearchBuilder builder = BuildingSearchBuilder.builder();

            // String fields
            String name = MapUtil.getObject(params, "name", String.class);
            if (name != null) {
                builder.name(name);
            }

            String typeCode = MapUtil.getObject(params, "typecode", String.class);
            if (typeCode != null) {
                builder.typeCode(typeCode);
            }

            // Long fields
            Long districtId = MapUtil.getObject(params, "districtid", Long.class);
            if (districtId != null) {
                builder.districtId(districtId);
            }

            Long areaFrom = MapUtil.getObject(params, "areafrom", Long.class);
            if (areaFrom != null && areaFrom > 0) {
                builder.areaFrom(areaFrom.intValue());
            }

            Long areaTo = MapUtil.getObject(params, "areato", Long.class);
            if (areaTo != null && areaTo > 0) {
                builder.areaTo(areaTo.intValue());
            }

            Long rentPriceFrom = MapUtil.getObject(params, "rentpricefrom", Long.class);
            if (rentPriceFrom != null && rentPriceFrom > 0) {
                builder.rentPriceFrom(rentPriceFrom.intValue());
            }

            Long rentPriceTo = MapUtil.getObject(params, "rentpriceto", Long.class);
            if (rentPriceTo != null && rentPriceTo > 0) {
                builder.rentPriceTo(rentPriceTo.intValue());
            }

            // Set typeCodeList if provided
            if (typeCodeList != null && !typeCodeList.isEmpty()) {
                // Note: BuildingSearchBuilder may not have a method for this
                // This is a placeholder in case it's needed
            }

            return builder.build();

        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid parameter type: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error converting parameters to BuildingSearchBuilder: " + e.getMessage(), e);
        }
    }

    /**
     * Convert Map<String, Object> sang BuildingSearchBuilder (overloaded version)
     * Gunakan khi không có typeCodeList
     *
     * @param params Map chứa các parameters từ request
     * @return BuildingSearchBuilder với các giá trị đã được set
     * @throws IllegalArgumentException nếu params là null hoặc rỗng
     */
    public static BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params) {
        return toBuildingSearchBuilder(params, null);
    }
}

