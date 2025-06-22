/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.service;

import java.time.LocalDateTime;

/**
 *
 * @author ecastillo
 */
public class RoomService {
    
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
