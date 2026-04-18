package com.javaweb.repository;

import com.javaweb.model.entity.BuildingEntity;
import java.util.List;

public interface BuildingRepository {
    List<BuildingEntity> findAll(String name, Long districtId, String typeCode, Integer areaFrom, Integer areaTo, Integer rentPriceFrom, Integer rentPriceTo);
}