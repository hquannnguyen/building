# 💡 Ví Dụ Sử Dụng queryNormal & querySpecial trong API

## 📍 Vị Trí: BuildingAPI.java

```java
package com.javaweb.api;

import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.search.BuildingSearchBuilder;
import com.javaweb.repository.impl.BuildingRepositoryImpl;
import com.javaweb.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {
    
    @Autowired
    private BuildingService buildingService;
    
    /**
     * GET /api/building/search
     * 
     * Tìm kiếm cơ bản sử dụng queryNormal
     * 
     * @param name Tên tòa nhà
     * @param districtId ID quận
     * @param typeCode Loại tòa nhà
     * @param areaFrom Diện tích từ
     * @param areaTo Diện tích đến
     * @param rentPriceFrom Giá thuê từ
     * @param rentPriceTo Giá thuê đến
     * @return Danh sách các tòa nhà
     */
    @GetMapping("/search")
    public List<BuildingDTO> searchBuildings(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) String typeCode,
            @RequestParam(required = false) Integer areaFrom,
            @RequestParam(required = false) Integer areaTo,
            @RequestParam(required = false) Integer rentPriceFrom,
            @RequestParam(required = false) Integer rentPriceTo) {
        
        // Xây dựng search builder
        BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
            .name(name)
            .districtId(districtId)
            .typeCode(typeCode)
            .areaFrom(areaFrom)
            .areaTo(areaTo)
            .rentPriceFrom(rentPriceFrom)
            .rentPriceTo(rentPriceTo)
            .build();
        
        // Xây dựng SQL WHERE clause
        StringBuilder where = new StringBuilder("WHERE 1 = 1");
        
        // Áp dụng queryNormal
        BuildingRepositoryImpl.queryNormal(searchBuilder, where);
        
        // Thực thi query và lấy kết quả
        // (Phần này phụ thuộc vào cách implementation của service/repository)
        
        return buildingService.findAll(name, districtId, typeCode, areaFrom, areaTo, rentPriceFrom, rentPriceTo);
    }
    
    /**
     * GET /api/building/search-advanced
     * 
     * Tìm kiếm nâng cao sử dụng cả queryNormal và querySpecial
     * 
     * @param params Tất cả các tham số request
     * @return Danh sách các tòa nhà
     */
    @GetMapping("/search-advanced")
    public List<BuildingDTO> advancedSearch(
            @RequestParam Map<String, String> params) {
        
        // Chuyển đổi tham số String sang Long/Integer
        String name = params.get("name");
        Long districtId = params.containsKey("districtId") ? Long.valueOf(params.get("districtId")) : null;
        String typeCode = params.get("typeCode");
        Integer areaFrom = params.containsKey("areaFrom") ? Integer.valueOf(params.get("areaFrom")) : null;
        Integer areaTo = params.containsKey("areaTo") ? Integer.valueOf(params.get("areaTo")) : null;
        Integer rentPriceFrom = params.containsKey("rentPriceFrom") ? Integer.valueOf(params.get("rentPriceFrom")) : null;
        Integer rentPriceTo = params.containsKey("rentPriceTo") ? Integer.valueOf(params.get("rentPriceTo")) : null;
        Long staffId = params.containsKey("staffId") ? Long.valueOf(params.get("staffId")) : null;
        
        // Xử lý TypeCodeList (có thể là CSV hoặc array)
        List<String> typeCodeList = null;
        String typeCodeParam = params.get("typeCodeList");
        if (typeCodeParam != null && !typeCodeParam.isEmpty()) {
            typeCodeList = Arrays.asList(typeCodeParam.split(","));
        }
        
        // Xây dựng search builder
        BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
            .name(name)
            .districtId(districtId)
            .typeCode(typeCode)
            .areaFrom(areaFrom)
            .areaTo(areaTo)
            .rentPriceFrom(rentPriceFrom)
            .rentPriceTo(rentPriceTo)
            .staffId(staffId)
            .typeCodeList(typeCodeList)
            .build();
        
        // Xây dựng SQL SELECT statement
        StringBuilder sql = new StringBuilder("SELECT b.* FROM building b LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid WHERE 1 = 1");
        
        // Áp dụng queryNormal (tìm kiếm cơ bản)
        BuildingRepositoryImpl.queryNormal(searchBuilder, sql);
        
        // Áp dụng querySpecial (tìm kiếm nâng cao)
        BuildingRepositoryImpl.querySpecial(searchBuilder, sql);
        
        // Log SQL cho debugging
        System.out.println("Generated SQL: " + sql.toString());
        
        // Thực thi query
        return buildingService.findAll(name, districtId, typeCode, areaFrom, areaTo, rentPriceFrom, rentPriceTo);
    }
    
    /**
     * GET /api/building/filter-by-staff
     * 
     * Tìm kiếm tòa nhà theo nhân viên quản lý
     * 
     * @param staffId ID nhân viên
     * @return Danh sách tòa nhà của nhân viên
     */
    @GetMapping("/filter-by-staff")
    public List<BuildingDTO> findBuildingsByStaff(
            @RequestParam Long staffId) {
        
        BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
            .staffId(staffId)
            .build();
        
        StringBuilder sql = new StringBuilder("SELECT b.* FROM building b LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid WHERE 1 = 1");
        
        BuildingRepositoryImpl.querySpecial(searchBuilder, sql);
        
        System.out.println("SQL - Find by Staff: " + sql.toString());
        
        // Thực thi query
        return buildingService.findAll(null, null, null, null, null, null, null);
    }
    
    /**
     * GET /api/building/filter-by-type
     * 
     * Tìm kiếm tòa nhà theo loại (multi-select)
     * 
     * @param typeCode CSV list của các loại, ví dụ: "TANG_TRET,NGUYEN_CAN,TAN"
     * @return Danh sách tòa nhà
     */
    @GetMapping("/filter-by-type")
    public List<BuildingDTO> findBuildingsByType(
            @RequestParam String typeCode) {
        
        List<String> typeCodeList = Arrays.asList(typeCode.split(","));
        
        BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
            .typeCodeList(typeCodeList)
            .build();
        
        StringBuilder sql = new StringBuilder("SELECT b.* FROM building b WHERE 1 = 1");
        
        BuildingRepositoryImpl.querySpecial(searchBuilder, sql);
        
        System.out.println("SQL - Find by Types: " + sql.toString());
        
        // Thực thi query
        return buildingService.findAll(null, null, null, null, null, null, null);
    }
    
    /**
     * GET /api/building/filter-by-rentarea
     * 
     * Tìm kiếm tòa nhà theo diện tích thuê
     * 
     * @param areaFrom Diện tích thuê từ
     * @param areaTo Diện tích thuê đến
     * @return Danh sách tòa nhà
     */
    @GetMapping("/filter-by-rentarea")
    public List<BuildingDTO> findBuildingsByRentArea(
            @RequestParam(required = false) Integer areaFrom,
            @RequestParam(required = false) Integer areaTo) {
        
        BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
            .areaFrom(areaFrom)
            .areaTo(areaTo)
            .build();
        
        StringBuilder sql = new StringBuilder("SELECT b.* FROM building b WHERE 1 = 1");
        
        BuildingRepositoryImpl.querySpecial(searchBuilder, sql);
        
        System.out.println("SQL - Find by RentArea: " + sql.toString());
        
        // Thực thi query
        return buildingService.findAll(null, null, null, areaFrom, areaTo, null, null);
    }
    
    /**
     * GET /api/building/complex-search
     * 
     * Tìm kiếm phức tạp với tất cả các tiêu chí
     * URL Example: 
     * /api/building/complex-search?name=Tower&districtId=1&areaFrom=100&areaTo=500&staffId=3&typeCodeList=TANG_TRET,NGUYEN_CAN
     * 
     * @param params Map của tất cả các tham số
     * @return Danh sách tòa nhà
     */
    @GetMapping("/complex-search")
    public List<BuildingDTO> complexSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) String typeCode,
            @RequestParam(required = false) Integer areaFrom,
            @RequestParam(required = false) Integer areaTo,
            @RequestParam(required = false) Integer rentPriceFrom,
            @RequestParam(required = false) Integer rentPriceTo,
            @RequestParam(required = false) Long staffId,
            @RequestParam(required = false) String typeCodeList) {
        
        // Parse typeCodeList
        List<String> types = null;
        if (typeCodeList != null && !typeCodeList.isEmpty()) {
            types = Arrays.asList(typeCodeList.split(","));
        }
        
        // Xây dựng search builder
        BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
            .name(name)
            .districtId(districtId)
            .typeCode(typeCode)
            .areaFrom(areaFrom)
            .areaTo(areaTo)
            .rentPriceFrom(rentPriceFrom)
            .rentPriceTo(rentPriceTo)
            .staffId(staffId)
            .typeCodeList(types)
            .build();
        
        // Xây dựng SQL
        StringBuilder sql = new StringBuilder("SELECT b.* FROM building b LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid WHERE 1 = 1");
        
        // Áp dụng cả hai phương thức
        BuildingRepositoryImpl.queryNormal(searchBuilder, sql);
        BuildingRepositoryImpl.querySpecial(searchBuilder, sql);
        
        // Log
        System.out.println("Complex Search SQL: " + sql.toString());
        System.out.println("Search Builder: " + searchBuilder.toString());
        
        // Thực thi query
        return buildingService.findAll(name, districtId, typeCode, areaFrom, areaTo, rentPriceFrom, rentPriceTo);
    }
}
```

