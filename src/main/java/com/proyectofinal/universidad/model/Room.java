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
public class Room {
    // PROPIEDADES (normalmente [private tipoDato nombrePropiedad;])
    private int roomID;
    private String name;
    private int capacity;
    private String location;
    private LocalDateTime startReserve;
    private LocalDateTime endReserve;
    
    // SETTER => public void setPropiedad(tipoDato parametro) { propiedad = parametro; }
    // GETTER => public tipoDato getPropiedad() { return propiedad; }
    public void setRoomID(int val) { this.roomID = val; }
    public int getRoomID() { return this.roomID; }
    public void setName(String val) { this.name = val; }
    public String getName() { return this.name; }
    public void setCapacity(int val) { this.capacity = val; }
    public int getCapacity() { return this.capacity; }
    public void setLocation(String val) { this.location = val; }
    public String getLocation() { return this.location; }
    public void setStartReserve(LocalDateTime val) { this.startReserve = val; }
    public LocalDateTime getStartReserve() { return this.startReserve; }
    public void setEndReserve(LocalDateTime val) { this.endReserve = val; }
    public LocalDateTime getEndReserve() { return this.endReserve; }
}
