package com.javaweb.model.entity;

public class RentAreaEntity {
    private Long id;
    private Long buildingId;
    private String value; // Giá trị diện tích (vd: "50-100m2", "100m2", ...)

    public RentAreaEntity() {
    }

    public RentAreaEntity(Long id, Long buildingId, String value) {
        this.id = id;
        this.buildingId = buildingId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

