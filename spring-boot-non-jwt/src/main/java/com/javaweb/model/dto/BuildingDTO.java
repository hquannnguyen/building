package com.javaweb.model.dto;
public class BuildingDTO {
    private String name;
    private String address; // Gộp từ street + ward + district để hiển thị đẹp hơn
    private Integer numberOfBasement;
    private Integer floorArea;
    private Integer rentPrice;
    private String managerName;
    private String managerPhoneNumber;
    private String rentArea; // Các diện tích cho thuê, gộp từ rentAreas (được join bằng dấu phẩy)
    private Double brokerageFee;
    private Integer emptyArea;

    // Constructor mặc định
    public BuildingDTO() {
    }

    // Constructor private cho Builder Pattern
    private BuildingDTO(Builder builder) {
        this.name = builder.name;
        this.address = builder.address;
        this.numberOfBasement = builder.numberOfBasement;
        this.floorArea = builder.floorArea;
        this.rentPrice = builder.rentPrice;
        this.managerName = builder.managerName;
        this.managerPhoneNumber = builder.managerPhoneNumber;
        this.rentArea = builder.rentArea;
        this.brokerageFee = builder.brokerageFee;
        this.emptyArea = builder.emptyArea;
    }

    // Static method để tạo Builder
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String name;
        private String address;
        private Integer numberOfBasement;
        private Integer floorArea;
        private Integer rentPrice;
        private String managerName;
        private String managerPhoneNumber;
        private String rentArea;
        private Double brokerageFee;
        private Integer emptyArea;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder numberOfBasement(Integer numberOfBasement) {
            this.numberOfBasement = numberOfBasement;
            return this;
        }

        public Builder floorArea(Integer floorArea) {
            this.floorArea = floorArea;
            return this;
        }

        public Builder rentPrice(Integer rentPrice) {
            this.rentPrice = rentPrice;
            return this;
        }

        public Builder managerName(String managerName) {
            this.managerName = managerName;
            return this;
        }

        public Builder managerPhoneNumber(String managerPhoneNumber) {
            this.managerPhoneNumber = managerPhoneNumber;
            return this;
        }

        public Builder rentArea(String rentArea) {
            this.rentArea = rentArea;
            return this;
        }

        public Builder brokerageFee(Double brokerageFee) {
            this.brokerageFee = brokerageFee;
            return this;
        }

        public Builder emptyArea(Integer emptyArea) {
            this.emptyArea = emptyArea;
            return this;
        }

        public BuildingDTO build() {
            return new BuildingDTO(this);
        }
    }

    // Getter và Setter (Lưu ý: Getter phải không có tham số đầu vào)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfBasement() {
        return numberOfBasement;
    }

    public void setNumberOfBasement(Integer numberOfBasement) {
        this.numberOfBasement = numberOfBasement;
    }

	public Integer getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}

	public Integer getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}

	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}

	public String getRentArea() {
		return rentArea;
	}

	public void setRentArea(String rentArea) {
		this.rentArea = rentArea;
	}

	public Double getBrokerageFee() {
		return brokerageFee;
	}

	public void setBrokerageFee(Double brokerageFee) {
		this.brokerageFee = brokerageFee;
	}

	public Integer getEmptyArea() {
		return emptyArea;
	}

	public void setEmptyArea(Integer emptyArea) {
		this.emptyArea = emptyArea;
	}
}