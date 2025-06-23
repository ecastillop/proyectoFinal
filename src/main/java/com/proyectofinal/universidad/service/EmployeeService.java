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
    // instancia de clase EmpleadoDAO (donde estan los metodos que interactuan con la BD
    private final EmployeeDAO dao = new EmployeeDAO(); 
    // definicion de arrayList que actuara de cache para mostrar los datos en pantalla
    private final List<Employee> cache = new ArrayList<>();
    
    // metodo que limpiara el arrayList (cache) y agregará todos los regitros de la BD
    public void refresh() throws SQLException, Exception {
        cache.clear(); // limpia
        cache.addAll(dao.findAll()); // agrega al arrayList todos los datos obteneidos en findAll() del DAO;
    }
    // metodo que obtiene todos los registros y lo retorna en array
    public Employee[] getAll() {
        return cache.toArray(new Employee[0]);
    }
    // metodo que obtiene un unico registro por su ID buscando dicho ID en el array
    public Employee get(int id) {
        for (Employee e: cache) {
            if (e.getEmployeeID() == id) {
                return e;
            }
        }
        return null;
    }
    // metodo para crear nuevo registro, hace uso del metodo insert en el DAO, luego lo agrega al arrayList
    public int create(Employee e) throws SQLException, Exception {
        int idGenerado = dao.insert(e);
        e.setEmployeeID(idGenerado);
        cache.add(e);
        return idGenerado;
    }
    // metodo para actualizar un registro, usa el metodo update del DAO y luego refresca el arrayList
    public boolean update(Employee e) throws SQLException, Exception {
        boolean bool = dao.update(e);
        if (bool){
            refresh();
        }
        return bool;
    }
    // metodo para eliminar un registro, usa el metodo delete del DAO, luego refresca arrayList
    public boolean delete(int id) throws SQLException, Exception {
        boolean bool = dao.delete(id);
        if (bool) {
            refresh();
        }
        return bool;
    }
}
