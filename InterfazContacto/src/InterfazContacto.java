import javax.swing.*;
import java.awt.*;

public class InterfazContacto {
    private JPanel panel;
    private JLabel cabeza;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;


    public JPanel getPanel() {
        return panel;
    }
    JFrame frame = new JFrame("Preferencias");

    public JFrame getFrame() {
        return frame;
    }
    public InterfazContacto(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cabeza.setBackground(Color.BLUE);
        frame.add(panel);
    }
}
