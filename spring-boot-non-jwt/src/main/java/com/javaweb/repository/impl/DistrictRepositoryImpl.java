package com.javaweb.repository.impl;

import com.javaweb.model.entity.DistrictEntity;
import com.javaweb.repository.DistrictRepository;
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
public class DistrictRepositoryImpl implements DistrictRepository {
	@Value("${spring.datasource.url}") 
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public DistrictEntity findNameById(Long id) {
        String sql = "SELECT * FROM district WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DistrictEntity district = new DistrictEntity();
                    district.setId(rs.getLong("id"));
                    district.setName(rs.getString("name"));
                    district.setCode(rs.getString("code"));
                    return district;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying district: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<DistrictEntity> findAll() {
        String sql = "SELECT * FROM district";
        List<DistrictEntity> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DistrictEntity district = new DistrictEntity();
                    district.setId(rs.getLong("id"));
                    district.setName(rs.getString("name"));
                    district.setCode(rs.getString("code"));
                    result.add(district);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying district: " + e.getMessage());
        }
        return result;
    }
}

