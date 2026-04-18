# 📋 TÓM TẮT CÁC THAY ĐỔI - queryNormal & querySpecial

## ✅ Hoàn Thành

### 1. Cập nhật BuildingSearchBuilder
**File:** `com/javaweb/model/search/BuildingSearchBuilder.java`

**Thêm trường:**
```java
private Long staffId;           // ID nhân viên quản lý
private List<String> typeCodeList;  // Danh sách loại bất động sản
```

**Thêm builder methods:**
```java
public BuildingSearchBuilder staffId(Long staffId)
public BuildingSearchBuilder typeCodeList(List<String> typeCodeList)
```

**Thêm getter methods:**
```java
public Long getStaffId()
public List<String> getTypeCodeList()
```

**Cập nhật imports:**
```java
import java.util.List;
```

---

### 2. Cập nhật BuildingRepositoryImpl
**File:** `com/javaweb/repository/impl/BuildingRepositoryImpl.java`

#### Imports được thêm:
```java
import com.javaweb.model.search.BuildingSearchBuilder;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
import java.util.stream.Collectors;
```

#### Phương thức queryNormal:
```java
public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where)
```

**Tính năng:**
- Sử dụng Reflection để xử lý các trường một cách động
- Hỗ trợ: name, typeCode, areaFrom, areaTo, rentPriceFrom, rentPriceTo, districtId
- Null-safe, tự động bỏ qua giá trị null hoặc rỗng

**Ví dụ:**
```java
BuildingSearchBuilder search = new BuildingSearchBuilder()
    .name("Building")
    .areaFrom(100)
    .areaTo(500)
    .build();

StringBuilder where = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.queryNormal(search, where);
// Result: WHERE 1 = 1 AND b.name LIKE '%Building%' AND b.floorarea >= 100 AND b.floorarea <= 500
```

---

#### Phương thức querySpecial:
```java
public static void querySpecial(BuildingSearchBuilder searchBuilder, StringBuilder where)
```

**Tính năng:**
- **Staff ID:** Lọc theo nhân viên quản lý (JOIN với assignmentbuilding)
- **RentArea:** Lọc theo diện tích thuê (EXISTS subquery - tránh trùng lặp)
- **TypeCode List:** Lọc theo danh sách loại bất động sản (Stream + OR logic)

**Ví dụ:**
```java
List<String> types = Arrays.asList("TANG_TRET", "NGUYEN_CAN");
BuildingSearchBuilder search = new BuildingSearchBuilder()
    .staffId(5L)
    .areaFrom(100)
    .areaTo(500)
    .typeCodeList(types)
    .build();

StringBuilder where = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(search, where);
// Result: WHERE 1 = 1 
//         AND ab.staffid = 5 
//         AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid AND r.value >= 100 AND r.value <= 500)
//         AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

---

## 📊 So Sánh queryNormal vs querySpecial

| Tiêu Chí | queryNormal | querySpecial |
|----------|-----------|------------|
| **Tìm kiếm cơ bản** | ✅ | ❌ |
| **Staff ID** | ❌ | ✅ |
| **RentArea** | ❌ | ✅ |
| **TypeCode List** | ❌ | ✅ |
| **Phương pháp** | Reflection | Direct access + Stream |
| **Hiệu năng** | Thấp | Cao |
| **Dễ sử dụng** | ✅ | ✅ |

---

## 🚀 Cách Sử Dụng Thực Tế

### Tìm kiếm Toàn Bộ
```java
// Tạo search builder
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .name("Tower")
    .districtId(1L)
    .areaFrom(100)
    .areaTo(1000)
    .rentPriceFrom(5000000)
    .rentPriceTo(20000000)
    .typeCodeList(Arrays.asList("TANG_TRET", "NGUYEN_CAN"))
    .staffId(3L)
    .build();

// Xây dựng SQL WHERE clause
StringBuilder where = new StringBuilder("SELECT * FROM building b WHERE 1 = 1");

// Áp dụng queryNormal (tìm kiếm cơ bản)
BuildingRepositoryImpl.queryNormal(searchBuilder, where);

// Áp dụng querySpecial (tìm kiếm nâng cao)
BuildingRepositoryImpl.querySpecial(searchBuilder, where);

