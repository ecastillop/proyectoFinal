/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.dao;

import com.proyectofinal.universidad.model.Employee;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ecastillo
 */
public class EmployeeDAO {
    public int insert(Employee e) throws SQLException, Exception {
        String sql = "INSERT INTO employee "
                + "(firstName,lastName,documentNumber,documentType,"
                + "age,birthday,salary,position,email)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getDocumentNumber());
            ps.setInt(4, e.getDocumentType());
            ps.setInt(5, e.getAge());
            ps.setDate(6, Date.valueOf(e.getBirthday()));
            ps.setDouble(7, e.getSalary());
            ps.setString(8, e.getPosition());
            ps.setString(9, e.getEmail());
            ps.executeUpdate();                              // ← ejecuta primero
            try (ResultSet rs = ps.getGeneratedKeys()) {     // ← después lee la PK
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }
    public List<Employee> findAll() throws SQLException, Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee ORDER BY employeeID";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }
    public boolean update(Employee e) throws SQLException, Exception {
        String sql = "UPDATE employee SET firstName=?,lastName=?,documentNumber=?,documentType=?,"
                + "age=?,birthday=?,salary=?,position=?,email=? WHERE employeeID=?";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getDocumentNumber());
            ps.setInt(4, e.getDocumentType());
            ps.setInt(5, e.getAge());
            ps.setDate(6, Date.valueOf(e.getBirthday()));
            ps.setDouble(7, e.getSalary());
            ps.setString(8, e.getPosition());
            ps.setString(9, e.getEmail());
            ps.setInt(10, e.getEmployeeID());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean delete(int id) throws SQLException, Exception {
        String sql = "DELETE FROM employee WHERE employeeID=?";
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        }
    }
    private Employee map(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmployeeID(rs.getInt("employeeID"));
        e.setFirstName(rs.getString("firstName"));
        e.setLastName(rs.getString("lastName"));
        e.setDocumentNumber(rs.getString("documentNumber"));
        e.setDocumentType(rs.getInt("documentType"));
        e.setAge(rs.getInt("age"));
        e.setBirthday(rs.getDate("birthday").toLocalDate());
        e.setSalary(rs.getDouble("salary"));
        e.setPosition(rs.getString("position"));
        e.setEmail(rs.getString("email"));
        return e;
    }
}
