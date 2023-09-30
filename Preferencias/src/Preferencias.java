import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Preferencias {
    private JPanel panel;
    private JTextField textField1;
    private JRadioButton opcion1RadioButton;
    private JCheckBox notificacionesCheckBox;
    private JRadioButton opcion2RadioButton;
    private JButton preferenciasButton;
    private JSpinner spinner1;

    public JPanel getPanel() {
        return panel;
    }
    JFrame frame = new JFrame("Preferencias");

    public JFrame getFrame() {
        return frame;
    }


    public Preferencias(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem openItem = new JMenuItem("Guardar Preferencias");
        SpinnerNumberModel spinner2 = new SpinnerNumberModel(1,1,10,1);
        spinner1.setModel(spinner2);
        fileMenu.add(openItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.add(panel);
        opcion1RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opcion1RadioButton.isSelected() && opcion2RadioButton.isSelected()){
                    opcion2RadioButton.setSelected(false);
                }
            }
        });
        opcion2RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opcion1RadioButton.isSelected() && opcion2RadioButton.isSelected()){
                    opcion1RadioButton.setSelected(false);
                }
            }
        });

        preferenciasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPreferencias();
            }
        });


    }
    public void guardarPreferencias(){
        String checkSeleccionad = notificacionesCheckBox.isSelected() ? "Si" : "No";
        String opcionSeleccionada= "";
        if (opcion1RadioButton.isSelected() || opcion2RadioButton.isSelected())
            opcionSeleccionada = opcion1RadioButton.isSelected() ? "Opción 1" : "Opción 2";

        String mensaje ="";
        mensaje += "Nombre: " + textField1.getText() + "\n";
        mensaje += checkSeleccionad + " tiene las notificaciones seleccionadas" + "\n";
        mensaje += "Opcion seleccionada: " + opcionSeleccionada + "\n";
        mensaje += "Cantidad de elementos seleccionados: " + spinner1.getValue() + "\n";
        JOptionPane.showMessageDialog(panel, mensaje);


    }
}
