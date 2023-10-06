import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazContacto {
    private JPanel panel;
    private JTextField textDireccion;
    private JTextField textTelefono;
    private JTextField textCorreo;
    private JTextField textNombre;
    private JTextArea textMensaje;
    private JCheckBox checkBoletin;
    private JButton buttonEnviar;
    private JRadioButton radioMasculino;
    private JRadioButton radioFemenino;
    private JRadioButton radioOtro;
    private JCheckBox checkCorreo;


    public JPanel getPanel() {
        return panel;
    }
    JFrame frame = new JFrame("EjemploCorp");

    public JFrame getFrame() {
        return frame;
    }
    public InterfazContacto(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileInicio = new JMenu("Inicio");
        JMenuItem inicio = new JMenuItem("Inicio");
        JMenuItem servicios = new JMenuItem("Servicios");
        JMenuItem acercaNosotros = new JMenuItem("Acerca de Nosotros");
        JMenuItem contacto = new JMenuItem("Contacto");

        fileInicio.add(inicio);
        fileInicio.add(servicios);
        fileInicio.add(acercaNosotros);
        fileInicio.add(contacto);
        menuBar.add(fileInicio);
        frame.setJMenuBar(menuBar);
        frame.add(panel);
        ButtonGroup group = new ButtonGroup();
        group.add(radioMasculino);
        group.add(radioFemenino);
        group.add(radioOtro);

        buttonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String boletin = checkBoletin.isSelected() ? "Si" : "No";
                String correo = checkCorreo.isSelected() ? "Si" : "No";
                String mensaje = "";
                mensaje += "Nombre: " + textNombre.getText() + "\n";
                mensaje += "Correo Electrónico: " + textCorreo.getText() + "\n";
                mensaje += "Teléfono: " + textTelefono.getText() + "\n";
                mensaje += "Dirección: " +textDireccion.getText() + "\n";
                mensaje += "Mensaje: " + textMensaje.getText() + "\n";
                mensaje += "Suscripción al boletin: " + boletin + "\n";
                mensaje += "Recibir promoción por correo: " + correo + "\n";
                if (radioMasculino.isSelected())
                    mensaje += "Genero: Masculino";
                else if (radioFemenino.isSelected())
                    mensaje += "Genero: Femenino";
                else if (radioOtro.isSelected())
                    mensaje += "Genero: Otro";
                else
                    mensaje += "Genero: No seleccionado";

                JOptionPane.showMessageDialog(panel, mensaje,"Contacto Completado", JOptionPane.DEFAULT_OPTION);
            }
        });
    }
}
