package com.javaweb.repository;

import com.javaweb.model.entity.RentAreaEntity;
import java.util.List;

public interface RentAreaRepository {
    List<RentAreaEntity> getValueByBuildingId(Long buildingId);
}

