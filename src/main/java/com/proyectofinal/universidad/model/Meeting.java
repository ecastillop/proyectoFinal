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
    private int meetingID;
    private String subject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Room room;
    private Employee employee;
    
    public void setMeetingID(int val) { this.meetingID = val; }
}
