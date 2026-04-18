# Hướng Dẫn Sử Dụng Phương Thức queryNormal

## Mô Tả
Phương thức `queryNormal` được sử dụng để xây dựng động câu truy vấn SQL dựa trên các tiêu chí tìm kiếm từ `BuildingSearchBuilder`.

## Vị Trí
- **Lớp:** `com.javaweb.repository.impl.BuildingRepositoryImpl`
- **Loại:** Static method
- **Mục đích:** Xây dựng động WHERE clause cho SQL query

## Signature
```java
public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where)
```

## Tham Số
| Tham số | Kiểu | Mô Tả |
|--------|------|-------|
| `buildingSearchBuilder` | BuildingSearchBuilder | Đối tượng chứa các tiêu chí tìm kiếm |
| `where` | StringBuilder | StringBuilder để xây dựng WHERE clause (sẽ được append) |

## Các Tiêu Chí Hỗ Trợ
1. **name** (String) - Tìm kiếm theo tên (LIKE)
2. **typeCode** (String) - Tìm kiếm theo loại bất động sản
3. **areaFrom** (Integer) - Diện tích từ (floor area >= value)
4. **areaTo** (Integer) - Diện tích đến (floor area <= value)
5. **rentPriceFrom** (Integer) - Giá thuê từ (rent price >= value)
6. **rentPriceTo** (Integer) - Giá thuê đến (rent price <= value)
7. **districtId** (Long) - ID của quận/huyện

## Cách Sử Dụng

### Ví Dụ 1: Tìm kiếm đơn giản
```java
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .name("building")
    .areaFrom(100)
    .areaTo(500)
    .build();

StringBuilder whereClause = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.queryNormal(searchBuilder, whereClause);

// Kết quả: WHERE 1 = 1 AND b.name LIKE '%building%' AND b.floorarea >= 100 AND b.floorarea <= 500
```

### Ví Dụ 2: Tìm kiếm với nhiều tiêu chí
```java
BuildingSearchBuilder searchBuilder = new BuildingSearchBuilder()
    .name("Tower")
    .typeCode("nguyen-can")
    .areaFrom(350)
    .rentPriceFrom(1000000)
    .rentPriceTo(5000000)
    .districtId(1L)
    .build();

StringBuilder whereClause = new StringBuilder("WHERE 1 = 1");
BuildingRepositoryImpl.queryNormal(searchBuilder, whereClause);

// Kết quả SQL sẽ bao gồm tất cả các điều kiện phù hợp
```

## Đặc Điểm
- **Reflection-based**: Sử dụng Java Reflection để động xử lý các trường
- **Null-safe**: Tự động bỏ qua các giá trị null
- **Flexible**: Có thể mở rộng để hỗ trợ thêm các tiêu chí khác
- **Static method**: Không cần tạo instance của BuildingRepositoryImpl để sử dụng

## Ghi Chú Quan Trọng

### SQL Injection Warning ⚠️
Phương thức hiện tại không sử dụng Prepared Statements cho các tham số. Để tăng bảo mật, nên cân nhắc sử dụng:

```java
// Thay vì:
where.append(" AND b.name LIKE '%").append(nameValue).append("%'");

// Nên sử dụng Prepared Statement với parameter binding
```

### Xử Lý Lỗi
- Các lỗi `IllegalAccessException` sẽ được in ra console
- Nếu giá trị không thể convert sang kiểu dữ liệu mong muốn, nó sẽ bị bỏ qua

### Mở Rộng
Để thêm tiêu chí tìm kiếm mới:
1. Thêm trường vào `BuildingSearchBuilder`
2. Thêm builder method vào `BuildingSearchBuilder`
3. Thêm else-if branch mới trong `queryNormal`

## Ví Dụ Mở Rộng - Thêm Tiêu Chí Mới
```java
// 1. Thêm vào BuildingSearchBuilder
private String street;

public BuildingSearchBuilder street(String street) {
    this.street = street;
    return this;
}

public String getStreet() {
    return street;
}

// 2. Thêm vào queryNormal
else if (fieldName.equals("street")) {
    String streetValue = String.valueOf(value);
    if (StringUtil.isNotEmpty(streetValue)) {
        where.append(" AND b.street LIKE '%").append(streetValue).append("%'");
    }
}
```

## Tương Tác với Các Utility Classes
- **StringUtil**: Kiểm tra string null, empty
- **NumberUtil**: Convert giá trị sang Integer, Long
- **MapUtil**: (nếu cần) xử lý Map operations

## Hiệu Năng
- Sử dụng Reflection nên có chi phí hiệu năng nhất định
- Thích hợp cho tìm kiếm, không thích hợp cho các thao tác loop lặp lại hàng triệu lần
- Với dataset vừa phải (< 10,000 bản ghi), hiệu năng là chấp nhận được

---
**Cập nhật:** 05/04/2026

