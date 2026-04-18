package com.javaweb.repository.impl;

import com.javaweb.model.entity.BuildingEntity;
import com.javaweb.model.search.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	@Value("${spring.datasource.url}") 
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public List<BuildingEntity> findAll(String name, Long districtId, String typeCode, Integer areaFrom, Integer areaTo, Integer rentPriceFrom, Integer rentPriceTo) {
        StringBuilder sql = new StringBuilder("SELECT * FROM building b WHERE 1 = 1");
        List<Object> parameters = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND b.name LIKE ?");
            parameters.add("%" + name + "%");
        }
        if (districtId != null) {
            sql.append(" AND b.districtid = ?");
            parameters.add(districtId);
        }
        if (typeCode != null && !typeCode.isEmpty()) {
            sql.append(" AND b.typecode = ?");
            parameters.add(typeCode);
        }
        if (areaFrom != null) {
            sql.append(" AND b.floorarea >= ?");
            parameters.add(areaFrom);
        }
        if (areaTo != null) {
            sql.append(" AND b.floorarea <= ?");
            parameters.add(areaTo);
        }
        if (rentPriceFrom != null) {
            sql.append(" AND b.rentprice >= ?");
            parameters.add(rentPriceFrom);
        }
        if (rentPriceTo != null) {
            sql.append(" AND b.rentprice <= ?");
            parameters.add(rentPriceTo);
        }

        List<BuildingEntity> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BuildingEntity building = new BuildingEntity();
                    building.setId(rs.getLong("id"));
                    building.setName(rs.getString("name"));
                    building.setStreet(rs.getString("street"));
                    building.setWard(rs.getString("ward"));
                    building.setDistrictId(rs.getLong("districtid"));
                    building.setStructure(rs.getString("structure"));
                    building.setNumberOfBasement(rs.getInt("numberofbasement"));
                    building.setFloorArea(rs.getInt("floorarea"));
                    building.setDirection(rs.getString("direction"));
                    building.setLevel(rs.getString("level"));
                    building.setRentPrice(rs.getInt("rentprice"));
                    building.setRentPriceDescription(rs.getString("rentpricedescription"));
                    building.setManagerName(rs.getString("managername"));
                    building.setManagerPhoneNumber(rs.getString("managerphonenumber"));
                    building.setCreatedDate(rs.getTimestamp("createddate"));
                    building.setModifiedDate(rs.getTimestamp("modifieddate"));
                    building.setCreatedBy(rs.getString("createdby"));
                    building.setModifiedBy(rs.getString("modifiedby"));
                    
                    // Xử lý các cột tùy chọn (có thể không tồn tại trong database)
                    try {
                        building.setBrokerageFee(rs.getDouble("brokeragefee"));
                    } catch (SQLException e) {
                        // Cột brokeragefee không tồn tại, bỏ qua
                    }
                    
                    try {
                        building.setEmptyArea(rs.getInt("emptyarea"));
                    } catch (SQLException e) {
                        // Cột emptyarea không tồn tại, bỏ qua
                    }
                    
                    result.add(building);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying building: " + e.getMessage());
        }
        return result;
    }

    /**
     * Xây dựng động câu truy vấn SQL dựa trên tiêu chí tìm kiếm BuildingSearchBuilder
     * 
     * @param buildingSearchBuilder tiêu chí tìm kiếm
     * @param where StringBuilder để xây dựng WHERE clause
     */
    public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        for (java.lang.reflect.Field field : buildingSearchBuilder.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            
            try {
                Object value = field.get(buildingSearchBuilder);
                
                if (value == null || StringUtil.isEmpty(String.valueOf(value))) {
                    continue;
                }
                
                // Xử lý từng trường dữ liệu
                if (fieldName.equals("name")) {
                    String nameValue = String.valueOf(value);
                    if (StringUtil.isNotEmpty(nameValue)) {
                        where.append(" AND b.name LIKE '%").append(nameValue).append("%'");
                    }
                } 
                else if (fieldName.equals("typeCode")) {
                    String typeCodeValue = String.valueOf(value);
                    if (StringUtil.isNotEmpty(typeCodeValue)) {
                        where.append(" AND b.typecode = '").append(typeCodeValue).append("'");
                    }
                }
                else if (fieldName.equals("areaFrom")) {
                    Integer areaFromValue = NumberUtil.toInteger(value);
                    if (areaFromValue != null) {
                        where.append(" AND b.floorarea >= ").append(areaFromValue);
                    }
                }
                else if (fieldName.equals("areaTo")) {
                    Integer areaToValue = NumberUtil.toInteger(value);
                    if (areaToValue != null) {
                        where.append(" AND b.floorarea <= ").append(areaToValue);
                    }
                }
                else if (fieldName.equals("rentPriceFrom")) {
                    Integer rentPriceFromValue = NumberUtil.toInteger(value);
                    if (rentPriceFromValue != null) {
                        where.append(" AND b.rentprice >= ").append(rentPriceFromValue);
                    }
                }
                else if (fieldName.equals("rentPriceTo")) {
                    Integer rentPriceToValue = NumberUtil.toInteger(value);
                    if (rentPriceToValue != null) {
                        where.append(" AND b.rentprice <= ").append(rentPriceToValue);
                    }
                }
                else if (fieldName.equals("districtId")) {
                    try {
                        Long districtIdValue = field.getType().equals(Long.class) ? (Long) value : 
                                              Long.valueOf(String.valueOf(value));
                        if (districtIdValue > 0) {
                            where.append(" AND b.districtid = ").append(districtIdValue);
                        }
                    } catch (NumberFormatException ex) {
                        // Bỏ qua nếu không thể convert thành Long
                    }
                }
            } catch (IllegalAccessException e) {
                System.err.println("Error accessing field: " + fieldName);
            }
        }
    }

    /**
     * Xây dựng động câu truy vấn SQL cho các tiêu chí tìm kiếm đặc biệt
     * Xử lý: Staff ID, RentArea, và TypeCode (danh sách)
     * 
     * @param searchBuilder tiêu chí tìm kiếm
     * @param where StringBuilder để xây dựng WHERE clause
     */
    public static void querySpecial(BuildingSearchBuilder searchBuilder, StringBuilder where) {
        // 1. Xử lý lọc theo Nhân viên quản lý (Staff)
        Long staffId = searchBuilder.getStaffId();
        if (staffId != null && staffId > 0) {
            // Lưu ý: SQL chính ở hàm findAll cần JOIN với bảng assignmentbuilding as ab
            where.append(" AND ab.staffid = ").append(staffId);
        }

        // 2. Xử lý lọc theo Diện tích thuê (RentArea) - Sử dụng EXISTS để tránh trùng lặp bản ghi
        Integer areaFrom = searchBuilder.getAreaFrom();
        Integer areaTo = searchBuilder.getAreaTo();
        
        if (areaFrom != null || areaTo != null) {
            where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
            if (areaFrom != null && areaFrom > 0) {
                where.append(" AND r.value >= ").append(areaFrom);
            }
            if (areaTo != null && areaTo > 0) {
                where.append(" AND r.value <= ").append(areaTo);
            }
            where.append(") ");
        }

        // 3. Xử lý lọc theo Loại tòa nhà (TypeCode) - Dùng Java 8 Stream để tối ưu
        List<String> typeCodes = searchBuilder.getTypeCodeList();
        if (typeCodes != null && !typeCodes.isEmpty()) {
            where.append(" AND (");
            
            // Tạo chuỗi: b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%'
            String sqlTypeCode = typeCodes.stream()
                    .filter(StringUtil::isNotEmpty)  // Lọc các chuỗi rỗng
                    .map(it -> "b.type LIKE '%" + it + "%'")
                    .collect(Collectors.joining(" OR "));
            
            if (!sqlTypeCode.isEmpty()) {
                where.append(sqlTypeCode).append(") ");
            } else {
                // Nếu không có typeCode hợp lệ, xóa "AND (" đi
                where.setLength(where.length() - 6);
            }
        }
    }
}
