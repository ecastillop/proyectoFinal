/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.service;

import com.proyectofinal.universidad.model.Employee;
import com.proyectofinal.universidad.model.Meeting;
import com.proyectofinal.universidad.model.Room;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ecastillo
 */
public class MeetingService {  
    //realiza la programacion
    public boolean schedule() {
        return false;
    }
    // realiza la reprogramacion
    public boolean reschedule(int id, LocalDateTime start, LocalDateTime end) {
        return false;
    }
    // cancela reunion, libera sala
    public boolean cancel(int id) {
        return false;
    }
    // verifica si hay conflictos con otra reunion (sala, horario, etc)
    public boolean conflictsWithOther(Meeting meeting) {
        return false;
    }
    // crea reunion
    public Meeting createMeeting(Employee emp, 
            Room room, 
            String subject, 
            LocalDateTime start, 
            LocalDateTime end) {
        Meeting meet = new Meeting();
        return meet;
    }
    // selecciona sala, verifica disponibilidad, reserva temporal
    public Room selectRoom() {
        Room room = new Room();
        return room;
    }
    // obtiene lista de programaciones de empleado
    public List<Meeting> getSchedules(int id) {
        List<Meeting> lista = new ArrayList<>();
        return lista;
    }
    // retorna lista tipo reporte de hora pico en X dia
    public List<Object[]> peakHourMeetings(LocalDateTime day) {
        List<Object[]> list = null;
        return list;
    }
}
