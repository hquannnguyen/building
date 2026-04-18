package com.javaweb.model.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "building")
public class BuildingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "street")
	private String street;

	@Column(name = "ward")
	private String ward;

	@Column(name = "districtid")
	private Long districtId;

	@Column(name = "structure")
	private String structure;

	@Column(name = "numberofbasement")
	private Integer numberOfBasement;

	@Column(name = "floorarea")
	private Integer floorArea;

	@Column(name = "direction")
	private String direction;

	@Column(name = "level")
	private String level;

	@Column(name = "rentprice")
	private Integer rentPrice;

	@Column(name = "rentpricedescription")
	private String rentPriceDescription;

	@Column(name = "managername")
	private String managerName;

	@Column(name = "managerphonenumber")
	private String managerPhoneNumber;

	@Column(name = "brokeragefee")
	private Double brokerageFee;

	@Column(name = "emptyarea")
	private Integer emptyArea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createddate")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifieddate")
	private Date modifiedDate;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String modifiedBy;

	// --- Getters & Setters (giữ nguyên) ---

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Integer getNumberOfBasement() { return numberOfBasement; }
	public void setNumberOfBasement(Integer numberOfBasement) { this.numberOfBasement = numberOfBasement; }

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }

	public String getWard() { return ward; }
	public void setWard(String ward) { this.ward = ward; }

	public Long getDistrictId() { return districtId; }
	public void setDistrictId(Long districtId) { this.districtId = districtId; }

	public String getStructure() { return structure; }
	public void setStructure(String structure) { this.structure = structure; }

	public Integer getFloorArea() { return floorArea; }
	public void setFloorArea(Integer floorArea) { this.floorArea = floorArea; }

	public String getDirection() { return direction; }
	public void setDirection(String direction) { this.direction = direction; }

	public String getLevel() { return level; }
	public void setLevel(String level) { this.level = level; }

	public Integer getRentPrice() { return rentPrice; }
	public void setRentPrice(Integer rentPrice) { this.rentPrice = rentPrice; }

	public String getRentPriceDescription() { return rentPriceDescription; }
	public void setRentPriceDescription(String rentPriceDescription) { this.rentPriceDescription = rentPriceDescription; }

	public String getManagerName() { return managerName; }
	public void setManagerName(String managerName) { this.managerName = managerName; }

	public String getManagerPhoneNumber() { return managerPhoneNumber; }
	public void setManagerPhoneNumber(String managerPhoneNumber) { this.managerPhoneNumber = managerPhoneNumber; }

	public Date getCreatedDate() { return createdDate; }
	public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

	public Date getModifiedDate() { return modifiedDate; }
	public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; }

	public String getCreatedBy() { return createdBy; }
	public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

	public String getModifiedBy() { return modifiedBy; }
	public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

	public Double getBrokerageFee() { return brokerageFee; }
	public void setBrokerageFee(Double brokerageFee) { this.brokerageFee = brokerageFee; }

	public Integer getEmptyArea() { return emptyArea; }
	public void setEmptyArea(Integer emptyArea) { this.emptyArea = emptyArea; }
}