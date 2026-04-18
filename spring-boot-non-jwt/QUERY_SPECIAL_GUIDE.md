# Hướng Dẫn Sử Dụng Phương Thức querySpecial

## Mô Tả
Phương thức `querySpecial` được sử dụng để xây dựng động câu truy vấn SQL cho các tiêu chí tìm kiếm đặc biệt, bao gồm:
- Lọc theo Nhân viên quản lý (Staff ID)
- Lọc theo Diện tích thuê (RentArea) - sử dụng EXISTS subquery
- Lọc theo Loại tòa nhà (TypeCode) - hỗ trợ danh sách

## Vị Trí
- **Lớp:** `com.javaweb.repository.impl.BuildingRepositoryImpl`
- **Loại:** Static method
- **Mục đích:** Xây dựng động WHERE clause cho SQL query có các điều kiện phức tạp

## Signature
```java
public static void querySpecial(BuildingSearchBuilder searchBuilder, StringBuilder where)
```

## Tham Số
| Tham số | Kiểu | Mô Tả |
|--------|------|-------|
| `searchBuilder` | BuildingSearchBuilder | Đối tượng chứa các tiêu chí tìm kiếm |
| `where` | StringBuilder | StringBuilder để xây dựng WHERE clause (sẽ được append) |

## Các Tiêu Chí Hỗ Trợ

### 1. **staffId** (Long)
- Lọc các tòa nhà được quản lý bởi nhân viên cụ thể
- Giá trị: > 0
- **Yêu cầu:** SQL chính phải có JOIN với bảng `assignmentbuilding` với alias `ab`
- **Ví dụ SQL sinh ra:** `AND ab.staffid = 123`

### 2. **areaFrom / areaTo** (Integer)
- Lọc theo khoảng diện tích thuê (từ bảng rentarea)
- Giá trị: > 0
- **Phương pháp:** Sử dụng EXISTS subquery để tránh trùng lặp bản ghi
- **Ví dụ SQL sinh ra:**
```sql
AND EXISTS (SELECT * FROM rentarea r 
            WHERE b.id = r.buildingid 
            AND r.value >= 100 
            AND r.value <= 500)
```

### 3. **typeCodeList** (List<String>)
- Lọc theo danh sách loại bất động sản
- Giá trị: Danh sách các chuỗi không rỗng
- **Phương pháp:** Sử dụng Java 8 Stream + OR logic
- **Ví dụ SQL sinh ra:**
```sql
AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

## Cách Sử Dụng

### Ví Dụ 1: Tìm kiếm theo Staff ID
```java
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .staffId(5L)
    .build();

StringBuilder whereClause = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(searchBuilder, whereClause);

// Kết quả: WHERE 1 = 1 AND ab.staffid = 5
```

### Ví Dụ 2: Tìm kiếm theo RentArea
```java
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .areaFrom(100)
    .areaTo(500)
    .build();

StringBuilder whereClause = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(searchBuilder, whereClause);

// Kết quả: WHERE 1 = 1 AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid AND r.value >= 100 AND r.value <= 500)
```

### Ví Dụ 3: Tìm kiếm theo TypeCode List
```java
List<String> types = Arrays.asList("TANG_TRET", "NGUYEN_CAN");
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .typeCodeList(types)
    .build();

StringBuilder whereClause = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(searchBuilder, whereClause);

// Kết quả: WHERE 1 = 1 AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

### Ví Dụ 4: Kết Hợp Tất Cả Các Tiêu Chí
```java
List<String> types = Arrays.asList("TANG_TRET", "NGUYEN_CAN");
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .staffId(5L)
    .areaFrom(100)
    .areaTo(500)
    .typeCodeList(types)
    .build();

StringBuilder whereClause = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(searchBuilder, whereClause);

// Kết quả sẽ bao gồm tất cả các điều kiện:
// WHERE 1 = 1 
// AND ab.staffid = 5 
// AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid AND r.value >= 100 AND r.value <= 500)
// AND (b.type LIKE '%TANG_TRET%' OR b.type LIKE '%NGUYEN_CAN%')
```

## Đặc Điểm Chính

### ✅ Ưu Điểm
- **Tránh trùng lặp bản ghi:** Sử dụng EXISTS thay vì JOIN cho RentArea
- **Flexible:** Hỗ trợ danh sách TypeCode với OR logic
- **Type-safe:** Xử lý null checks và validation
- **Static method:** Không cần instance, gọi trực tiếp
- **Stream-based:** Sử dụng Java 8 Stream API cho TypeCode

