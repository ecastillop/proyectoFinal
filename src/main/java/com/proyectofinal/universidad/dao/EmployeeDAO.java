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
    // metodo que registra en la tabla/entidad employee
    public int insert(Employee e) throws SQLException, Exception {
        // define instrucción SQL a ejecutarse (INSERT), cada ? es un parametro
        String sql = "INSERT INTO employee "
                + "(firstName,lastName,documentNumber,documentType,"
                + "age,birthday,salary,position,email)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        // establece conexion y prepara sentencia para ejecutar
        // try () : usado para manejar recursos, conexiones, etc
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // setea o asigna parametros con los valores requeridos segun orden definido en instrucción SQL
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getDocumentNumber());
            ps.setInt(4, e.getDocumentType());
            ps.setInt(5, e.getAge());
            ps.setDate(6, Date.valueOf(e.getBirthday()));
            ps.setDouble(7, e.getSalary());
            ps.setString(8, e.getPosition());
            ps.setString(9, e.getEmail());
            ps.executeUpdate(); // realiza la ejecución de SQL en la BD
            try (ResultSet rs = ps.getGeneratedKeys()) { // maneja recurso resultante de ejecución
                return rs.next() ? rs.getInt(1) : -1; // obtiene y devuelve ID generado
            }
        }
    }
    // metodo que obtiene todos los registros en la tabla/entidad employee
    public List<Employee> findAll() throws SQLException, Exception {
        List<Employee> list = new ArrayList<>(); // define arrayList employee
        String sql = "SELECT * FROM employee ORDER BY employeeID"; // define instrucción SQL (SELECT)
        // establece conexión y prepara sentencia para ejecutar
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            // bucle para iterar todos los registros y añadirlos en la lista a retornar
            while (rs.next()) {
                list.add(map(rs)); // añade registro a la lista
            }
        }
        return list; // retorna la lista
    }
    // metodo que actualiza registro especifico en la tabla/entidad employee
    public boolean update(Employee e) throws SQLException, Exception {
        // define instrucción SQL (UPDATE), cada ? es un parametro
        String sql = "UPDATE employee SET firstName=?,lastName=?,documentNumber=?,documentType=?,"
                + "age=?,birthday=?,salary=?,position=?,email=? WHERE employeeID=?";
        // establece conexion y prepara sentencia para ejecutar
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // setea o asigna los valores a los parametros segun orden definido en SQL
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
            // ejecuta y retorna true si se actualizo, false caso contrario
            return ps.executeUpdate() > 0;
        }
    }
    // metodo que elimina registro especifico de la tabla
    public boolean delete(int id) throws SQLException, Exception {
        // define instrucción SQL (DELETE), cada ? es un parametro
        String sql = "DELETE FROM employee WHERE employeeID=?";
        // establece conexion y prepara sentencia para ejecutar
        try (Connection c = Conexion.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // setea o asigna los valores a los parametros segun orden definido en SQL
            ps.setInt(1, id);
            // ejecuta y retorna true si se elimino, false caso contrario
            return ps.executeUpdate()>0;
        }
    }
    // mapeo de valores y devuelve clase employee correctamente seteado
    // uso de SET y GET
    private Employee map(ResultSet rs) throws SQLException {
        Employee e = new Employee(); // instancia
        // seteo de valores
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
        // retorna
        return e;
    }
}
