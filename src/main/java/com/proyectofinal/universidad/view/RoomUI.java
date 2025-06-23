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
public class RoomUI  extends JPanel {

    /* --------- servicios y modelo en memoria --------- */
    private final RoomService roomService = new RoomService();

    /* --------- componentes globales --------- */
    private final CardLayout cards            = new CardLayout();
    private final JPanel     root             = new JPanel(cards);

    /* --- panel LISTA --- */
    private final String[]   cols             = {"ID","Nombre","Capacidad","Ubicación"};
    private final DefaultTableModel model     = new DefaultTableModel(cols,0);
    private final JTable     table            = new JTable(model);

    /* --- panel FORMULARIO --- */
    private final JTextField name      = new JTextField(30);
    private final JTextField capacity       = new JTextField(30);
    private final JTextField location = new JTextField(10);

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            try { new RoomUI().setVisible(true); }
            catch (Exception e) {}
        });
    }
    // CONSTRUCTOR
    public RoomUI() throws Exception {
        /* ---------- ventana ---------- */
        setLayout(new BorderLayout());
        /* ---------- carga inicial ---------- */
        roomService.refresh();
        buildListPanel();
        buildFormPanel();
        add(root);
        cards.show(root, "LISTA");
    }

    /* ===================================================== */
    /* ===============   P A N E L   L I S T A   ============ */
    /* ===================================================== */
    private void buildListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        /* — Botonera — */
        JButton btnNew = new JButton("Nuevo");
        btnNew.addActionListener(e -> { 
            clearForm(); 
            cards.show(root, "FORM"); 
        });
        JButton btnUpd = new JButton("Actualizar");
        btnUpd.addActionListener(e -> { 
            clearForm();
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            getRoom(row);
            cards.show(root, "FORM"); 
        });
        JButton btnDlt = new JButton("Eliminar");
        btnDlt.addActionListener(e -> { 
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            onDelete(row);
        });
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnNew);
        top.add(btnUpd);
        top.add(btnDlt);

        /* — Tabla — */
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        root.add(panel, "LISTA");
        refreshTable();
    }

    /* ===================================================== */
    /* ===========   P A N E L   F O R M U L A R I O   ===== */
    /* ===================================================== */
    private void buildFormPanel() {

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.anchor = GridBagConstraints.WEST;

        String[] labels = { "Nombre:", "Capacidad:", "Ubicación:" };
        JTextField[] fields = { name,capacity,location };

        for (int i=0;i<labels.length;i++) {
            c.gridx = 0; c.gridy = i;
            form.add(new JLabel(labels[i]), c);
            c.gridx = 1;
            form.add(fields[i], c);
        }

        /* -------- Botones Guardar / Cancelar -------- */
        JButton btnSave = new JButton("Guardar");
        btnSave.addActionListener(e -> onSave());

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> {
            clearForm();
            cards.show(root, "LISTA");
        });

        JPanel south = new JPanel();
        south.add(btnSave); 
        south.add(btnCancel);

        JPanel container = new JPanel(new BorderLayout());
        container.add(form, BorderLayout.CENTER);
        container.add(south, BorderLayout.SOUTH);

        root.add(container, "FORM");
    }

    /* ===================================================== */
    /* =================   L Ó G I C A   =================== */
    /* ===================================================== */
    private void getRoom(int row) {
        name.setText(model.getValueAt(row, 1).toString());
        capacity.setText(model.getValueAt(row, 2).toString());
        location.setText(model.getValueAt(row, 3).toString());
        name.putClientProperty("editID", Integer.valueOf(model.getValueAt(row, 0).toString()));
        cards.show(root, "FORM");
    }
    private void onSave() {
        try {
            Room e = new Room();
            e.setName(name.getText().trim());
            e.setCapacity(Integer.parseInt(capacity.getText().trim()));
            e.setLocation(location.getText().trim());

            Object id = name.getClientProperty("editID");
            if (id == null) { // nuevo
                roomService.create(e); // inserta en BD y cache
                JOptionPane.showMessageDialog(this, "Sala creada.");
            } else {
                e.setRoomID((Integer) id);
                roomService.update(e);
                name.putClientProperty("editID", null); // limpia estado
                JOptionPane.showMessageDialog(this,"Sala actualizada");
            }
            refreshTable();
            clearForm();
            cards.show(root, "LISTA");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar: "+ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onDelete(int row) {
        int id = (int) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar sala "+id+"?", "Confirmar",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try {
                roomService.delete(id);
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
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
    private void clearForm() { // limpia inputs
        JTextField[] f = {name,capacity,location};
        for (JTextField t : f) {
            t.setText("");
        }
    }
}