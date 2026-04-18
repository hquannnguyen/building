package com.javaweb.api;

import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.search.BuildingSearchBuilder;
import com.javaweb.service.BuildingService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BuildingAPI {

    private final BuildingService buildingService;

    // Sử dụng Constructor Injection thay vì @Autowired lên field (Tuân thủ Dependency Inversion - SOLID)
    public BuildingAPI(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping(value = "/api/building/")
    public List<BuildingDTO> getBuilding(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "districtid", required = false) Long districtId,
                                         @RequestParam(value = "typecode", required = false) String typeCode,
                                         @RequestParam(value = "areaFrom", required = false) Integer areaFrom,
                                         @RequestParam(value = "areaTo", required = false) Integer areaTo,
                                         @RequestParam(value = "rentPriceFrom", required = false) Integer rentPriceFrom,
                                         @RequestParam(value = "rentPriceTo", required = false) Integer rentPriceTo)
    {
        // Xây dựng tiêu chí tìm kiếm sử dụng Builder Pattern
        BuildingSearchBuilder searchBuilder = BuildingSearchBuilder.builder()
                .name(name)
                .districtId(districtId)
                .typeCode(typeCode)
                .areaFrom(areaFrom)
                .areaTo(areaTo)
                .rentPriceFrom(rentPriceFrom)
                .rentPriceTo(rentPriceTo)
                .build();

        // Chỉ gọi Service xử lý, không xử lý logic tại đây
        return buildingService.findAll(
                searchBuilder.getName(),
                searchBuilder.getDistrictId(),
                searchBuilder.getTypeCode(),
                searchBuilder.getAreaFrom(),
                searchBuilder.getAreaTo(),
                searchBuilder.getRentPriceFrom(),
                searchBuilder.getRentPriceTo()
        );
    }
}