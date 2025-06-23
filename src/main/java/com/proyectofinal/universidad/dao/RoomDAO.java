/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.dao;

import com.proyectofinal.universidad.model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ecastillo
 */
public class RoomDAO {
    public int insert(Room e) throws SQLException, Exception {
        String sql = "INSERT INTO room "
                + "(name,capacity,location,startReserve,endReserve)"
                + "VALUES (?,?,?,?,?)";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getName());
            ps.setInt(2, e.getCapacity());
            ps.setString(3, e.getLocation());
            ps.setTimestamp(4, e.getStartReserve() != null ? Timestamp.valueOf(e.getStartReserve()) : null );
            ps.setTimestamp(5, e.getEndReserve() != null ? Timestamp.valueOf(e.getEndReserve()) : null );
            ps.executeUpdate(); // ← ejecuta primero
            try (ResultSet rs = ps.getGeneratedKeys()) { // ← después lee la PK
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }
    public List<Room> findAll() throws SQLException, Exception {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM room ORDER BY roomID";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }
    public boolean update(Room e) throws SQLException, Exception {
        String sql = "UPDATE room SET name=?,capacity=?,location=?,startReserve=?,"
                + "endReserve=? WHERE roomID=?";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setInt(2, e.getCapacity());
            ps.setString(3, e.getLocation());
            ps.setTimestamp(4, e.getStartReserve() != null ? Timestamp.valueOf(e.getStartReserve()) : null );
            ps.setTimestamp(5, e.getEndReserve() != null ? Timestamp.valueOf(e.getEndReserve()) : null );
            ps.setInt(6, e.getRoomID());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean delete(int id) throws SQLException, Exception {
        String sql = "DELETE FROM room WHERE roomID=?";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        }
    }
    private Room map(ResultSet rs) throws SQLException {
        Room e = new Room();
        e.setRoomID(rs.getInt("roomID"));
        e.setName(rs.getString("name"));
        e.setCapacity(rs.getInt("capacity"));
        e.setLocation(rs.getString("location"));
        Timestamp startTs = rs.getTimestamp("startReserve");
        e.setStartReserve(startTs != null ? startTs.toLocalDateTime() : null);
        Timestamp endTs = rs.getTimestamp("endReserve");
        e.setEndReserve(endTs != null ? endTs.toLocalDateTime() : null);
        return e;
    }
}
