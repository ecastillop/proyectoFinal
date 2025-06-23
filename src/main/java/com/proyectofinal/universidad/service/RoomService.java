/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.service;

import com.proyectofinal.universidad.dao.EmployeeDAO;
import com.proyectofinal.universidad.dao.RoomDAO;
import com.proyectofinal.universidad.model.Employee;
import com.proyectofinal.universidad.model.Room;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ecastillo
 */
public class RoomService {
    private final RoomDAO dao = new RoomDAO();
    private final List<Room> cache = new ArrayList<>();
    
    public void refresh() throws SQLException, Exception {
        cache.clear();
        cache.addAll(dao.findAll());
    }
    
    public Room[] getAll() {
        return cache.toArray(new Room[0]);
    }
    public Room get(int id) {
        for (Room e: cache) {
            if (e.getRoomID() == id) {
                return e;
            }
        }
        return null;
    }
    public int create(Room e) throws SQLException, Exception {
        int idGenerado = dao.insert(e);
        e.setRoomID(idGenerado);
        cache.add(e);
        return idGenerado;
    }
    public boolean update(Room e) throws SQLException, Exception {
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
    
    // METODOS
    /*buscar si la sala se encuentra programada en horario (start)
    * retornar true si no esta programada, false si se encuentra programada*/
    public boolean checkAvailability(LocalDateTime start) {
        return false;
    }
    /*asigna reserva por 5 minutos (calcula inicio, final); retorna true en caso exito*/
    public boolean reserveTemp(int minute) {
        return false;
    }
    /*libera la sala, es decir remueve reserva a nivel sala*/
    public boolean release() {
        return false;
    }   
}
