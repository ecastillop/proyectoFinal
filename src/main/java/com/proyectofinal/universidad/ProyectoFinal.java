/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyectofinal.universidad;

import com.proyectofinal.universidad.view.MainView;
import javax.swing.SwingUtilities;

/**
 *
 * @author ecastillo
 */
public class ProyectoFinal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
