package com.javaweb.service.impl;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.entity.BuildingEntity;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.service.BuildingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final DistrictRepository districtRepository;
    private final RentAreaRepository rentAreaRepository;
    private final ModelMapper modelMapper;
    private final BuildingDTOConverter buildingDTOConverter;

    public BuildingServiceImpl(BuildingRepository buildingRepository, DistrictRepository districtRepository, 
                              RentAreaRepository rentAreaRepository, ModelMapper modelMapper) {
        this.buildingRepository = buildingRepository;
        this.districtRepository = districtRepository;
        this.rentAreaRepository = rentAreaRepository;
        this.modelMapper = modelMapper;
        this.buildingDTOConverter = new BuildingDTOConverter(modelMapper, districtRepository, rentAreaRepository);
    }

    @Override
    public List<BuildingDTO> findAll(String name, Long districtId, String typeCode, Integer areaFrom, Integer areaTo, Integer rentPriceFrom, Integer rentPriceTo) {
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(name, districtId, typeCode, areaFrom, areaTo, rentPriceFrom, rentPriceTo);
        List<BuildingDTO> result = new ArrayList<>();
        for (BuildingEntity item : buildingEntities) {
            BuildingDTO buildingDTO = buildingDTOConverter.toDTO(item);
            result.add(buildingDTO);
        }
        return result;
    }
}