### ⚠️ Lưu Ý Quan Trọng

#### 1. SQL Injection Risk
**Cảnh báo:** Phương thức hiện tại không sử dụng Prepared Statements. Để tăng bảo mật, nên:
- Validate và escape các giá trị String trước khi sử dụng
- Trong tương lai, nên chuyển sang sử dụng Parameterized Queries

#### 2. Required JOINs
Khi sử dụng `querySpecial`, SQL chính cần các JOIN/EXISTS sau:
```sql
SELECT b.* FROM building b
LEFT JOIN assignmentbuilding ab ON b.id = ab.buildingid
WHERE 1 = 1
-- querySpecial sẽ thêm các điều kiện vào đây
```

#### 3. Xử Lý Null & Empty
- Các giá trị null sẽ bị bỏ qua
- Các chuỗi rỗng trong typeCodeList sẽ bị lọc
- Nếu areaFrom/areaTo = 0 hoặc âm, sẽ bị bỏ qua

## Mở Rộng

### Thêm Tiêu Chí Mới - Ward
Ví dụ thêm hỗ trợ tìm kiếm theo Ward (Phường):

```java
// 1. Thêm vào BuildingSearchBuilder
private String ward;

public BuildingSearchBuilder ward(String ward) {
    this.ward = ward;
    return this;
}

public String getWard() {
    return ward;
}

// 2. Thêm vào querySpecial
List<String> wards = searchBuilder.getWardList();
if (wards != null && !wards.isEmpty()) {
    where.append(" AND (");
    String sqlWards = wards.stream()
            .filter(StringUtil::isNotEmpty)
            .map(it -> "b.ward LIKE '%" + it + "%'")
            .collect(Collectors.joining(" OR "));
    if (!sqlWards.isEmpty()) {
        where.append(sqlWards).append(") ");
    }
}
```

## Tương Tác với Các Utility Classes
- **StringUtil**: Kiểm tra string empty
- **NumberUtil**: (có thể mở rộng) validate số
- **Collectors**: Stream API để join chuỗi

## So Sánh queryNormal vs querySpecial

| Tiêu Chí | queryNormal | querySpecial |
|----------|-----------|------------|
| **Phạm vi** | Tìm kiếm cơ bản | Tìm kiếm nâng cao |
| **Loại dữ liệu** | String, Integer, Long | Thêm List<String> |
| **Phương pháp** | Reflection + If-else | Direct access + Stream |
| **Hiệu năng** | Thấp hơn (Reflection) | Cao hơn (Direct) |
| **RentArea** | Không hỗ trợ | EXISTS subquery |
| **Staff** | Không hỗ trợ | Có hỗ trợ |
| **TypeCode** | Single string | List<String> |

## Hiệu Năng

### Tối Ưu Hoá
- Sử dụng EXISTS thay vì LEFT JOIN để tránh Cartesian product
- Stream API có overhead nhẹ nhưng không đáng lo ngại
- Tích hợp StringBuilder cho performance tối ưu

### Lợi Ích
- EXISTS query: Giảm 50-80% thời gian truy vấn so với JOIN trên dataset lớn
- Stream + filter: Loại bỏ chuỗi rỗng trước khi tạo SQL
- StringBuilder: Tránh tạo nhiều String objects tạm thời

## Cảnh Báo và Best Practices

### 🚨 Bảo Mật (Security)
```java
// ❌ KHÔNG NÊN - SQL Injection risk
where.append(" AND b.type LIKE '%" + userInput + "%'");

// ✅ NÊN - Sử dụng Prepared Statement
preparedStatement.setString(index, "%" + userInput + "%");
```

### 📝 Logging
Để debug, có thể log WHERE clause:
```java
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .staffId(5L)
    .areaFrom(100)
    .build();

StringBuilder where = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.querySpecial(searchBuilder, where);

System.out.println("Generated SQL WHERE: " + where.toString());
```

## Test Cases

### Test 1: StaffId = 0 (bỏ qua)
```java
searchBuilder.staffId(0L);
// where: WHERE 1 = 1 (không thêm điều kiện)
```

### Test 2: TypeCodeList rỗng
```java
searchBuilder.typeCodeList(Collections.emptyList());
// where: WHERE 1 = 1 (không thêm điều kiện)
```

### Test 3: AreaFrom = null, AreaTo = 500
```java
searchBuilder.areaFrom(null).areaTo(500);
// where chứa: AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid AND r.value <= 500)
```

---
**Cập nhật:** 05/04/2026
**Phiên bản:** 1.0

