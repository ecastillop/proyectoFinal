/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.service;

import com.proyectofinal.universidad.dao.EmployeeDAO;
import com.proyectofinal.universidad.model.Employee;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ecastillo
 */
public class EmployeeService {
    private final EmployeeDAO dao = new EmployeeDAO();
    private final List<Employee> cache = new ArrayList<>();
    
    public void refresh() throws SQLException, Exception {
        cache.clear();
        cache.addAll(dao.findAll());
    }
    
    public Employee[] getAll() {
        return cache.toArray(new Employee[0]);
    }
    public Employee get(int id) {
        for (Employee e: cache) {
            if (e.getEmployeeID() == id) {
                return e;
            }
        }
        return null;
    }
    public int create(Employee e) throws SQLException, Exception {
        int idGenerado = dao.insert(e);
        e.setEmployeeID(idGenerado);
        cache.add(e);
        return idGenerado;
    }
    public boolean update(Employee e) throws SQLException, Exception {
        boolean bool = dao.update(e);
        if (bool){
            refresh();
        }
        return bool;
    }
    public boolean delete(int id) throws SQLException, Exception {
        boolean bool = dao.delete(id);
        if (bool) {
            refresh();
        }
        return bool;
    }
}
