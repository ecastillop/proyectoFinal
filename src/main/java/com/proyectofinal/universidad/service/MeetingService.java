/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.service;

import com.proyectofinal.universidad.model.Meeting;

/**
 *
 * @author ecastillo
 */
public class MeetingService {  
    //realiza a programacion
    public boolean schedule() {
        return false;
    }
    // realiza la reprogramacion
    public boolean reschedule() {
        return false;
    }
    // cancela reunion, libera sala
    public boolean cancel() {
        return false;
    }
    // verifica si hay conflictos con otra reunion (sala, horario, etc)
    public boolean conflictsWithOther(Meeting meeting) {
        return false;
    }
}
