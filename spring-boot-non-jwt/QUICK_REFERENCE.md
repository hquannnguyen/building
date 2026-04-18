# 🚀 QUICK REFERENCE - queryNormal & querySpecial

## 📍 Một Dòng Summary

**queryNormal**: Reflection-based builder cho tìm kiếm cơ bản | **querySpecial**: Stream-based builder cho Staff/RentArea/TypeCode

---

## ⚡ Quick Start (30 giây)

```java
// 1. Tạo search builder
BuildingSearchBuilder builder = new BuildingSearchBuilder()
    .name("Tower")
    .areaFrom(100)
    .areaTo(500)
    .staffId(5L)
    .typeCodeList(Arrays.asList("TANG_TRET", "NGUYEN_CAN"))
    .build();

// 2. Xây dựng SQL
StringBuilder sql = new StringBuilder("SELECT * FROM building b LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid WHERE 1 = 1");

// 3. Áp dụng query builders
BuildingRepositoryImpl.queryNormal(builder, sql);
BuildingRepositoryImpl.querySpecial(builder, sql);

// 4. Thực thi SQL
System.out.println(sql.toString());
// Result: SELECT * FROM building b ... WHERE 1 = 1 AND b.name LIKE '%Tower%' AND ... AND ab.staffid = 5 AND ...
```

---

## 📊 So Sánh Nhanh

| | queryNormal | querySpecial |
|---|---|---|
| **Mục đích** | Cơ bản | Nâng cao |
| **Tìm kiếm** | name, type, area, price, district | staff, rentarea, typelist |
| **Kỹ thuật** | Reflection | Stream API |
| **Hiệu năng** | Thấp | Cao |
| **Sử dụng** | Simple searches | Complex filters |

---

## 🎯 Supported Fields

### queryNormal
```java
.name(String)               // b.name LIKE
.typeCode(String)           // b.typecode =
.areaFrom(Integer)          // b.floorarea >=
.areaTo(Integer)            // b.floorarea <=
.rentPriceFrom(Integer)     // b.rentprice >=
.rentPriceTo(Integer)       // b.rentprice <=
.districtId(Long)           // b.districtid =
```

### querySpecial
```java
.staffId(Long)              // ab.staffid =
.areaFrom(Integer)          // EXISTS rentarea >= (override normal)
.areaTo(Integer)            // EXISTS rentarea <= (override normal)
.typeCodeList(List<String>) // b.type LIKE OR
```

---

## 💡 Common Use Cases

### Use Case 1: Tìm tòa nhà theo tên và diện tích
```java
var builder = new BuildingSearchBuilder()
    .name("Alpha")
    .areaFrom(100)
    .areaTo(500)
    .build();

var sql = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.queryNormal(builder, sql);
// WHERE 1 = 1 AND b.name LIKE '%Alpha%' AND b.floorarea >= 100 AND b.floorarea <= 500
```

### Use Case 2: Tìm tòa nhà của nhân viên
```java
var builder = new BuildingSearchBuilder()
    .staffId(3L)
    .build();

var sql = new StringBuilder("SELECT b.* FROM building b LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(builder, sql);
// ... AND ab.staffid = 3
```

### Use Case 3: Lọc theo loại tòa nhà
```java
var builder = new BuildingSearchBuilder()
    .typeCodeList(Arrays.asList("TANG_TRET", "NGUYEN_CAN"))
    .build();

var sql = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(builder, sql);
// WHERE 1 = 1 AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

### Use Case 4: Tìm kiếm fulltext
```java
var builder = new BuildingSearchBuilder()
    .name("Tower")
    .districtId(1L)
    .areaFrom(100)
    .areaTo(1000)
    .rentPriceFrom(5000000)
    .rentPriceTo(20000000)
    .staffId(3L)
    .typeCodeList(Arrays.asList("TANG_TRET", "NGUYEN_CAN"))
    .build();

var sql = new StringBuilder("SELECT b.* FROM building b LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid WHERE 1 = 1");
BuildingRepositoryImpl.queryNormal(builder, sql);
BuildingRepositoryImpl.querySpecial(builder, sql);
// Toàn bộ điều kiện được áp dụng
```

---

## 🔧 API Endpoints (từ API_USAGE_EXAMPLES.md)

```
GET /api/building/search?name=Tower&areaFrom=100&areaTo=500
GET /api/building/filter-by-staff?staffId=5
GET /api/building/filter-by-type?typeCode=TANG_TRET,NGUYEN_CAN
GET /api/building/filter-by-rentarea?areaFrom=100&areaTo=500
GET /api/building/complex-search?name=Tower&staffId=3&typeCodeList=TANG_TRET,NGUYEN_CAN
```

---

## ⚠️ Important Notes

1. **Null-safe**: Tất cả null values tự động bị bỏ qua
2. **Empty-safe**: Empty strings/lists không sinh ra SQL
3. **Type-safe**: Sử dụng generics cho type conversion
4. **Dynamic**: Chỉ append những điều kiện cần thiết

---

## 🐛 Debugging

```java
// Log generated SQL
var builder = new BuildingSearchBuilder().name("Test").build();
var sql = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.queryNormal(builder, sql);
System.out.println("SQL: " + sql.toString());

// Check if condition applied
if (sql.toString().contains("b.name LIKE")) {
    System.out.println("✓ Name condition applied");
}
```

---

## 📚 Full Documentation

- **COMPLETE_GUIDE.md** - Tất cả thông tin
- **QUERY_NORMAL_GUIDE.md** - Chi tiết queryNormal
- **QUERY_SPECIAL_GUIDE.md** - Chi tiết querySpecial
- **API_USAGE_EXAMPLES.md** - Ví dụ API
- **IMPLEMENTATION_SUMMARY.md** - Tóm tắt thay đổi

---

## ✅ Checklist trước khi sử dụng

- [ ] Import Collectors (`import java.util.stream.Collectors;`)
- [ ] Import StringUtil, NumberUtil
- [ ] Import BuildingSearchBuilder
- [ ] StringBuilder initialized với "WHERE 1 = 1"
- [ ] Gọi build() sau khi set các fields
- [ ] Kiểm tra generated SQL

---

**Ready to use!** 🚀  
**Version:** 1.0  
**Date:** April 5, 2026

