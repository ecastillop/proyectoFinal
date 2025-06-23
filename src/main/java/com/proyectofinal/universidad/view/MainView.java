/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.view;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ecastillo
 */
public class MainView extends JFrame {

    /* ---------- CardLayout y panel central ---------- */
    private final CardLayout cards  = new CardLayout();
    private final JPanel     center = new JPanel(cards);

    /* ---------- Paneles (módulos) ---------- */
    private final JPanel pnlEmployee; // CRUD de empleados
    private final JPanel pnlRoom;
    private final JPanel pnlCreateMt   = new PlaceholderPane("Creación de Reunión");
    private final JPanel pnlSelRoom    = new PlaceholderPane("Selección de Sala");
    private final JPanel pnlSchedule   = new PlaceholderPane("Programaciones");

    public MainView() {
        super("Sistema de Reuniones");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        /* ----- CRUD de Empleado ----- */
        JPanel tmpEmployee;
        try {
            tmpEmployee = new EmployeeUI(); // CRUD convertido a JPanel
        } catch (Exception ex) { // control de error
            tmpEmployee = new PlaceholderPane(
                    "<html>Error cargando módulo Empleado:<br>"
                  + ex.getMessage() + "</html>");
        }
        pnlEmployee = tmpEmployee;
        /* ----- CRUD de Sala ----- */
        JPanel tmpSala;
        try {
            tmpSala = new RoomUI(); // CRUD convertido a JPanel
        } catch (Exception ex) { // control de error
            tmpSala = new PlaceholderPane(
                    "<html>Error cargando módulo Sala:<br>"
                  + ex.getMessage() + "</html>");
        }
        pnlRoom = tmpSala;

        buildLeftMenu(); // primero el menú
        buildCenterCards(); // después las tarjetas
    }

    /* ==========================================================
       ==============  MENÚ LATERAL DE BOTONES  =================
       ========================================================== */
    private void buildLeftMenu() {
        JPanel menu = new JPanel(new GridLayout(0, 1, 5, 5));
        menu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        menu.add(navButton("Mantenimiento Empleado", "empleado"));
        menu.add(navButton("Mantenimiento Sala", "sala"));
        menu.add(navButton("Creación Reunión", "creaReunion"));
        menu.add(navButton("Selección Sala", "seleccionSala"));
        menu.add(navButton("Ver Programaciones", "programacion"));

        getContentPane().add(menu, BorderLayout.WEST);
    }

    /* Crea un botón que muestra la tarjeta indicada */
    private JButton navButton(String text, String cardKey) {
        JButton b = new JButton(text);
        b.addActionListener(e -> cards.show(center, cardKey));
        return b;
    }

    /* ==========================================================
       ==============  ÁREA CENTRAL (CardLayout)  ===============
       ========================================================== */
    private void buildCenterCards() {
        center.add(pnlEmployee, "empleado");
        center.add(pnlRoom, "sala");
        center.add(pnlCreateMt, "creaReunion");
        center.add(pnlSelRoom, "seleccionSala");
        center.add(pnlSchedule, "programacion");

        getContentPane().add(center, BorderLayout.CENTER);
        cards.show(center, "empleado");  // vista inicial
    }

    /* ==========================================================
       ==============  PLACEHOLDER INTERNO  =====================
       ========================================================== */
    private static class PlaceholderPane extends JPanel {
        PlaceholderPane(String htmlTitle) {
            setLayout(new GridBagLayout());
            JLabel lbl = new JLabel(htmlTitle, SwingConstants.CENTER);
            add(lbl);
        }
    }
}
