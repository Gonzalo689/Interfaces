import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Preferencias");
        Preferencias gestionDeContactos = new Preferencias();
        frame.getContentPane().add(gestionDeContactos.getPanel());
        frame.setMinimumSize(new Dimension(350, 250));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}