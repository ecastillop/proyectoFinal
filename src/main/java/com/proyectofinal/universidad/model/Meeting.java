/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.model;

import java.time.LocalDateTime;

/**
 *
 * @author ecastillo
 */
public class Meeting {
    // PROPIEDADES (normalmente [private tipoDato nombrePropiedad;])
    private int meetingID;
    private String subject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Room room;
    private Employee employee;
    
    // SETTER => public void setPropiedad(tipoDato parametro) { propiedad = parametro; }
    // GETTER => public tipoDato getPropiedad() { return propiedad; }
    public void setMeetingID(int val) { this.meetingID = val; }
    public int getMeetingID() { return this.meetingID; }
    public void setSubject(String val) { this.subject = val; }
    public String getSubject() { return this.subject; }
    public void setStartTime(LocalDateTime val) { this.startTime = val; }
    public LocalDateTime getStartTime() { return this.startTime; }
    public void setEndTime(LocalDateTime val) { this.endTime = val; }
    public LocalDateTime getEndTime() { return this.endTime; }
    public void setRoom(Room val) { this.room = val; }
    public Room getRoom() { return this.room; }
    public void setEmployee(Employee val) { this.employee = val; }
    public Employee getEmployee() { return this.employee; }
}
