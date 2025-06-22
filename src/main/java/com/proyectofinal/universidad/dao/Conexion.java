/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author ecastillo
 */
public class Conexion {
    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = Conexion.class.getClassLoader().getResourceAsStream("application.yml");
            Map<String, Object> config = yaml.load(inputStream);
            Map<String, String> db = (Map<String, String>) config.get("database");

            url = db.get("url");
            username = db.get("username");
            password = db.get("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Conexión exitosa a Azure SQL Server.");
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }
}
