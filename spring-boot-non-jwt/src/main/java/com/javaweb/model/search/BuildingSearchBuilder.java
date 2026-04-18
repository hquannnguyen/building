package com.javaweb.model.search;

import java.util.List;

/**
 * Builder class để xây dựng các tiêu chí tìm kiếm Building
 * Sử dụng Builder Pattern để quản lý các tham số tìm kiếm một cách rõ ràng và dễ bảo trì
 */
public class BuildingSearchBuilder {
    private String name;
    private Long districtId;
    private String typeCode;
    private List<String> typeCodeList;
    private Long staffId;
    private Integer areaFrom;
    private Integer areaTo;
    private Integer rentPriceFrom;
    private Integer rentPriceTo;

    // Constructor private
    private BuildingSearchBuilder() {
    }

    // Static factory method
    public static BuildingSearchBuilder builder() {
        return new BuildingSearchBuilder();
    }

    public BuildingSearchBuilder name(String name) {
        this.name = name;
        return this;
    }

    public BuildingSearchBuilder districtId(Long districtId) {
        this.districtId = districtId;
        return this;
    }

    public BuildingSearchBuilder typeCode(String typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public BuildingSearchBuilder areaFrom(Integer areaFrom) {
        this.areaFrom = areaFrom;
        return this;
    }

    public BuildingSearchBuilder areaTo(Integer areaTo) {
        this.areaTo = areaTo;
        return this;
    }

    public BuildingSearchBuilder rentPriceFrom(Integer rentPriceFrom) {
        this.rentPriceFrom = rentPriceFrom;
        return this;
    }

    public BuildingSearchBuilder rentPriceTo(Integer rentPriceTo) {
        this.rentPriceTo = rentPriceTo;
        return this;
    }

    public BuildingSearchBuilder staffId(Long staffId) {
        this.staffId = staffId;
        return this;
    }

    public BuildingSearchBuilder typeCodeList(List<String> typeCodeList) {
        this.typeCodeList = typeCodeList;
        return this;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public List<String> getTypeCodeList() {
        return typeCodeList;
    }

    public Long getStaffId() {
        return staffId;
    }

    public Integer getAreaFrom() {
        return areaFrom;
    }

    public Integer getAreaTo() {
        return areaTo;
    }

    public Integer getRentPriceFrom() {
        return rentPriceFrom;
    }

    public Integer getRentPriceTo() {
        return rentPriceTo;
    }

    /**
     * Build method - có thể thêm validation logic ở đây nếu cần
     * @return BuildingSearchBuilder instance
     */
    public BuildingSearchBuilder build() {
        // Có thể thêm validation ở đây
        // Ví dụ: kiểm tra areaFrom <= areaTo, rentPriceFrom <= rentPriceTo
        validateSearchCriteria();
        return this;
    }

    /**
     * Validate các tiêu chí tìm kiếm
     */
    private void validateSearchCriteria() {
        if (areaFrom != null && areaTo != null && areaFrom > areaTo) {
            throw new IllegalArgumentException("areaFrom không được lớn hơn areaTo");
        }
        if (rentPriceFrom != null && rentPriceTo != null && rentPriceFrom > rentPriceTo) {
            throw new IllegalArgumentException("rentPriceFrom không được lớn hơn rentPriceTo");
        }
    }

    /**
     * Kiểm tra xem có tiêu chí tìm kiếm nào không
     * @return true nếu có ít nhất một tiêu chí, false nếu không có tiêu chí nào
     */
    public boolean hasAnySearchCriteria() {
        return name != null || districtId != null || typeCode != null ||
               areaFrom != null || areaTo != null ||
               rentPriceFrom != null || rentPriceTo != null;
    }

    /**
     * Reset tất cả tiêu chí tìm kiếm
     */
    public void reset() {
        this.name = null;
        this.districtId = null;
        this.typeCode = null;
        this.areaFrom = null;
        this.areaTo = null;
        this.rentPriceFrom = null;
        this.rentPriceTo = null;
    }

    @Override
    public String toString() {
        return "BuildingSearchBuilder{" +
                "name='" + name + '\'' +
                ", districtId=" + districtId +
                ", typeCode='" + typeCode + '\'' +
                ", typeCodeList=" + typeCodeList +
                ", staffId=" + staffId +
                ", areaFrom=" + areaFrom +
                ", areaTo=" + areaTo +
                ", rentPriceFrom=" + rentPriceFrom +
                ", rentPriceTo=" + rentPriceTo +
                '}';
    }
}

