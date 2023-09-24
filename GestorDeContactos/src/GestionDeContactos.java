import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GestionDeContactos {
    private JPanel panel;
    private JTable table1;
    private JTextField textNombre;
    private JTextField textEmail;
    private JButton buttonAgregar;
    private JButton buttonEditar;
    private JButton buttonEliminar;

    public JPanel getPanel() {
        return panel;
    }

    private DefaultTableModel modeloTabla;

    public GestionDeContactos(){

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Email");
        table1 = new JTable(modeloTabla);
        buttonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textNombre.getText();
                String email = textEmail.getText();
                if (!nombre.isEmpty() && !email.isEmpty()) {
                    Vector<String> fila = new Vector<>();
                    fila.add(nombre);
                    fila.add(email);
                    modeloTabla.addRow(fila);
                    textNombre.setText("");
                    textEmail.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "Por favor, ingrese nombre y email.");
                }
            }
        });
    }
}
