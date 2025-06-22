/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.universidad.view;

import com.proyectofinal.universidad.model.Employee;
import com.proyectofinal.universidad.service.EmployeeService;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
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
public class EmployeeUI extends JPanel {

    /* --------- servicios y modelo en memoria --------- */
    private final EmployeeService employeeService = new EmployeeService();

    /* --------- componentes globales --------- */
    private final CardLayout cards            = new CardLayout();
    private final JPanel     root             = new JPanel(cards);

    /* --- panel LISTA --- */
    private final String[]   cols             = {"ID","Nombre","Apellido","Doc","Tipo. Doc.", "Edad", "Nacimiento", "Salario", "Puesto", "E-mail"};
    private final DefaultTableModel model     = new DefaultTableModel(cols,0);
    private final JTable     table            = new JTable(model);

    /* --- panel FORMULARIO --- */
    private final JTextField firstName      = new JTextField(30);
    private final JTextField lastName       = new JTextField(30);
    private final JTextField documentNumber = new JTextField(10);
    private final JTextField documentType   = new JTextField(5);
    private final JTextField age            = new JTextField(5);
    private final JTextField birthday       = new JTextField(10);
    private final JTextField salary         = new JTextField(10);
    private final JTextField position       = new JTextField(30);
    private final JTextField email          = new JTextField(30);

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            try { new EmployeeUI().setVisible(true); }
            catch (Exception e) {}
        });
    }

    public EmployeeUI() throws Exception {
        /* ---------- ventana ---------- */
        setLayout(new BorderLayout());
        /* ---------- carga inicial ---------- */
        employeeService.refresh();
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
            getEmployee(row);
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

        String[] labels = {
            "Nombre:", "Apellido:", "Nº Documento:", "Tipo Documento :",
            "Edad:", "Cumpleaños (yyyy-MM-dd):", "Salario:", "Puesto:", "Email:"
        };
        JTextField[] fields = { firstName,lastName,documentNumber,documentType,age,birthday,salary,position,email };

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
    private void getEmployee(int row) {
        firstName.setText(model.getValueAt(row, 1).toString());
        lastName.setText(model.getValueAt(row, 2).toString());
        documentNumber.setText(model.getValueAt(row, 3).toString());
        documentType.setText(model.getValueAt(row, 4).toString());
        age.setText(model.getValueAt(row, 5).toString());
        birthday.setText(model.getValueAt(row, 6).toString());
        salary.setText(model.getValueAt(row, 7).toString());
        position.setText(model.getValueAt(row, 8).toString());
        email.setText(model.getValueAt(row, 9).toString());
        firstName.putClientProperty("editID", Integer.valueOf(model.getValueAt(row, 0).toString()));
        cards.show(root, "FORM");
    }
    private void onSave() {
        try {
            Employee e = new Employee();
            e.setFirstName(firstName.getText().trim());
            e.setLastName(lastName.getText().trim());
            e.setDocumentNumber(documentNumber.getText().trim());
            e.setDocumentType(Integer.parseInt(documentType.getText().trim()));
            e.setAge(Integer.parseInt(age.getText().trim()));
            e.setBirthday(LocalDate.parse(birthday.getText().trim()));
            e.setSalary(Double.parseDouble(salary.getText().trim()));
            e.setPosition(position.getText().trim());
            e.setEmail(email.getText().trim());

            Object id = firstName.getClientProperty("editID");
            if (id == null) { // nuevo
                employeeService.create(e); // inserta en BD y cache
                JOptionPane.showMessageDialog(this, "Empleado creado.");
            } else {
                e.setEmployeeID((Integer) id);
                employeeService.update(e);
                firstName.putClientProperty("editID", null); // limpia estado
                JOptionPane.showMessageDialog(this,"Empleado actualizado");
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
                "¿Eliminar empleado "+id+"?", "Confirmar",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try {
                employeeService.delete(id);
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /* ---------- utilidades ---------- */
    private void refreshTable() {
        model.setRowCount(0); // limpia datos
        Employee[] arr = employeeService.getAll(); // **Employee[] (Array)**
        for (Employee e : arr) {
            /* Object[][]: cada fila es Object[] → JTable model */
            model.addRow(new Object[]{
                    e.getEmployeeID(), 
                    e.getFirstName(),
                    e.getLastName(),   
                    e.getDocumentNumber(),
                    e.getDocumentType(),
                    e.getAge(),
                    e.getBirthday(),
                    e.getSalary(),
                    e.getPosition(),
                    e.getEmail()
            });
        }
    }
    private void clearForm() { // limpia inputs
        JTextField[] f = {firstName,lastName,documentNumber,documentType,age,birthday,salary,position,email};
        for (JTextField t : f) {
            t.setText("");
        }
    }
}