// Kết quả: SELECT * FROM building b WHERE 1 = 1 
//          AND b.name LIKE '%Tower%' 
//          AND b.districtid = 1 
//          AND b.floorarea >= 100 
//          AND b.floorarea <= 1000 
//          AND b.rentprice >= 5000000 
//          AND b.rentprice <= 20000000 
//          AND ab.staffid = 3 
//          AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid AND r.value >= 100 AND r.value <= 1000)
//          AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

---

## ⚠️ Lưu Ý Quan Trọng

### 1. SQL Injection Risk
**Cảnh báo:** Phương thức không sử dụng Prepared Statements. Trong tương lai nên:
- Sử dụng Parameterized Queries (PreparedStatement)
- Validate input trước khi sử dụng

### 2. Required Database Structure
```sql
-- Bảng chính
CREATE TABLE building (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    districtid BIGINT,
    typecode VARCHAR(50),
    floorarea INT,
    rentprice INT,
    ...
);

-- Bảng hỗ trợ
CREATE TABLE assignmentbuilding (
    id BIGINT PRIMARY KEY,
    buildingid BIGINT,
    staffid BIGINT,
    FOREIGN KEY (buildingid) REFERENCES building(id)
);

CREATE TABLE rentarea (
    id BIGINT PRIMARY KEY,
    buildingid BIGINT,
    value INT,
    FOREIGN KEY (buildingid) REFERENCES building(id)
);
```

### 3. SQL JOIN Cần Thiết
Khi dùng `querySpecial` với staffId, SQL cần:
```sql
SELECT b.* FROM building b
LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid
WHERE 1 = 1
-- querySpecial sẽ thêm các điều kiện tại đây
```

---

## 📁 Tài Liệu Chi Tiết

1. **QUERY_NORMAL_GUIDE.md** - Hướng dẫn chi tiết phương thức queryNormal
2. **QUERY_SPECIAL_GUIDE.md** - Hướng dẫn chi tiết phương thức querySpecial

---

## 🔍 Kiểm Tra Lỗi Compile

**Status:** ✅ **PASSED** - Không có lỗi compile

**Warnings (bình thường):**
- Fields @Value không được assign (Spring injection)
- Methods không được sử dụng (vì là static helper)
- Suggestion dùng switch statement (tùy chọn)

---

## 📝 Test Cases Đề Xuất

```java
@Test
public void testQueryNormalWithName() {
    BuildingSearchBuilder builder = new BuildingSearchBuilder()
        .name("Tower")
        .build();
    StringBuilder where = new StringBuilder("WHERE 1 = 1");
    BuildingRepositoryImpl.queryNormal(builder, where);
    assertTrue(where.toString().contains("b.name LIKE"));
}

@Test
public void testQuerySpecialWithStaffId() {
    BuildingSearchBuilder builder = new BuildingSearchBuilder()
        .staffId(5L)
        .build();
    StringBuilder where = new StringBuilder("WHERE 1 = 1");
    BuildingRepositoryImpl.querySpecial(builder, where);
    assertTrue(where.toString().contains("ab.staffid = 5"));
}

@Test
public void testQuerySpecialWithTypeCodeList() {
    BuildingSearchBuilder builder = new BuildingSearchBuilder()
        .typeCodeList(Arrays.asList("TYPE1", "TYPE2"))
        .build();
    StringBuilder where = new StringBuilder("WHERE 1 = 1");
    BuildingRepositoryImpl.querySpecial(builder, where);
    assertTrue(where.toString().contains("OR"));
}
```

---

## 🎯 Kết Luận

✅ **Tất cả chức năng đã hoàn thành:**
- BuildingSearchBuilder: Thêm staffId, typeCodeList, getters
- queryNormal: Xây dựng SQL cho tìm kiếm cơ bản (Reflection-based)
- querySpecial: Xây dựng SQL cho tìm kiếm nâng cao (Staff, RentArea, TypeCode List)

📚 **Tài liệu:** Chi tiết trong QUERY_NORMAL_GUIDE.md và QUERY_SPECIAL_GUIDE.md

🚀 **Sẵn sàng:** Code có thể sử dụng ngay, không có lỗi compile

---
**Phiên bản:** 1.0  
**Cập nhật:** 05/04/2026

