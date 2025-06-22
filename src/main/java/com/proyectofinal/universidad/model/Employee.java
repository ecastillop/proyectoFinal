/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.model;

import java.time.LocalDate;

/**
 *
 * @author ecastillo
 */
public class Employee {
    // PROPIEDADES (normalmente [private tipoDato nombrePropiedad;])
    private int employeeID;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private int documentType;
    private int age;
    private LocalDate birthday;
    private double salary;
    private String position;
    private String email;
    
    // SETTER => public void setPropiedad(tipoDato parametro) { propiedad = parametro; }
    // GETTER => public tipoDato getPropiedad() { return propiedad; }
    public void setEmployeeID(int val) { this.employeeID = val; }
    public int getEmployeeID(){ return this.employeeID; }
    public void setFirstName(String val) { this.firstName = val; }
    public String getFirstName() { return this.firstName; }
    public void setLastName(String val) { this.lastName = val; }
    public String getLastName() { return this.lastName; }
    public void setDocumentNumber(String val) { this.documentNumber = val; }
    public String getDocumentNumber() { return this.documentNumber; }
    public void setDocumentType(int val) { this.documentType = val; }
    public int getDocumentType() { return this.documentType; }
    public void setAge(int val) { this.age = val; }
    public int getAge() { return this.age; }
    public void setBirthday(LocalDate val) { this.birthday = val; }
    public LocalDate getBirthday() { return this.birthday; }
    public void setSalary(double val) { this.salary = val; }
    public double getSalary() { return this.salary; }
    public void setPosition(String val) { this.position = val; }
    public String getPosition() { return this.position; }
    public void setEmail(String val) { this.email = val; }
    public String getEmail() { return this.email; }
}
