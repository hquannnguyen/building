package com.javaweb.service;

import com.javaweb.model.dto.BuildingDTO;
import java.util.List;

public interface BuildingService {
    List<BuildingDTO> findAll(String name, Long districtId, String typeCode, Integer areaFrom, Integer areaTo, Integer rentPriceFrom, Integer rentPriceTo);
}