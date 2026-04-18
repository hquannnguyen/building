package com.javaweb.converter;

import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.entity.BuildingEntity;
import com.javaweb.model.entity.DistrictEntity;
import com.javaweb.model.entity.RentAreaEntity;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingDTOConverter {
    
    private final ModelMapper modelMapper;
    private final DistrictRepository districtRepository;
    private final RentAreaRepository rentAreaRepository;

    public BuildingDTOConverter(ModelMapper modelMapper, DistrictRepository districtRepository, RentAreaRepository rentAreaRepository) {
        this.modelMapper = modelMapper;
        this.districtRepository = districtRepository;
        this.rentAreaRepository = rentAreaRepository;
    }

    public BuildingDTO toDTO(BuildingEntity entity) {
        if (entity == null) return null;
        
        // Sử dụng ModelMapper để mapping các trường cơ bản
        BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class);
        
        // Xử lý trường address: gộp street + ward + district name
        StringBuilder addressBuilder = new StringBuilder();
        if (entity.getStreet() != null) {
            addressBuilder.append(entity.getStreet());
        }
        if (entity.getWard() != null) {
            if (addressBuilder.length() > 0) addressBuilder.append(", ");
            addressBuilder.append(entity.getWard());
        }
        if (entity.getDistrictId() != null) {
            DistrictEntity districtEntity = districtRepository.findNameById(entity.getDistrictId());
            if (districtEntity != null && districtEntity.getName() != null) {
                if (addressBuilder.length() > 0) addressBuilder.append(", ");
                addressBuilder.append(districtEntity.getName());
            }
        }
        dto.setAddress(addressBuilder.toString());
        
        // Xử lý trường rentArea: lấy từ rentAreaRepository và gộp
        if (entity.getId() != null) {
            List<RentAreaEntity> rentAreas = rentAreaRepository.getValueByBuildingId(entity.getId());
            String areaResult = rentAreas.stream()
                    .map(RentAreaEntity::getValue)
                    .collect(Collectors.joining(","));
            if (!areaResult.isEmpty()) {
                dto.setRentArea(areaResult);
            }
        }
        
        return dto;
    }

    public BuildingEntity toEntity(BuildingDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, BuildingEntity.class);
    }
}