---

## 📝 Ví Dụ URL Requests

### 1. Tìm kiếm cơ bản
```
GET /api/building/search?name=Tower&areaFrom=100&areaTo=500
```

### 2. Tìm kiếm nâng cao
```
GET /api/building/search-advanced?name=Tower&districtId=1&areaFrom=100&areaTo=500&staffId=3&typeCodeList=TANG_TRET,NGUYEN_CAN
```

### 3. Lọc theo nhân viên
```
GET /api/building/filter-by-staff?staffId=5
```

### 4. Lọc theo loại
```
GET /api/building/filter-by-type?typeCode=TANG_TRET,NGUYEN_CAN
```

### 5. Lọc theo diện tích thuê
```
GET /api/building/filter-by-rentarea?areaFrom=100&areaTo=500
```

### 6. Tìm kiếm phức tạp
```
GET /api/building/complex-search?name=Alpha&districtId=1&areaFrom=100&areaTo=1000&staffId=3&typeCodeList=TANG_TRET,NGUYEN_CAN&rentPriceFrom=5000000&rentPriceTo=20000000
```

---

## 📊 SQL Generated Examples

### queryNormal Only
```sql
SELECT * FROM building b 
WHERE 1 = 1 
AND b.name LIKE '%Tower%' 
AND b.districtid = 1 
AND b.floorarea >= 100 
AND b.floorarea <= 500 
AND b.rentprice >= 5000000 
AND b.rentprice <= 20000000
```

