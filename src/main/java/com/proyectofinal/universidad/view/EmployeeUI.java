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
    // CONSTRUCTOR
    // aqui se construye todo lo que tendrá la vista
    public EmployeeUI() throws Exception {
        /* ---------- ventana ---------- */
        setLayout(new BorderLayout());
        /* ---------- carga inicial ---------- */
        employeeService.refresh(); // mediante el metodo refresh del SERVICE fuerza actualizar el arrayList cache
        buildListPanel(); // construye la lista de paneles
        buildFormPanel(); // construye el panel de formulario
        add(root); // agrega panel al "DOM" de la interfaz
        cards.show(root, "LISTA"); // muestra layout con identificador LISTA en el DOM
    }

    /* ===================================================== */
    /* ===============   P A N E L   L I S T A   ============ */
    /* ===================================================== */
    private void buildListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        /* Define botones que existiran en este panel */
        JButton btnNew = new JButton("Nuevo"); // boton nuevo
        // al boton nuevo se agrega un listener (evento de escucha)
        // cuando se hace click en el, entonces realiza una accion o ejecuta un metodo
        btnNew.addActionListener(e -> { 
            clearForm(); // clearForm -> limpia los controles o inputs para un nuevo registro
            cards.show(root, "FORM"); // muestra layout con identificador FORM en el DOM
        });
        JButton btnUpd = new JButton("Actualizar");
        btnUpd.addActionListener(e -> { 
            clearForm();
            int row = table.getSelectedRow(); // obtiene fila seleccionada, -1 si no hay seleccion
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            getEmployee(row); // obtiene los datos del registro seleccionado segun su fila
            cards.show(root, "FORM");  // muestra layout con identificador FORM en el DOM
        });
        JButton btnDlt = new JButton("Eliminar");
        btnDlt.addActionListener(e -> { 
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            onDelete(row); // elimina registro
        });
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // agrega botones al panel
        top.add(btnNew);
        top.add(btnUpd);
        top.add(btnDlt);

        /* agrega controles al panel principal del UI*/
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        root.add(panel, "LISTA"); // agrega panel con identificador LISTA al DOM
        refreshTable(); // actualiza los datos de la tabla
    }

    /* ===================================================== */
    /* ===========   P A N E L   F O R M U L A R I O   ===== */
    /* ===================================================== */
    private void buildFormPanel() {

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.anchor = GridBagConstraints.WEST;
        // array de labels
        String[] labels = {
            "Nombre:", "Apellido:", "Nº Documento:", "Tipo Documento :",
            "Edad:", "Cumpleaños (yyyy-MM-dd):", "Salario:", "Puesto:", "Email:"
        };
        // array de inputs
        JTextField[] fields = { firstName,lastName,documentNumber,documentType,age,birthday,salary,position,email };
        // recorre array y agrega en el form en cada fila un label con su input
        for (int i=0;i<labels.length;i++) {
            c.gridx = 0; 
            c.gridy = i;
            form.add(new JLabel(labels[i]), c);
            c.gridx = 1;
            form.add(fields[i], c);
        }

        /* declaracion de botones y asignacion de eventos */
        JButton btnSave = new JButton("Guardar");
        btnSave.addActionListener(e -> {
            onSave();
        });

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
    // obtencion de empleado segun fila selecionada, buscando en el model de la tabla
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
        // asignación de id oculto para determinar si se registrara o actualizara el dato
        firstName.putClientProperty("editID", Integer.valueOf(model.getValueAt(row, 0).toString()));
        cards.show(root, "FORM"); // se muestra el card con identificador FORM
    }
    private void onSave() {
        // realiza el grabado, se usa try catch para controlar errores
        try {
            Employee e = new Employee(); // intancia de clase empleado
            // asignacion de valores a la instancia de acuerdo al contenido de los inputs
            e.setFirstName(firstName.getText().trim());
            e.setLastName(lastName.getText().trim());
            e.setDocumentNumber(documentNumber.getText().trim());
            e.setDocumentType(Integer.parseInt(documentType.getText().trim()));
            e.setAge(Integer.parseInt(age.getText().trim()));
            e.setBirthday(LocalDate.parse(birthday.getText().trim()));
            e.setSalary(Double.parseDouble(salary.getText().trim()));
            e.setPosition(position.getText().trim());
            e.setEmail(email.getText().trim());
            // obtencion del id oculto
            Object id = firstName.getClientProperty("editID");
            if (id == null) { // nuevo registro
                employeeService.create(e); // inserta en BD y cache (usando el metodo del SERVICE)
                JOptionPane.showMessageDialog(this, "Empleado creado.");
            } else { // actualizacion
                e.setEmployeeID((Integer) id);
                employeeService.update(e); // actualiza en BD (usando el metodo del SERVICE)
                firstName.putClientProperty("editID", null); // limpia id oculto
                JOptionPane.showMessageDialog(this,"Empleado actualizado");
            }
            refreshTable(); // refresca tabla
            clearForm(); // limpia formulario
            cards.show(root, "LISTA"); // muestra card LISTA
        } catch (Exception ex) {
            // en caso un error se muestra un mensaje
            JOptionPane.showMessageDialog(this,
                    "Error al guardar: "+ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onDelete(int row) {
        int id = (int) model.getValueAt(row, 0); // obtencion del id del registro seleccionado
        // validación de confirmación
        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar empleado "+id+"?", "Confirmar",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try {
                employeeService.delete(id); // elimina registro usando metodo delete del SERVICE
                refreshTable(); // refresca tabla
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /* ---------- utilidades ---------- */
    private void refreshTable() {
        model.setRowCount(0); // limpia datos
        Employee[] arr = employeeService.getAll(); // obtiene todos los registros y los asgina en array
        // itera o recorre todos los registros
        for (Employee e : arr) {
            // cada registro se agrega al model de la tabla a mostrar
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