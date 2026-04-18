package com.javaweb.repository.impl;

import com.javaweb.model.entity.RentAreaEntity;
import com.javaweb.repository.RentAreaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {
	@Value("${spring.datasource.url}") 
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public List<RentAreaEntity> getValueByBuildingId(Long buildingId) {
        String sql = "SELECT * FROM rentarea WHERE buildingid = ?";
        List<RentAreaEntity> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, buildingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RentAreaEntity rentArea = new RentAreaEntity();
                    rentArea.setId(rs.getLong("id"));
                    rentArea.setBuildingId(rs.getLong("buildingid"));
                    rentArea.setValue(rs.getString("value"));
                    result.add(rentArea);
                }
            }
        } catch (SQLException e) {
            // Bảng rentarea không tồn tại hoặc lỗi khác, trả về danh sách rỗng
            System.err.println("Error querying rentarea: " + e.getMessage());
        }
        return result;
    }
}