### querySpecial Only (Staff + RentArea + TypeCode)
```sql
SELECT * FROM building b 
WHERE 1 = 1 
AND ab.staffid = 3 
AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid AND r.value >= 100 AND r.value <= 500)
AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

### Both queryNormal + querySpecial (Full Search)
```sql
SELECT b.* FROM building b 
LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid 
WHERE 1 = 1 
AND b.name LIKE '%Tower%' 
AND b.districtid = 1 
AND b.floorarea >= 100 
AND b.floorarea <= 500 
AND b.rentprice >= 5000000 
AND b.rentprice <= 20000000
AND ab.staffid = 3 
AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid AND r.value >= 100 AND r.value <= 500)
AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

---

## 🔧 Integration Steps

1. **Thêm phương thức vào BuildingAPI.java** (copy code trên)
2. **Cập nhật BuildingService** để xử lý các SQL queries
3. **Update BuildingRepository** để tương thích với dynamically generated SQL
4. **Test các endpoints** với curl hoặc Postman
5. **Monitor logs** để xem generated SQL

---

## ✅ Best Practices

1. ✅ **Luôn validate input** trước khi tạo SearchBuilder
2. ✅ **Log generated SQL** để debugging
3. ✅ **Test edge cases:** null values, empty lists, 0 values
4. ✅ **Sử dụng Prepared Statements** thay vì string concatenation (bảo mật)
5. ✅ **Add pagination** để xử lý large result sets
6. ✅ **Cache frequently used searches** để tối ưu hiệu năng

---

**Cập nhật:** 05/04/2026  
**Phiên bản:** 1.0

