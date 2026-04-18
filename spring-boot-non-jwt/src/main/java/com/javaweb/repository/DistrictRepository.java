package com.javaweb.repository;

import com.javaweb.model.entity.DistrictEntity;
import java.util.List;

public interface DistrictRepository {
    DistrictEntity findNameById(Long id);
    List<DistrictEntity> findAll();
}

