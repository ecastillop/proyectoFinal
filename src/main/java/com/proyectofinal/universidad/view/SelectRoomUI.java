/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.view;

import com.proyectofinal.universidad.model.Room;
import com.proyectofinal.universidad.service.RoomService;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ecastillo
 */
public class SelectRoomUI  extends JPanel {

    /* --------- servicios y modelo en memoria --------- */
    private final RoomService roomService = new RoomService();

    /* --------- componentes globales --------- */
    private final CardLayout cards            = new CardLayout();
    private final JPanel     root             = new JPanel(cards);

    /* --- panel LISTA --- */
    private final String[]   cols             = {"ID","Nombre","Capacidad","Ubicación"};
    private final DefaultTableModel model     = new DefaultTableModel(cols,0);
    private final JTable     table            = new JTable(model);

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            try { new RoomUI().setVisible(true); }
            catch (Exception e) {}
        });
    }
    // CONSTRUCTOR
    public SelectRoomUI() throws Exception {
        /* ---------- ventana ---------- */
        setLayout(new BorderLayout());
        /* ---------- carga inicial ---------- */
        roomService.refresh();
        buildListPanel();
        add(root);
        cards.show(root, "LISTA");
    }

    /* ===================================================== */
    /* ===============   P A N E L   L I S T A   ============ */
    /* ===================================================== */
    private void buildListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        /* — Botonera — */
        JButton btnSelect = new JButton("Seleccionar");
        btnSelect.addActionListener(e -> { 
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            onSelect(row);
            cards.show(root, "FORM"); 
        });
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnSelect);

        /* — Tabla — */
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        root.add(panel, "LISTA");
        refreshTable();
    }
    private void onSelect(int row) {
        
    }
    /* ---------- utilidades ---------- */
    private void refreshTable() {
        model.setRowCount(0); // limpia datos
        Room[] arr = roomService.getAll();
        for (Room e : arr) {
            model.addRow(new Object[]{
                    e.getRoomID(), 
                    e.getName(),
                    e.getCapacity(),   
                    e.getLocation()
            });
        }
    }
}
