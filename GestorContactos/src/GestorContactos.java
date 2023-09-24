import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
public class GestorContactos {


    private JPanel panel;
    private JTable table1;
    private JTextField textNombre;
    private JTextField textEmail;
    private JButton buttonEditar;
    private JButton buttonEliminar;
    private JButton buttonAgregar;
    private JLabel nombre;
    private JLabel email;

    public JPanel getPanel() {
        return panel;
    }

    private DefaultTableModel modeloTabla;

    public GestorContactos(){
        modeloTabla = new DefaultTableModel();
        Vector<String> fila = new Vector<>();
        fila.add("Hola, mundo");
        modeloTabla.addRow(fila);
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
